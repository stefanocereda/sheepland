package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.marketGuiHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.GameMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.InterfaceGui;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class manages the panel that allows the player to choose which cards he
 * wants to sell in the market and their price.
 * 
 * It contains smaller panels that contains the information about player's card.
 * 
 * 
 * 
 * @author Andrea
 * 
 */
public class MarketPanelSell extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4071397720025743458L;

	// the layout
	private GridLayout layout;

	// the arraylist containing panels that display card's information and
	// allows to set the price
	private List<SingleCardToSell> cardsSubPanels = new ArrayList<SingleCardToSell>();

	private JLabel initialInformation;

	// the button to allow the player to "say" when he's done
	private JButton nextButton;

	// The listener on the next button
	private NextListener listener;

	private InterfaceGui interfaceGui;

	/**
	 * The constructor creates the panel,
	 * 
	 * adds cards sub panel, initial information, and the button.
	 * 
	 * Adds the listener to the button.
	 * 
	 * @param sellable
	 * @param interfaceGui
	 */
	public MarketPanelSell(List<Card> sellable, InterfaceGui interfaceGui) {

		super();

		this.interfaceGui = interfaceGui;

		GameMap gameMap = interfaceGui.getFrame().getMap();
		// set the gridLayout depending on the number of cards (1 slot for each
		// card+ 1 slot for the information + 1 slot for the next button)
		layout = new GridLayout(sellable.size() + 2, 1);
		this.setLayout(layout);

		// add the bigger panel to the game map
		this.setSize(gameMap.getWidth() / 2, gameMap.getHeight() / 2);
		this.setLocation(gameMap.getWidth() / 4, gameMap.getHeight() / 4);
		gameMap.add(this, 0);

		if (!sellable.isEmpty()) {
			// add the information panel
			initialInformation = new JLabel(
					"Choose the price of the cards you want to sell");
			this.add(initialInformation);
			initialInformation.setVisible(true);

			// add the cards subpanels
			for (int i = 0; i < sellable.size(); i++) {

				// creates a new SingleCardToBuyPanel and adds it to the
				// arraylist
				// of panels
				cardsSubPanels.add(new SingleCardToSell(sellable.get(i)));

				// add the single card panel to the container panel
				this.add(cardsSubPanels.get(i));
				cardsSubPanels.get(i).setVisible(true);
			}
		} else {
			initialInformation = new JLabel("No cards to sell");
			this.add(initialInformation);
			initialInformation.setVisible(true);
		}

		// add the button and set the action listener
		nextButton = new JButton("Next");
		listener = new NextListener();
		nextButton.addActionListener(listener);
		this.add(nextButton);
	}

	/**
	 * The listener waits for the player to choose to go on (next button).
	 * 
	 * It creates the arraylist of cards that the player wants to sell and send
	 * it back to the interfaceGui.
	 * 
	 */
	private class NextListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			// For every SingleCardtoBuy it checks if the player wants to sell
			// it
			// if so it creates a new market offer (Player+card+price) and adds
			// it to the list
			// Finally it send the whole list of market offer to the
			// interfaceGui

			List<MarketOffer> marketOffersList = new ArrayList<MarketOffer>();

			if (cardsSubPanels != null) {
				for (SingleCardToSell cardToSell : cardsSubPanels) {

					if (cardToSell.wantToSell()) {
						// creates and adds the new market offer
						marketOffersList.add(new MarketOffer(interfaceGui
								.getGameController().getControlledPlayer(),
								cardToSell.getCard(), cardToSell.getPrice()));
					}
				}
			}

			interfaceGui.returnMarketOffers(marketOffersList);
		}
	}

}
