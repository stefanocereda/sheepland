package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.TimeConstants;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This abstract class is used for the methods in common between RMI and socket
 * 
 * @author Stefano
 * 
 */
public abstract class NetworkHandler {
	/** A reference to the client's game controller */
	protected GameControllerClient controller;

	/** An identificator of this client, used to perform reconnection */
	protected Integer myId = 0;

	/** A logger */
	protected Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * The constructor of a network handler is used to set the reference to the
	 * client's game controller
	 */
	public NetworkHandler(GameControllerClient gameController) {
		controller = gameController;
	}

	/** This method keep trying to reconnect until it can or it's stopped */
	protected void reconnect() {
		try {
			Thread.sleep(TimeConstants.WAIT_FOR_RECONNECTION);
			connect();
		} catch (InterruptedException e) {
			String message = "Thread interrupted while trying to reconnect";
			logger.log(Level.SEVERE, message, e);
		} catch (IOException e) {
			String message = "Unable to reconnect";
			logger.log(Level.INFO, message, e);
			reconnect();
		}
	}

	/** Connect to the server */
	protected abstract void connect() throws RemoteException, IOException;

	/**
	 * This method tells the controller to inform the user that we're
	 * disconnected and trying to reconnect
	 */
	protected void notifyDisconnection() {
		controller.notifyDisconnection();
	}
}
