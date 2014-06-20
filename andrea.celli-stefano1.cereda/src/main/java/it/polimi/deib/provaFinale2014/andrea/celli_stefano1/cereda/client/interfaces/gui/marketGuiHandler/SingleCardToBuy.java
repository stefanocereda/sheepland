package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.marketGuiHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This panel contains the information of the buyable market offers. It also
 * contains a button that allows the player to select the offers he wants to
 * purchase.
 * 
 * When an offer is purchased its background color is set to green.
 * 
 * 
 * @author Andrea
 * 
 */
public class SingleCardToBuy extends JPanel {

	// the displayed market offer
	private MarketOffer marketOffer;

	private JButton buyButton;

	private JLabel offerInformation;

	private BuyListener listener;

	// the flag that tells if the player bought the offer
	private boolean bought;

	/**
	 * The constructor builds the panel
	 * 
	 * @param marketOffer
	 */
	public SingleCardToBuy(MarketOffer marketOffer) {
		super();

		this.marketOffer = marketOffer;

		this.setLayout(new GridLayout(1, 2));

		bought = false;

		// Label displaying information about the offer
		offerInformation = new JLabel(marketOffer.getOfferer().toString()
				+ " sells " + marketOffer.getCardOffered().toString()
				+ " at price: " + marketOffer.getPrice());
		offerInformation.setBackground(GuiConstants.COLORGAMECONSOLE);
		this.add(offerInformation);

		// button to buy the offer
		buyButton = new JButton("Buy");
		listener = new BuyListener();
		buyButton.addActionListener(listener);
		this.add(buyButton);

	}

	/**
	 * The listener manages the click on the BuyButton.
	 * 
	 * if bought=false: 1)set the new background color 2)set bought to true
	 * 
	 * if bought=true: 1)set the default background color 2)set bought to false
	 * 
	 */
	private class BuyListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (!bought) {

				offerInformation.setBackground(Color.GREEN);
				bought = true;

			} else {

				offerInformation.setBackground(GuiConstants.COLORGAMECONSOLE);
				bought = false;

			}

		}

	}

	public MarketOffer getMarketOffer() {
		return marketOffer;
	}

	public boolean isBought() {
		return bought;
	}

}
