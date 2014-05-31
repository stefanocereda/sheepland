package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.DefaultConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.NetworkConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.TimeConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;

/**
 * This is class only launch the server
 * 
 * @author Stefano
 */
public class ServerMainClass {
	/** the max number of players in a game */
	static final int maxPlayers = GameConstants.MAX_PLAYERS_IN_A_GAME;
	/** the type of game (original/extended rules) */
	static final GameType gameType = DefaultConstants.DEFAULT_GAME_TYPE;
	/** the minutes waiting for maxPlayers */
	static final int minutesWaiting = TimeConstants.MINUTES_WAITING_FOR_MAX_PLAYERS;

	/** the ip port for socket server */
	static final int socketPort = NetworkConstants.SOCKET_IP_PORT;
	/** the ip port for the rmi registry */
	static final int rmiPort = NetworkConstants.REGISTRY_IP_PORT;

	/**
	 * The main method of the server, launches the server
	 * 
	 * @param args
	 *            Unused
	 */
	public static void main(String[] args) {
		ServerStarter server = new ServerStarter(maxPlayers, gameType,
				minutesWaiting, socketPort, rmiPort);

		(new Thread(server)).start();
	}
}
