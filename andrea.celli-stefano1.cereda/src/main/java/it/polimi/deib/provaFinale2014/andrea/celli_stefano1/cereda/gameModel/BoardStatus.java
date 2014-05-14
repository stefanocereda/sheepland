package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import java.util.ArrayList;

/**
 * A complete representation of the game panel, territories, roads, players and
 * animals
 * 
 * @author Stefano
 * 
 *         TODO all
 */
public class BoardStatus {
	/** The wolf */
	private Wolf wolf;
	/** The list of gates, either finally and not */
	private ArrayList<Gate> gates;
	/** The black sheep */
	private BlackSheep blackSheep;
	/** The list of sheep */
	private ArrayList<Sheep> sheeps;
	/** The array of players */
	private Player[] players;
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

}
