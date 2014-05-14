package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;

import java.util.ArrayList;

/**
 * A game player, not including the network link with the client
 * 
 * @author Stefano
 * 
 *         TODO i remove e add delle ultime mosse
 */
public class Player {
	/** The money of the player */
	private int money;
	/** The array of the last moves of this player */
	private ArrayList<Move> lastMoves;
	/** the array of the territorial card owned by the player */
	private ArrayList<Card> territorialCards;
	/** The position of the player (road) */
	private Road position;

	/** Returns the money of the player */
	public int getMoney() {
		return money;
	}

	/**
	 * Move the player in a new road, without rules checking
	 * 
	 * @param newRoad
	 *            The new road
	 */
	public void move(Road newRoad) {
		position = newRoad;
	}

	/**
	 * Add a card to the player list
	 * 
	 * @param newCard
	 *            The card to add
	 */
	public void addCard(Card newCard) {
		territorialCards.add(newCard);
	}

	/**
	 * Set the money of the player
	 * 
	 * @param newMoney
	 *            The new value of money
	 */
	public void setMoney(int newMoney) {
		money = newMoney;
	}
}
