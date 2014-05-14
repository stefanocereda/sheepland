package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

/**
 * BuyCard defines the action of buying a terrain card
 * 
 * @author Andrea Celli
 * 
 */
public class BuyCard extends Move {
	private Card newCard;

	/**
	 * BuyCard()
	 * 
	 * @param player
	 *            the player who performs the action
	 * @param card
	 *            the card which is bought
	 */
	public BuyCard(Player player, Card newCard) {
		super(player);
		this.newCard = newCard;
	}

	// this method returns the card bought in this move
	public Card getNewCard() {
		return newCard;
	}
}
