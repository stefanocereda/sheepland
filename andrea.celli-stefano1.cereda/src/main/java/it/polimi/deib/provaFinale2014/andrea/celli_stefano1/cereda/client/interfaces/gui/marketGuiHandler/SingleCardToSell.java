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

	/**
	 * The listener manages changes in the JTextField. If the player writes
	 * "Don't" sell the flag is set to false. Otherwise, if the string in the
	 * jTextField is a number, is set the new price and the flag to true.
	 * 
	 */
	private class TextListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			// get the new price
			JTextField textField = (JTextField) e.getSource();
			String newPrice = textField.getText();

			// if the text is "Don't sell" the flag is set again to false
			if ((textField.equals("Don't sell"))) {
				sell = false;
			} else {
				// try to get the price from the input string
				try {
					price = Integer.parseInt(newPrice);
					sell = true;
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			}
		}

	}

	public int getPrice() {
		return price;
	}

	public boolean wantToSell() {
		return sell;
	}

	public Card getCard() {
		return card;
	}

}
