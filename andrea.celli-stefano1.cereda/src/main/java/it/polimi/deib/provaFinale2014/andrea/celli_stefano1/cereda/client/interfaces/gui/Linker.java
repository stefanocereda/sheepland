package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * This class manage all the links between: 1)color and terrains 2)color and
 * roads 3)pawns and players 4)fixed points in the map where to locate panels
 * (terrain-type of animal-point)
 * 
 * A singleton here should be nice!
 * 
 * @author Andrea
 * 
 */

public class Linker {

	/**
	 * This hashmap contains the links between the player and the pawns. (In
	 * this way they're linked also with their "game name" and their
	 * "game color".
	 */
	private Map<Player, Pawns> playersAndPawns = new HashMap<Player, Pawns>();

	/**
	 * This methods creates the relations between player and pawns.
	 * 
	 * @param the
	 *            array of player taking part in the game.
	 */
	public void givePawns(Player[] players) {
		for (int i = 0; i < players.length; i++) {
			playersAndPawns.put(players[i], Pawns.values()[i]);
		}
	}

	/**
	 * This methods returns the pawn of a player.
	 * 
	 * @param player
	 */
	public Pawns getPawn(Player player) {
		return playersAndPawns.get(player);
	}

}
