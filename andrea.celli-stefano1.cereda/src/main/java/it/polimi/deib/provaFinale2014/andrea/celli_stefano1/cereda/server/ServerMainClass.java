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
	/** Hide the default constructor */
	private ServerMainClass() {
	}

	/**
	 * The main method of the server, launches the server
	 * 
	 * @param args
	 *            Unused
	 */
	public static void main(String[] args) {
		ServerStarter server = new ServerStarter(
				GameConstants.MAX_PLAYERS_IN_A_GAME,
				DefaultConstants.DEFAULT_GAME_TYPE,
				TimeConstants.WAITING_FOR_MAX_PLAYERS,
				NetworkConstants.SOCKET_IP_PORT,
				NetworkConstants.REGISTRY_IP_PORT);

		new Thread(server).start();
	}
}
