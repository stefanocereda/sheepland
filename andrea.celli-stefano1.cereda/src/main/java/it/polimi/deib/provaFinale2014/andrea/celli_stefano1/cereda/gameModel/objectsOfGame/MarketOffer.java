package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.GenericGameObject;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.RuleChecker;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * This is the representation of a market offer, representing the player that is
 * offering a card at a certain price
 * 
 * @author stefano
 * 
 */
public class MarketOffer extends GenericGameObject {

    private static final long serialVersionUID = 3843663438208852213L;
    /** The player making this offer */
    private Player offerer;
    /** The traded card */
    private Card cardOffered;
    /** The price requested for the card */
    private int price;

    /**
     * 
     * @param offerer
     *            The player that is making this offer
     * @param cardOffered
     *            The card that we are talking about
     * @param price
     *            The price that the player is requesting for the card
     */
    public MarketOffer(Player offerer, Card cardOffered, int price) {
        this.offerer = offerer;
        this.cardOffered = cardOffered;
        this.price = price;
    }

    /**
     * @return the offerer
     */
    public Player getOfferer() {
        return offerer;
    }

    /**
     * @return the cardOffered
     */
    public Card getCardOffered() {
        return cardOffered;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /** Check if this is a valid market offer */
    public boolean isValidOffer(Player offerer) {
        return RuleChecker.isValidMarketOffer(this, offerer);
    }

}
