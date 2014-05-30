package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.ExecuteAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.RuleChecker;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * BuyCard defines the action of buying a terrain card
 * 
 * @author Andrea Celli
 * 
 */
public class BuyCardMove extends PlayerAction {
	private Card newCard;

	/**
	 * @param player
	 *            The player buying the card
	 * @param card
	 *            the card which is bought
	 */
	public BuyCardMove(Player player, Card newCard) {
		super(player);
		this.newCard = newCard;
	}

	/** @return the card bought in this move */
	public Card getNewCard() {
		return newCard;
	}

	@Override
	public boolean isValid(BoardStatus status) {
		return RuleChecker.isValidBuyCard(this, status);
	}

	@Override
	public void execute(BoardStatus boardStatus) {
		ExecuteAction.executeBuyCardMove(this, boardStatus);
	}
}
