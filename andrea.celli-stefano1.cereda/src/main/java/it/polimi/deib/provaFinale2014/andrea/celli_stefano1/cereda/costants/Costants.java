package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.GameType;

/**
 * Class of game constants
 * 
 * @author Stefano
 * 
 */
public class Costants {
	/** the max number of players in a games */
	public static final int MAX_PLAYERS_IN_A_GAME = 6;

	/** the minutes waiting for the max number of players */
	public static final int MINUTES_WAITING_FOR_MAX_PLAYERS = 5;

	/** the type of game to play (default/extended rules) */
	public static final GameType DEFAULT_GAME_TYPE = GameType.ORIGINAL;

	/** the ip port for the socket server */
	public static final int SOCKET_IP_PORT = 5000;

	/** the milliseconds between each ping to a client */
	public static final long PING_TIME = 10 * 1000;

	/** The milliseconds waiting for the client pong */
	public static final long PONG_WAITING_TIME = 5 * 1000;

	/** Hide the default constructor */
	private Costants() {
	}
}
