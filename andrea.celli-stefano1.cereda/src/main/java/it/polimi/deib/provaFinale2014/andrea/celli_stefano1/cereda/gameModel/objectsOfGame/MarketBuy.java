package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import java.util.List;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.GenericGameObject;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.ExecuteAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.RuleChecker;
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

	/**
	 * @return the buyer
	 */
	public Player getBuyer() {
		return buyer;
	}

	/**
	 * @return the cardBought
	 */
	public Card getCardBought() {
		return cardBought;
	}

	/**
	 * Check if this buy is valid in the context of the given list of market
	 * offers
	 */
	public boolean isValidBuy(List<MarketOffer> offers, Player buyer) {
		return RuleChecker.isValidMarketBuy(this, offers, buyer);
	}

	/** Execute a market buy, trade cards and money and delete the offer */
	public void execute(List<MarketOffer> offers,
			BoardStatusExtended boardStatus) {
		ExecuteAction.executeMarketTrade(this, offers, boardStatus);
	}
}
