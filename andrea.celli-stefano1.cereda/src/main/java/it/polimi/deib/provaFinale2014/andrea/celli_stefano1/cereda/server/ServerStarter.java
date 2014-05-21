package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientIdentifier;

import java.io.IOException;

/**
 * Interface for a server
 * 
 * @author Stefano
 * 
 */
public interface ServerStarter {
	/**
	 * Start the server
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException;

	/** Notify to the server starter that a client disconnected */
	public void notifyDisconnection(ClientIdentifier clientIdentifier, GameController gameController, Player disconnectedPlayer);
}
