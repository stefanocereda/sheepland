package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road;

/**
 * MovePlayer defines the action of moving a shepherd to a new position
 * 
 * @author Andrea Celli
 * 
 */
public class MovePlayer extends Move {
	private Road newPosition;
	private int cost;

	/**
	 * The constructor creates a new move in which a shepherd is moved to a new
	 * road
	 * 
	 * @param player
	 *            the moved player (which is also the moving player)
	 * @param newPosition
	 *            the new road in which the player is placed
	 * @param cost
	 *            the cost of the move.The client sets it to zero and sends the
	 *            move to the server. The server calculates the right cost and
	 *            sets it in the move, then gives the move back to the client.
	 */
	public MovePlayer(Player player, Road newPosition, int cost) {
		super(player);
		this.newPosition = newPosition;
		this.cost = cost;
	}

	/** @return the new position of the player */
	public Road getNewPositionOfThePlayer() {
		return newPosition;
	}

	/** @return the cost of the move */
	public int getCost() {
		return cost;
	}

	/** Set the cost */
	public void setCost(int newCost) {
		cost = newCost;
	}
}
