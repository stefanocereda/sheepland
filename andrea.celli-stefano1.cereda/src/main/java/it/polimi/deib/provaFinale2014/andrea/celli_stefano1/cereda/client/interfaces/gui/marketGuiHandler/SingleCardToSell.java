package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.marketGuiHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;

import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This panel displays the information on a single card that can be sold in the
 * market. It contains a Jlabel to display the name and a textField to specify
 * the price.
 * 
 * @author Andrea
 * 
 */
public class SingleCardToSell extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4465135399964134446L;
	JLabel cardName;
	JTextField priceSetter;
	// the displayed card
	private Card card;
	// the price
	private int price;
	// flag that determines wheter the player wants to sell the card or not
	private boolean sell;
	// the listener
	DocListener listener;

	public SingleCardToSell(Card card) {
		super();

		this.setLayout(new GridLayout(1, 2));

		this.card = card;
		price = 0;
		sell = false;

		listener = new DocListener();

		cardName = new JLabel("     " + card.toString());

		priceSetter = new JTextField("Don't sell");
		priceSetter.setEditable(true);
		priceSetter.getDocument().addDocumentListener(listener);

		this.add(cardName);
		this.add(priceSetter);
		cardName.setVisible(true);
	}

	/**
	 * The listener manages changes in the JTextField. If the player writes
	 * "Don't" sell the flag is set to false. Otherwise, if the string in the
	 * jTextField is a number, is set the new price and the flag to true.
	 * 
	 */
	private class DocListener implements DocumentListener {

		public void insertUpdate(DocumentEvent e) {

			String text = priceSetter.getText();

			// if the text is "Don't sell" the flag is set again to false
			if ("Don't sell".equals(text)) {
				sell = false;
			} else {
				// try to get the price from the input string
				try {
					price = Integer.parseInt(text);
					sell = true;
				} catch (NumberFormatException ex) {

					Logger.getLogger(SingleCardToSell.class.getName()).log(
							Level.WARNING, "You didn't insert a number", ex);

					// if the player writes the wrong input the card is not sold
					sell = false;
				}

			}

		}

		public void removeUpdate(DocumentEvent e) {
		}

		public void changedUpdate(DocumentEvent e) {
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
