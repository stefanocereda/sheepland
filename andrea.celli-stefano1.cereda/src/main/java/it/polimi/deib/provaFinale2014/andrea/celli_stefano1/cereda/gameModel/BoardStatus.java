package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Gate;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.RoadMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayersOfAGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A complete representation of the game panel, territories, roads, players and
 * animals. This is a representation of "standard rules", therefore it doesn't
 * include the wolf.
 * 
 * @author Stefano
 * @author Andrea Celli
 * 
 */
public class BoardStatus implements Serializable {
	/**
	 * The list of gates, either finally and not (the gates that are already
	 * placed)
	 */
	private List<Gate> placedGates;
	/** The black sheep */
	private BlackSheep blackSheep;
	/** The list of sheep */
	private List<Sheep> sheeps;
	/** The array of players */
	private PlayersOfAGame players;
	/** The list of all the terrains of the map */
	private List<Terrain> terrains;
	/** The map of all the roads */
	private RoadMap roadMap;
	/** The deck of territorial cards */
	private Deck cardsDeck;
	/** The players who has to play */
	private Player currentPlayer;

	/**
	 * BoardsStatus constructor initialize the structures, creates the map, the
	 * roads and the deck of cards. It leaves out animals and players
	 * 
	 * @param numberOfPlayers
	 *            the number of players taking part in this game
	 * 
	 * @author Stefano
	 */
	public BoardStatus(int numberOfPlayers) {
		// create the array list
		placedGates = new ArrayList<Gate>();
		sheeps = new ArrayList<Sheep>();
		terrains = new ArrayList<Terrain>();
		players = new PlayersOfAGame(numberOfPlayers);

		// init the territories
		for (Terrain t : Terrain.values()) {
			terrains.add(t);
		}

		// init the roads
		roadMap = RoadMap.getRoadMap();

		// init the deck
		cardsDeck = new Deck();

		// init the blackSheep (it always starts from sheepsburg)
		blackSheep = new BlackSheep(Terrain.SHEEPSBURG);

		// init the current player
		currentPlayer = new Player();
	}

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
	public List<Gate> getGates() {
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
		for (Gate gate : placedGates.toArray(new Gate[placedGates.size()]))
			if (gate.getPosition() == roadToCheck)
				return false;
		// to be here it must be free
		return true;
	}

	/**
	 * Add a sheep to the board
	 * 
	 * @param sheep
	 *            the sheep to add
	 * @author Stefano
	 */
	public void addSheep(Sheep sheep) {
		sheeps.add(sheep);
	}

	/** Returns the road map */
	public RoadMap getRoadMap() {
		return roadMap;
	}

	/**
	 * This method calculate the number of standard (not final) gates placed in
	 * the map
	 * 
	 * @param placedGates
	 *            The arrayList of placed Gates
	 * @return int the number of standard gates placed in the map
	 * @author Andrea
	 */
	public int countStandardGates(List<Gate> placedGates) {
		int numberOfStandardGates = 0;
		for (Gate gate : placedGates)
			if (!gate.isLast())
				numberOfStandardGates++;
		return numberOfStandardGates;
	}

}
