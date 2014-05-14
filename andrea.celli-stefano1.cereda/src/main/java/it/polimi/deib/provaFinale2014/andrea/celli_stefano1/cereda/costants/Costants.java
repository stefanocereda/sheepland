package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.GameType;

/**
 * Class of game costants
 * 
 * @author Stefano
 * 
 */
public class Costants {
	/** the max number of players in a games */
	public static final int MaxPlayersInGame = 6;

	/** the minutes waiting for the max number of players */
	public static final int MinutesWaitingForMaxPlayers = 5;

	/** the type of game to play (default/extended rules) */
	public static final GameType DefaultGameType = GameType.ORIGINAL;

	/** the ip port for the socket server */
	public static final int SocketIpPort = 5000;
}
