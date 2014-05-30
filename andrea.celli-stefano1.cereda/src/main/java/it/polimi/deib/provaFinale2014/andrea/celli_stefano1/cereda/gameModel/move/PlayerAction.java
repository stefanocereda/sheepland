package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * All the classes of move made by a player inherit from this class.
 * 
 * @author Andrea
 * 
 */
public abstract class PlayerAction extends Move {
	private Player player;

	/**
	 * @param player
	 *            The player performing the move, it will also be used as the
	 *            actor of the move (i.e. in an BuyCard move this is player who
	 *            buys
	 */
	public PlayerAction(Player player) {
		super();
		this.player = player;
	}

	/** @return the actor of the move */
	public Player getPlayer() {
		return player;
	}
}
