package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

/**
 * This class manages the selection of a new card. It handles the action events
 * generated by the buttons. It also adds and remove the buy card panel.
 * 
 * @author Andrea
 * 
 */

public class BuyCardManager implements ActionListener {

	/**
	 * The map on which the buy card panel will be displayed
	 */
	private GameMap gameMap;

	/** Used to send moves back and to get information on adjacent terrains */
	private InterfaceGui interfaceGui;

	/** The displayed panel */
	BuyCardPanel buyCardPanel;

	/** The buyable cards */
	ArrayList<Card> buyable;

	public BuyCardManager(GameMap map, InterfaceGui interfaceGui) {
		gameMap = map;
		this.interfaceGui = interfaceGui;
	}

	/**
	 * This method creates a new buy card panel displaying the available cards
	 */
	public void getNewCard() {

		buyable = (ArrayList) interfaceGui
				.getGameController()
				.getBoardStatus()
				.getCardsPlayerCanBuy(
						interfaceGui.getGameController().getControlledPlayer());
		// ask for the card only if there's at least a buyable card
		if (buyable.size() > 0) {
			buyCardPanel = new BuyCardPanel(buyable, this,
					(int) gameMap.getWidth() / 3, (int) gameMap.getHeight() / 3);
			buyCardPanel.setSize(gameMap.getWidth() / 3,
					gameMap.getHeight() / 3);
			buyCardPanel.setLocation(gameMap.getWidth() / 3,
					gameMap.getHeight() / 3);
			gameMap.add(buyCardPanel, 0);

			buyCardPanel.setVisible(true);

			interfaceGui.getFrame().validate();
			interfaceGui.getFrame().repaint();
		} else {
			// if there're no cards available it goes back to move selection
			interfaceGui.getNewMove();
		}
	}

	/** This method removes the buy card panel */
	public void removeBuyCardPanel() {

		gameMap.remove(buyCardPanel);
		gameMap.repaint();

	}

	/** This is the listener which is called when a button is pressed */
	public void actionPerformed(ActionEvent e) {

		JButton pressed = (JButton) e.getSource();
		String text = pressed.getText();

		if (text.equals(buyable.get(0).toString())) {
			update(buyable.get(0));
		} else {
			if (text.equals(buyable.get(1).toString())) {
				update(buyable.get(1));
			}
		}

		removeBuyCardPanel();
	}

	/**
	 * 1)return buy card move to the interfaceGui 2)update player money in the
	 * console 3) update displayed cards
	 * 
	 * @param card
	 */

	private void update(Card card) {

		Player currentPlayer = interfaceGui.getGameController()
				.getControlledPlayer();

		// set the status back to NOT_YOUR_TURN
		gameMap.getListener().setStatus(GameStatus.NOT_YOUR_TURN);

		// 1)
		interfaceGui.returnMoveFromGui(new BuyCardMove(currentPlayer, card));

		// 2)
		interfaceGui.getFrame().getConsole().getPlayersPanel()
				.getPlayerDisplayedData(currentPlayer).getMoneyPlayer()
				.setMoneyPlayer(currentPlayer.getMoney() - card.getNumber());

		// 3)
		int numberOfCard = card.getNumber();

		if (numberOfCard < 4) {

			// looks in the board status for the new buyable card of that type
			// and adds it to the cards panel

			interfaceGui
					.getFrame()
					.getConsole()
					.getCardsPanel()
					.addCard(
							interfaceGui.getGameController().getBoardStatus()
									.getNewBuyableCardOfATerrainType(card));
		} else {
			interfaceGui.getFrame().getConsole().getCardsPanel()
					.removeSingleCardPanel(card.getTerrainType());
		}
	}
}
