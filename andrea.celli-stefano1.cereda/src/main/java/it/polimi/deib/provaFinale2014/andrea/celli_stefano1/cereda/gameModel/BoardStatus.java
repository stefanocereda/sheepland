package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import java.util.ArrayList;

/**
 * A complete representation of the game panel, territories, roads, players and
 * animals. This is a rapresentation of "standard rules", therefore it doesn't
 * include the wolf.
 * 
 * @author Stefano
 * @author Andrea Celli
 * 
 */
public class BoardStatus {
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

	/**
	 * Add a terrain to the ArrayList of terrains (only used to link the enum
	 * and the BoardStatus)
	 * 
	 * @param terrain
	 *            the terrain that has to be added
	 */
	public void addTerrainToBoardStatus(Terrain terrain) {
		terrains.add(terrain);
	}

	/**
	 * This method links the roadMap to the BoardStatus
	 * 
	 * @param roadMap
	 */
	public void addRoadMapToBoardStatus(RoadMap roadMap) {
		this.roadMap = roadMap;
	}

	/**
	 * This method adds a Deck to the boardStatus
	 * 
	 * @param deck
	 */
	public void addDeckToBoardStatus(Deck deck) {
		this.cardsDeck = deck;
	}

	/**
	 * This method returns the deck of a specific game
	 */
	public Deck getDeck() {
		return cardsDeck;
	}

	/**
	 * Check if a road is free: no shepherds and no gates
	 * 
	 * @param roadToCheck
	 *            the road to be checked
	 * @return true if the road is free, no otherwise
	 * @author Stefano
	 */
	public boolean isFreeRoad(Road roadToCheck) {
		// first check if we have a shepherd
		for (Player player : players.getPlayers())
			if (player.getPosition() == roadToCheck)
				return false;

		// then check the gates
		for (Gate gate : (Gate[]) placedGates.toArray())
			if (gate.getPosition() == roadToCheck)
				return false;

		// to be here it must be free
		return true;
	}

}
