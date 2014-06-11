package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.RoadMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
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
	 * This hashmap links colors to terrains.
	 */
	private Map<Color, Terrain> colorsAndTerrain = new HashMap<Color, Terrain>();

	/**
	 * This hashmap links colors and roads
	 */
	private Map<Color, Road> colorsAndRoad = new HashMap<Color, Road>();

	/**
	 * This hashmap links terrains and sheep's labels origins
	 */
	private Map<Terrain, Point> sheepOrigins = new HashMap<Terrain, Point>();

	/**
	 * This hashmap links terrains and wolf's labels origins
	 */
	private Map<Terrain, Point> wolfOrigins = new HashMap<Terrain, Point>();

	/**
	 * This hashmap links terrains and ram's labels origins
	 */
	private Map<Terrain, Point> ramOrigins = new HashMap<Terrain, Point>();

	/**
	 * This hashmap links terrains and lamb's labels origins
	 */
	private Map<Terrain, Point> lambOrigins = new HashMap<Terrain, Point>();

	/**
	 * This hashmap links terrains and black sheep's labels origins
	 */
	private Map<Terrain, Point> blackSheepOrigins = new HashMap<Terrain, Point>();

	/**
	 * This hashmap links roads and pawn's labels origins
	 * 
	 * @TODO define the starting origins
	 **/
	private Map<Road, Point> pawnOrigins = new HashMap<Road, Point>();

	//
	//
	//
	// methods start here
	//
	//
	//
	/**
	 * The constructor just creates an empty instance of the Linker class
	 */
	public Linker() {

	}

	/**
	 * Initialize all the Linker's attribute
	 * 
	 * @param BoardStatus
	 *            (the initial status of the game)
	 * @param mapDimension
	 *            (The dimension of the map(without possible borders))
	 */
	public void initLinker(BoardStatus boardStatus, Dimension mapDimension) {

		// pawns and, therefore, player names and colors
		givePawns(boardStatus.getPlayers());

		// colors-terrains
		initColorsAndTerrain();

		// colors-Roads
		initColorsAndRoad(boardStatus.getRoadMap());

		// creates hashMap of origins
		calculateOrigins(mapDimension, (HashMap) boardStatus.getRoadMap()
				.getHashMapOfRoads());
	}

	/**
	 * This method creates an istance of the class "originCreator" and
	 * calculates the points considering the size of the map.
	 * 
	 * @param mapDimension
	 * @param the
	 *            hashmap of roads contained in the board status
	 */
	private void calculateOrigins(Dimension mapDimension,
			HashMap<Integer, Road> roads) {
		// the class containing the methods to create the hashmaps with the
		// right points
		CoordinatesConverter converter = new CoordinatesConverter(mapDimension);

		sheepOrigins = converter.calculateSheepOrigins();

		wolfOrigins = converter.calculateWolfOrigins();

		lambOrigins = converter.calculateLambOrigins();

		ramOrigins = converter.calculateRamOrigins();

		blackSheepOrigins = converter.calculateBlackSheepOrigins();

		pawnOrigins = converter.calculatePawnOrigins(roads);

	}

	/**
	 * This methods creates the relations between player and pawns.
	 * 
	 * @param the
	 *            array of player taking part in the game.
	 */
	private void givePawns(Player[] players) {
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

	/**
	 * This method intializes the HashMap colorsAndTerrain
	 */
	private void initColorsAndTerrain() {

		colorsAndTerrain.put(new Color(52, 32, 33), Terrain.P1);
		colorsAndTerrain.put(new Color(108, 72, 72), Terrain.P2);
		colorsAndTerrain.put(new Color(128, 88, 88), Terrain.P3);
		colorsAndTerrain.put(new Color(56, 36, 37), Terrain.C2);
		colorsAndTerrain.put(new Color(60, 40, 41), Terrain.C1);
		colorsAndTerrain.put(new Color(104, 72, 73), Terrain.C3);
		colorsAndTerrain.put(new Color(104, 68, 68), Terrain.M1);
		colorsAndTerrain.put(new Color(64, 40, 40), Terrain.M2);
		colorsAndTerrain.put(new Color(68, 44, 44), Terrain.M3);
		colorsAndTerrain.put(new Color(100, 68, 69), Terrain.D1);
		colorsAndTerrain.put(new Color(72, 48, 48), Terrain.D2);
		colorsAndTerrain.put(new Color(116, 76, 76), Terrain.D3);
		colorsAndTerrain.put(new Color(112, 76, 76), Terrain.L1);
		colorsAndTerrain.put(new Color(116, 80, 80), Terrain.L2);
		colorsAndTerrain.put(new Color(120, 80, 80), Terrain.L3);
		colorsAndTerrain.put(new Color(128, 84, 85), Terrain.W1);
		colorsAndTerrain.put(new Color(124, 84, 84), Terrain.W2);
		colorsAndTerrain.put(new Color(132, 88, 89), Terrain.W3);

	}

	/**
	 * This method initializes the hashMap colorsAndRoad
	 * 
	 * @param roadMap
	 *            (the object containing the hashmap of roads)
	 */
	private void initColorsAndRoad(RoadMap roadMap) {

		HashMap<Integer, Road> roads = (HashMap<Integer, Road>) roadMap
				.getHashMapOfRoads();

		colorsAndRoad.put(new Color(254, 0, 0), roads.get(1));
		colorsAndRoad.put(new Color(0, 96, 255), roads.get(2));
		colorsAndRoad.put(new Color(1, 202, 255), roads.get(3));
		colorsAndRoad.put(new Color(255, 180, 0), roads.get(4));
		colorsAndRoad.put(new Color(255, 245, 0), roads.get(5));
		colorsAndRoad.put(new Color(134, 255, 0), roads.get(6));
		colorsAndRoad.put(new Color(0, 255, 120), roads.get(7));
		colorsAndRoad.put(new Color(0, 255, 195), roads.get(8));
		colorsAndRoad.put(new Color(71, 0, 96), roads.get(9));
		colorsAndRoad.put(new Color(142, 1, 255), roads.get(10));
		colorsAndRoad.put(new Color(3, 0, 255), roads.get(11));
		colorsAndRoad.put(new Color(207, 0, 255), roads.get(12));
		colorsAndRoad.put(new Color(175, 0, 255), roads.get(13));
		colorsAndRoad.put(new Color(255, 0, 220), roads.get(14));
		colorsAndRoad.put(new Color(64, 0, 84), roads.get(15));
		colorsAndRoad.put(new Color(76, 0, 100), roads.get(16));
		colorsAndRoad.put(new Color(77, 0, 104), roads.get(17));
		colorsAndRoad.put(new Color(116, 0, 151), roads.get(18));
		colorsAndRoad.put(new Color(79, 0, 109), roads.get(19));
		colorsAndRoad.put(new Color(81, 0, 105), roads.get(20));
		colorsAndRoad.put(new Color(64, 0, 87), roads.get(21));
		colorsAndRoad.put(new Color(69, 0, 93), roads.get(22));
		colorsAndRoad.put(new Color(59, 0, 80), roads.get(23));
		colorsAndRoad.put(new Color(255, 0, 56), roads.get(24));
		colorsAndRoad.put(new Color(254, 0, 122), roads.get(25));
		colorsAndRoad.put(new Color(96, 0, 128), roads.get(26));
		colorsAndRoad.put(new Color(92, 0, 124), roads.get(27));
		colorsAndRoad.put(new Color(88, 0, 120), roads.get(28));
		colorsAndRoad.put(new Color(89, 0, 116), roads.get(29));
		colorsAndRoad.put(new Color(84, 0, 112), roads.get(30));
		colorsAndRoad.put(new Color(112, 0, 147), roads.get(31));
		colorsAndRoad.put(new Color(88, 0, 120), roads.get(32));
		colorsAndRoad.put(new Color(92, 0, 124), roads.get(33));
		colorsAndRoad.put(new Color(96, 0, 133), roads.get(34));
		colorsAndRoad.put(new Color(140, 40, 172), roads.get(35));
		colorsAndRoad.put(new Color(100, 0, 135), roads.get(36));
		colorsAndRoad.put(new Color(104, 0, 139), roads.get(37));
		colorsAndRoad.put(new Color(128, 12, 163), roads.get(38));
		colorsAndRoad.put(new Color(108, 0, 145), roads.get(39));
		colorsAndRoad.put(new Color(115, 0, 155), roads.get(40));
		colorsAndRoad.put(new Color(124, 4, 161), roads.get(41));
		colorsAndRoad.put(new Color(132, 24, 169), roads.get(42));
	}
}
