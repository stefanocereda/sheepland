package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

/**
 * The move is used for client/server communications, for rule checking and to
 * actually execute them
 * 
 * Specific types of moves are built through inheritance of the class Move
 * 
 * @author Andrea Celli
 */
public class Move {
	private Player player;

	public Move(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}