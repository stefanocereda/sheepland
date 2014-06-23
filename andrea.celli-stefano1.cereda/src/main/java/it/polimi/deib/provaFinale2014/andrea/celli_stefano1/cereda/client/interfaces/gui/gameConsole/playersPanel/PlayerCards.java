package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.playersPanel;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;

import java.awt.Color;
import java.util.List;

public class PlayerCards extends javax.swing.JLabel {
	private static final long serialVersionUID = 9140172754329871486L;

	/**
	 * the constructor takes as input the list of cards owned by the player and
	 * the background color
	 */
	public PlayerCards(List<Card> cards, Color playerColor) {
		super();
		setBackground(playerColor);
		setPlayerCards(cards);
	}

	/**
	 * Set the cards owned by a player, without the initial ones
	 * 
	 * @param money
	 */
	public void setPlayerCards(List<Card> cards) {
		String msg = "";
		for (Card c : cards) {
			if (!c.isInitial()) {
				msg += c.toStringCompact() + " ";
			}
		}

		super.setText(msg);
		repaint();
	}

	/** Set the cards owned by a player, with the initial ones */
	public void setPlayerCardsWithInitial(List<Card> cards) {
		String msg = "";
		for (Card c : cards) {
			msg += c.toStringCompact() + " ";
		}

		super.setText(msg);
		repaint();
	}
}
