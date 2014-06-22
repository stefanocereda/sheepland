package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Dice;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Gate;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.RoadMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayerDouble;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayersOfAGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	 * 
	 */
	private static final long serialVersionUID = 3722246987094098610L;
	/**
	 * The list of gates, either finally and not (the gates that are already
	 * placed)
	 */
	private List<Gate> placedGates;
	/** The black sheep */
	private BlackSheep blackSheep;
	/** The list of sheep */
	protected List<Sheep> sheeps;
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
	/** The player who first played (considering the whole game) */
	private Player firstPlayer;

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
		roadMap.setID();

		// init the deck
		cardsDeck = new Deck();
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
	 * Add the black sheep to the map
	 * 
	 * @param blackSheep
	 *            the black sheep
	 */
	public void addBlackSheepToBoardStatus(BlackSheep blackSheep) {
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
		if (!players.isAlreadyThere(player)) {
			players.addPlayerToPlayersOfAGame(player);
		}
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
		for (Player player : players.getPlayers()) {
			if (player.getPosition() != null
					&& player.getPosition().equals(roadToCheck)) {
				return false;
			}
			// check for players with two shepherds
			if (PlayerDouble.class.isInstance(player)) {
				PlayerDouble p = (PlayerDouble) player;
				if (p.getSecondposition() != null
						&& p.getSecondposition().equals(roadToCheck)) {
					return false;
				}
				if (p.getFirstPosition() != null
						&& p.getFirstPosition().equals(roadToCheck)) {
					return false;
				}
			}
		}

		// then check the gates
		if (!isFreeFromGates(roadToCheck)) {
			return false;
		}

		// to be here it must be free
		return true;
	}

	/**
	 * Check if the give road is free from gates
	 * 
	 * @param toCheck
	 *            the road to check
	 * @return if the given road is free from gates
	 */
	public boolean isFreeFromGates(Road toCheck) {
		for (Gate gate : placedGates) {
			if (gate.getPosition().equals(toCheck)) {
				return false;
			}
		}
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

	/**
	 * This method returns the arrayList of Sheeps
	 */
	public List<Sheep> getSheeps() {
		return sheeps;
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
	public int countStandardGates() {
		int numberOfStandardGates = 0;
		for (Gate gate : placedGates) {
			if (!gate.isLast()) {
				numberOfStandardGates++;
			}
		}
		return numberOfStandardGates;
	}

	/**
	 * This method takes in input a player and returns the equivalent player in
	 * the board status. This is done by checking the id of player instances.
	 * 
	 * @param player
	 *            (the player that has to be matched with a player in the board
	 *            status)
	 * @return equivalentPlayer (the equivalent player)
	 * @author Andrea
	 */
	public Player getEquivalentPlayer(Player player) {
		for (Player equivalentPlayer : players.getPlayers()) {
			if (player.equals(equivalentPlayer)) {
				return equivalentPlayer;
			}
		}
		return null;
	}

	/**
	 * This method takes in input a sheep and returns the equivalent sheep in
	 * the board status. This is done by checking the id of the sheep.
	 * 
	 * @param sheep
	 *            (the sheep that has to be matched)
	 * @return equivalentSheep (the equivalent sheep)
	 * @author Andrea
	 */
	public Sheep getEquivalentSheep(Sheep sheep) {
		for (Sheep equivalentSheep : sheeps) {
			if (sheep.equals(equivalentSheep)) {
				return equivalentSheep;
			}
		}
		return null;
	}

	/**
	 * This method sets the first player of the game.
	 * 
	 * @param player
	 *            the first player who takes part in the game (determined
	 *            randomly)
	 */
	public void setFirstPlayer(Player player) {
		this.firstPlayer = player;
	}

	/**
	 * This method returns the first player who took part in the game
	 * 
	 * @return firstPlayer
	 */
	public Player getFirstPlayer() {
		return this.firstPlayer;
	}

	/**
	 * This method returns the position of a given player in the array of
	 * player.
	 * 
	 * @param player
	 * @return index the position of the player
	 */
	public int getPositionOfAPlayer(Player player) {
		for (int index = 0; index < players.getPlayers().length; index++) {
			if (player.equals(players.getPlayers()[index])) {
				return index;
			}
		}
		return players.getPlayers().length + 1;
	}

	/**
	 * This method creates a Map that associates to each terrain the number of
	 * sheep that contains. It's used in the CL interface.
	 * 
	 * @return a map that has Terrain as keys and Integer as mapped values.
	 * @author Andrea
	 */
	public Map<Terrain, Integer> calculateNumberOfSheepForEachTerrain() {
		Map<Terrain, Integer> map = new HashMap<Terrain, Integer>();
		int newValue;

		// initialize all the values
		for (Terrain terrain : terrains) {
			map.put(terrain, 0);
		}
		for (Sheep sheep : sheeps) {
			if (map.containsKey(sheep.getPosition())) {
				newValue = map.get(sheep.getPosition()) + 1;
				map.put(sheep.getPosition(), newValue);
			}
		}

		return map;
	}

	/**
	 * This method creates an List containing the ID number of all the free
	 * roads in the map.
	 * 
	 * @return The List of the identifier of free roads
	 * @author Andrea
	 */
	public List<Integer> findFreeRoads() {
		List<Integer> freeRoads = new ArrayList<Integer>();
		Map<Integer, Road> roadMap = getRoadMap().getHashMapOfRoads();

		// finds the free roads
		for (int i = 1; i <= GameConstants.NUMBER_OF_ROADS; i++) {
			if (this.isFreeRoad(roadMap.get(i))) {
				freeRoads.add(i);
			}
		}
		return freeRoads;
	}

	/**
	 * This method returns the position of a player in the array of players
	 * 
	 * @returns an int representing the position (going from 1 to the effective
	 *          number of players)
	 * @author Andrea
	 */
	public int getPlayerNumber(Player player) {
		return players.findPosition(player);
	}

	/**
	 * This method check if the given terrain has all its road occupied by gates
	 * 
	 * @param toCheck
	 *            the terrain to check
	 * @return if all the road adjacent to the given terrain are occupied by
	 *         gates
	 */
	public boolean isClosedByGates(Terrain toCheck) {
		for (Road r : roadMap.findRoadsAdjacentToATerrain(toCheck)) {
			if (isFreeFromGates(r)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return an iterator that goes through the players from the first player
	 *         (the first to play, not the first in the array) to the last
	 */
	public Iterator<Player> getPlayersIterator() {
		return new PlayerOrderedIterator();
	}

	/**
	 * @return an iterator that goes through the players starting from a random
	 *         one
	 */
	public Iterator<Player> getPlayersRandomIterator() {
		return new PlayerRandomIterator();
	}

	/**
	 * This is an iterator that iterates the players of a game from the first in
	 * the turn to the last.
	 */
	private class PlayerOrderedIterator implements Iterator<Player> {
		private Player playerPointed;
		private Integer firstPos;
		private boolean firstCallToDo = true;

		public PlayerOrderedIterator() {
			firstPos = (getPositionOfAPlayer(firstPlayer) - 1 + getPlayers().length)
					% getPlayers().length;
			playerPointed = getPlayers()[firstPos];
		}

		public boolean hasNext() {
			int curPos = getPositionOfAPlayer(playerPointed);
			int nextPos = (curPos + 1) % getPlayers().length;
			return nextPos != (firstPos + 1) % getPlayers().length
					|| firstCallToDo;
		}

		public Player next() {
			if (firstCallToDo) {
				firstCallToDo = false;
			}
			int curPos = getPositionOfAPlayer(playerPointed);
			int nextPos = (curPos + 1) % getPlayers().length;
			playerPointed = getPlayers()[nextPos];
			return playerPointed;
		}

		public void remove() {
			return;
		}
	}

	/**
	 * This is an iterator that goes through the players starting from a random
	 * one
	 */
	private class PlayerRandomIterator implements Iterator<Player> {
		private Player playerPointed;
		private int firstPos;
		private boolean firstCallToDo = true;

		public PlayerRandomIterator() {
			Dice dice = Dice.create();
			firstPos = (dice.roll(getPlayers().length) - 2 + getPlayers().length)
					% getPlayers().length;
			playerPointed = getPlayers()[firstPos];
		}

		public boolean hasNext() {
			int curPos = getPositionOfAPlayer(playerPointed);
			int nextPos = (curPos + 1) % getPlayers().length;
			return nextPos != (firstPos + 1) % getPlayers().length
					|| firstCallToDo;
		}

		public Player next() {
			if (firstCallToDo) {
				firstCallToDo = false;
			}
			int curPos = getPositionOfAPlayer(playerPointed);
			int nextPos = (curPos + 1) % getPlayers().length;
			playerPointed = getPlayers()[nextPos];
			return playerPointed;
		}

		public void remove() {
			return;
		}
	}

	/**
	 * This method returns the cards that a specifi player can buy
	 * 
	 * @param player
	 */
	public List<Card> getCardsPlayerCanBuy(Player player) {

		List<Card> buyableByAPlayer = new ArrayList<Card>();

		ArrayList<Card> allBuyableCards = cardsDeck.getBuyableCards();

		Terrain[] adjacentTerrains = player.getPosition().getAdjacentTerrains();

		for (Card buyable : allBuyableCards) {
			if (buyable.getTerrainType().equals(
					adjacentTerrains[0].getTerrainType())
					|| buyable.getTerrainType().equals(
							adjacentTerrains[1].getTerrainType())) {
				buyableByAPlayer.add(buyable);
			}
		}
		return buyableByAPlayer;
	}

	/**
	 * This method returns the new buyable card having the specified number and
	 * the specified terrain type.
	 * 
	 * @param oldCard
	 *            (the old buyable card having that terrain type)
	 */
	public Card getNewBuyableCardOfATerrainType(Card oldBuyableCard) {
		// if the number of card was 4 there aren't other buyable card of that
		// type in the deck
		if (oldBuyableCard.getNumber() < 4) {
			// looks for the card in the deck
			for (int i = 0; i < cardsDeck.size(); i++) {

				if (cardsDeck.get(i).getNumber() == oldBuyableCard.getNumber() + 1
						&& cardsDeck.get(i).getTerrainType()
								.equals(oldBuyableCard.getTerrainType())) {
					return cardsDeck.get(i);
				}
			}

		}
		return null;
	}
}
