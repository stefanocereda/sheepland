package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.marketGuiHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This panel displays the information on a single card that can be sold in the
 * market. It contains a Jlabel to display the name and a textField to specify
 * the price.
 * 
 * @author Andrea
 * 
 */
public class SingleCardToSell extends JPanel {

	JLabel cardName;
	JTextField priceSetter;
	// the displayed card
	private Card card;
	// the price
	private int price;
	// flag that determines wheter the player wants to sell the card or not
	private boolean sell;
	// the listener
	TextListener textListener;

	public SingleCardToSell(Card card) {
		super();

		this.setLayout(new GridLayout(1, 2));

		this.card = card;
		price = 0;
		sell = false;
		textListener = new TextListener();

		cardName = new JLabel(card.toString());

		priceSetter = new JTextField("Don't sell");
		priceSetter.setEditable(true);
		priceSetter.addActionListener(textListener);

		this.add(cardName);
		this.add(priceSetter);
		cardName.setVisible(true);
		cardName.setVisible(false);
	}

	private class TextListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

		}

	}

	public int getPrice() {
		return price;
	}

	public boolean wantToSell() {
		return sell;
	}

}
