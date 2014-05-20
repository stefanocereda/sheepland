package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * A Move represents all the type of moves available in the game and the actors
 * involved in a move. This class will be used for client/server communications,
 * for rule checking and to actually execute them.
 * 
 * Specific types of moves are built through inheritance of the class Move
 * 
 * @author Andrea Celli
 */
public class Move {
	private Player player;

	/**
	 * @param player
	 *            The player performing the move, it will also be used as the
	 *            actor of the move (i.e. in an BuyCard move this is player who
	 *            buys
	 */
	public Move(Player player) {
		this.player = player;
	}

	/** @return the actor of the move */
	public Player getPlayer() {
		return player;
	}
}