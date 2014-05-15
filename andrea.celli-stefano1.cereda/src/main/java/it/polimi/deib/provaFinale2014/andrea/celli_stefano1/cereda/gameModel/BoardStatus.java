package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import java.util.ArrayList;

/**
 * A complete representation of the game panel, territories, roads, players and
 * animals
 * 
 * @author Stefano
 * @author Andrea Celli
 * 
 *         TODO metodi da player, compreso
 */
public class BoardStatus {
	/** The wolf */
	private Wolf wolf;
	/**
	 * The list of gates, either finally and not (the gates that are already
	 * placed)
	 */
	private ArrayList<Gate> placedGates;
	/** The black sheep */
	private BlackSheep blackSheep;
	/** The list of sheep */
	private ArrayList<Sheep> sheeps;
	/** The array of players */
	private PlayersOfAGame players;
	/** The list of all the terrains of the map */
	private ArrayList<Terrain> terrains;
	/** The map of all the roads */
	private RoadMap roadMap;
	/** The deck of territorial cards */
	private Deck cardsDeck;
	/** The players who has to play */
	private Player currentPlayer;

	/** Returns the current player */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Set the current player
	 * 
	 * @param currentPlayer
	 *            the current player
	 * */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Returns the wolf
	 */
	public Wolf getWolf() {
		return wolf;
	}

	/**
	 * Add a wolf to the BoardStatus if it doesn't already exists
	 * 
	 * @param wolf
	 *            the wolf
	 */
	public void addWolfToBoardStatus(Wolf wolf) {
		if (this.wolf == null)
			this.wolf = wolf;
	}

	/**
	 * Returns the ArrayList of gates
	 */
	public ArrayList<Gate> getGates() {
		return placedGates;
	}

	/**
	 * Add a gate to the ArrayList of placed gates
	 * 
	 * @param gate
	 *            the new gate placed in the map
	 */
	public void addPlacedGateToBoardStatus(Gate gate) {
		placedGates.add(gate);
	}

	/**
	 * Returns the black sheep
	 */
	public BlackSheep getBlackSheep() {
		return blackSheep;
	}

	/**
	 * Add the black sheep to the map, if it doesn't already exists
	 * 
	 * @param blackSheep
	 *            the black sheep
	 */
	public void addBlackSheepToBoardStatus(BlackSheep blackSheep) {
		if (this.blackSheep == null)
			this.blackSheep = blackSheep;
	}

	/**
	 * Returns the array of players
	 */
	public Player[] getPlayers() {
		return players.getPlayers();
	}

	/**
	 * Add a new player to board status.
	 * 
	 * @param player
	 *            the new player
	 */
	public void addPlayerToBoardStatus(Player player) {
		if (!players.isAlreadyThere(player))
			players.addPlayerToPlayersOfAGame(player);
	}
}
