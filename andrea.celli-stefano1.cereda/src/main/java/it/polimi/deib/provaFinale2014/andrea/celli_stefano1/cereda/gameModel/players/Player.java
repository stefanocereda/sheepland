package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.GenericGameObject;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;

import java.util.ArrayList;
import java.util.List;

/**
 * A game player, not including the network link with the client
 * 
 * @author Stefano
 * @author Andrea Celli
 */
public class Player extends GenericGameObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -811469198290925192L;
	/** The money of the player */
	private int money;
	/** The array of the last moves of this player */
	private List<Move> lastMoves = new ArrayList<Move>();
	/** the array of the territorial card owned by the player */
	private List<Card> territorialCards = new ArrayList<Card>();
	/** The position of the player (road) */
	private Road position;
	/** A player is suspended when disconnected for a fixed time */
	private boolean suspended = false;
	/** A player is not connected when it loose connection */
	private boolean connected = true;

	/** Standard constructor */
	public Player() {
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
	 * Subtract the specified amount of money from the money of a player
	 * 
	 * @param moneySpent
	 *            the money to subtract
	 */
	public void subtractMoney(int moneySpent) {
		money = money - moneySpent;
	}

	/**
	 * Add the specified ammount of money
	 * 
	 * @param newMoney
	 *            the money that have to be added
	 */
	public void addMoney(int newMoney) {
		money = money + newMoney;
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
	public List<Card> getCards() {
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
	public List<Move> getLastMoves() {
		return lastMoves;
	}

	/** Set the status to suspended */
	public void suspend() {
		suspended = true;
	}

	/** Set the status to not-suspended */
	public void resume() {
		suspended = false;
	}

	/** Check if a player is suspended */
	public boolean isSuspended() {
		return suspended;
	}

	/** Set the status to connected */
	public void setConnected() {
		connected = true;
	}

	/** Set the status to not-connected */
	public void setNotConnected() {
		connected = false;
	}

	/** Check if a player is connected */
	public boolean isConnected() {
		return connected;
	}

	public void deleteLastMoves() {
		lastMoves = new ArrayList<Move>();
	}
}
