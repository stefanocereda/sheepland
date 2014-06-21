package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.marketGuiHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.GameMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.InterfaceGui;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
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
 * This panels diplays all the market offers that a player can purchase (it
 * doesn't show market offers made by the player himself).
 * 
 * When the player press the nextButton a new arrayList of marketBuy is created
 * and send back to the interfaceGui.
 * 
 * @author Andrea
 * 
 */
public class MarketPanelBuy extends JPanel {

	private InterfaceGui interfaceGui;

	// the list of offers that can be purchased by the player
	private List<MarketOffer> buyableOffers = new ArrayList<MarketOffer>();

	// the list of SingleCardToBuy panels
	private List<SingleCardToBuy> offersPanels = new ArrayList<SingleCardToBuy>();

	// the initial information label
	private JLabel infoLabel;

	// the next button
	private JButton nextButton;

	// the listener on the next button
	private NextListener listener;

	// the layout of the panel
	private GridLayout layout;

	/**
	 * The constructor performs the following tasks: 1) creates the panel 2)adds
	 * intro information, SingleCardToBuy panels and the button 3)set the the
	 * listener on the next button
	 * 
	 * @param marketOffers
	 * @param interfaceGui
	 */
	public MarketPanelBuy(ArrayList<MarketOffer> marketOffers,
			InterfaceGui interfaceGui) {

		super();

		this.interfaceGui = interfaceGui;

		GameMap gameMap = interfaceGui.getFrame().getMap();

		// creates the arraylist of buyable offers deleting offers made by this
		// player.
		for (MarketOffer marketOffer : marketOffers) {
			if (!(marketOffer.getOfferer().equals(interfaceGui
					.getGameController().getControlledPlayer()))) {
				buyableOffers.add(marketOffer);
			}
		}

		// set and add the layoutManager
		layout = new GridLayout(buyableOffers.size() + 2, 1);
		this.setLayout(layout);

		// sets the dimension, the location and adds to the gameMap
		this.setSize(gameMap.getWidth() / 2, gameMap.getHeight() / 2);
		this.setLocation(gameMap.getWidth() / 4, gameMap.getHeight() / 4);
		gameMap.add(this);

		// add the information panel
		infoLabel = new JLabel("Select offers you want to purchase");
		this.add(infoLabel);
		infoLabel.setVisible(true);

		// adds offers panels
		for (int i = 0; i < buyableOffers.size(); i++) {

			// create the new SingleCardToBuy panel
			offersPanels.add(new SingleCardToBuy(buyableOffers.get(i)));

			this.add(offersPanels.get(i));
			offersPanels.get(i).setVisible(true);
		}

		// adds the button and sets the listener
		nextButton = new JButton("Next");
		listener = new NextListener();
		nextButton.addActionListener(listener);
		this.add(nextButton);
	}

	/**
	 * This is the listener on the nextButton.
	 * 
	 * When the button is pressed the listener creates a new arraylist of
	 * marketBuy and send it back to the interfaceGui.
	 */
	private class NextListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			// the arraylist that will be sent back to the interfaceGui
			List<MarketBuy> offersPurchased = new ArrayList<MarketBuy>();

			// looks for the purchased offers among the SingleCardToBuy panels
			for (SingleCardToBuy offer : offersPanels) {

				if (offer.isBought()) {

					// creates the new MarketBuy
					offersPurchased.add(new MarketBuy(interfaceGui
							.getGameController().getControlledPlayer(), offer
							.getMarketOffer().getCardOffered()));
				}
			}

			interfaceGui
					.returnMarketBuyList((ArrayList<MarketBuy>) offersPurchased);
		}

	}
}
