package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * BuyCard defines the action of buying a terrain card
 * 
 * @author Andrea Celli
 * 
 */
public class BuyCardMove extends Move {
	private Card newCard;
	private int price;

	/**
	 * @param player
	 *            The player buying the card
	 * @param card
	 *            the card which is bought
	 * @param price
	 *            the price paid for the new card. The client sets it to zero
	 *            and sends the move to the server. The server calculates the
	 *            right price and sets it in the move, then gives the move back
	 *            to the client.
	 */
	public BuyCardMove(Player player, Card newCard, int price) {
		super(player);
		this.newCard = newCard;
		this.price = price;
	}

	/** @return the card bought in this move */
	public Card getNewCard() {
		return newCard;
	}

	/** @return the price paid for the card */
	public int getCardPrice() {
		return price;
	}

	/** Set the price */
	public void setPrice(int newPrice) {
		price = newPrice;
	}
}
