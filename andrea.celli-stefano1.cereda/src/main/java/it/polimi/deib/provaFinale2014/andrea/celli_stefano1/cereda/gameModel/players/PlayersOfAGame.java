package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.GenericGameObject;

/**
 * This class contains the array of players that take part in a game.
 * 
 * @author Andrea Celli
 * 
 */
public class PlayersOfAGame extends GenericGameObject {
	private Player[] players;

	/**
	 * The constructor
	 * 
	 * @param numberOfPlayers
	 *            the number of players taking part in a game
	 */
	public PlayersOfAGame(int numberOfPlayers) {
		players = new Player[numberOfPlayers];
	}

	/**
	 * Returns the array of players
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Add a specific player to the array in the first free position
	 * 
	 * @param player
	 *            The player to add
	 */
	public void addPlayerToPlayersOfAGame(Player player) {
		int index;
		for (index = 0; index < players.length && players[index] != null; index++) {
			;
		}
		players[index] = player;
	}

	/**
	 * Returns true if the array already contains a certain player (it checks
	 * the id)
	 * 
	 * @param player
	 *            the player whose presence has to be checked
	 */
	public boolean isAlreadyThere(Player player) {
		for (Player player2 : players) {
			if (player.equals(player2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Calculates the position of a specified player in the array of players.
	 * (From 1 to the array.lenght)
	 * 
	 * @return int
	 */
	public int findPosition(Player player) {
		int position = 0;
		for (int i = 0; i < players.length; i++) {
			if ((players[i] != null) && (player.equals(players[i]))) {
				position = i + 1;
			}
		}
		return position;
	}

}
