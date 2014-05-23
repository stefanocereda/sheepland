package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants;

import java.net.InetSocketAddress;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameType;

/**
 * Class of game constants
 * 
 * @author Stefano
 * 
 */
public class Costants {
	/** the max number of players in a games */
	public static final int MAX_PLAYERS_IN_A_GAME = 4;

	/** the minutes waiting for the max number of players */
	public static final int MINUTES_WAITING_FOR_MAX_PLAYERS = 5;

	/** the type of game to play (default/extended rules) */
	public static final GameType DEFAULT_GAME_TYPE = GameType.ORIGINAL;

	/** the ip port for the socket server */
	public static final int SOCKET_IP_PORT = 5000;

	/** the ip port for the rmi registry */
	public static final int REGISTRY_IP_PORT = 6000;

	/** the milliseconds between each ping to a client */
	public static final long PING_TIME = 10 * 1000;

	/** The milliseconds waiting for the client pong */
	public static final long PONG_WAITING_TIME = 5 * 1000;

	/** The number of non final gates that can be placed in a game */
	public static final int NUMBER_OF_NON_FINAL_GATES = 20;

	/** The time in milliseconds waiting for the reconnection of a client */
	public static final long WAITING_FOR_CLIENT_RECONNECT = 1 * 60 * 1000;// 1'

	/**
	 * The time in milliseconds that the client waits before trying to reconnect
	 */
	public static final long WAIT_FOR_RECONNECTION = 1 * 1000;

	/** The address of the server */
	public static final InetSocketAddress SERVER_SOCKET_ADDRESS = new InetSocketAddress(
			"localhost", SOCKET_IP_PORT);

	/** Hide the default constructor */
	private Costants() {
	}
}
