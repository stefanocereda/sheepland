package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class is used to display the panel to choose which card to buy.
 * 
 * @author Andrea
 * 
 */
public class BuyCardPanel extends JPanel {

	// the buyable cards
	private List<JTextField> cards = new ArrayList<JTextField>();

	// the possible buttons to select a card
	private List<JButton> buttons = new ArrayList<JButton>();

	// the grid layout used to manage the panel
	private GridLayout layout;

	// the listener on the buttons
	private BuyCardManager listener;

	/**
	 * 
	 * @param card
	 *            (the array of buyable cards)
	 */
	public BuyCardPanel(Card[] buyableCards, BuyCardManager listener) {

		this.listener = listener;

		// one slot for the "intro" and then one for each card and one for each
		// coresponding button
		layout = new GridLayout(buyableCards.length * 2 + 1, 1);

		setLayout(layout);
		setBackground(Color.BLUE);

		JTextField intro = new JTextField("BUYABLE CARDS:");
		setJTextField(intro);
		this.add(intro);
		intro.setVisible(true);

		// JTextFields rapresenting the cards
		for (int i = 0; i < buyableCards.length; i++) {
			cards.add(new JTextField("Card " + i + ": "
					+ buyableCards[i].toString()));
			setJTextField(cards.get(i));
			this.add(cards.get(i));
			cards.get(i).setVisible(true);
		}

		// Buttons
		for (int i = 0; i < buyableCards.length; i++) {
			buttons.add(new JButton("Card " + i));
			this.add(buttons.get(i));
			buttons.get(i).addActionListener(listener);
			buttons.get(i).setVisible(true);
		}

	}

	/** Used to set the style of the font */
	private void setJTextField(JTextField text) {
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setBackground(Color.BLUE);
		text.setEditable(false);
		text.setFont(new Font("Verdana", Font.BOLD, 13));
		text.setForeground(Color.WHITE);
	}
}
