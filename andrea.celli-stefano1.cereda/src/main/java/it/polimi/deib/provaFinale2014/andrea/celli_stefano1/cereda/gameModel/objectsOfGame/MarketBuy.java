package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.GenericGameObject;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/** This class represents the act of buying a card during the market phase */
public class MarketBuy extends GenericGameObject {
	private static final long serialVersionUID = 7251457679227886409L;

	/** The player that is doing this buy action */
	private Player buyer;
	/** The card being bought */
	private Card cardBought;

	/**
	 * 
	 * @param buyer
	 *            The player that is buying the card
	 * @param cardBought
	 *            The card bought
	 */
	public MarketBuy(Player buyer, Card cardBought) {
		super();
		this.buyer = buyer;
		this.cardBought = cardBought;
	}

}
