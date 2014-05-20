package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;

import java.util.ArrayList;

/**
 * A game player, not including the network link with the client
 * 
 * @author Stefano
 * @author Andrea Celli
 */
public class Player {
	/** The money of the player */
	private int money;
	/** The array of the last moves of this player */
	private ArrayList<Move> lastMoves = new ArrayList<Move>();
	/** the array of the territorial card owned by the player */
	private ArrayList<Card> territorialCards = new ArrayList<Card>();
	/** The position of the player (road) */
	private Road position;

	/** Standard constructor */
	public Player() {
		;
	}

	/**
	 * Constructor that sets the initial values of the attributes
	 * 
	 * @param money
	 * @param initialCard
	 * @param position
	 * */
	public Player(int money, Card initialCard, Road position) {
		this.money = money;
		territorialCards.add(initialCard);
		this.position = position;
	}

	/** Returns the money of the player */
	public int getMoney() {
		return money;
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

	/**
	 * Subtract the specified ammout of money from the money of a player
	 * 
	 * @param moneySpent
	 *            the money to subtract
	 */
	public void subtractMoney(int moneySpent) {
		money = money - moneySpent;
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
	 * Get the position of the player
	 */
	public Road getPosition() {
		return position;
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
	 * Returns the list of cards of the player
	 */
	public ArrayList<Card> getCards() {
		return territorialCards;
	}

	/**
	 * Add a move to the past moves of the player
	 * 
	 * @param move
	 *            the new move performed by the player
	 */
	public void addLastMove(Move lastMove) {
		lastMoves.add(lastMove);
	}

	/**
	 * Returns the list of past moves of the player
	 */
	public ArrayList<Move> getLastMoves() {
		return lastMoves;
	}
}
