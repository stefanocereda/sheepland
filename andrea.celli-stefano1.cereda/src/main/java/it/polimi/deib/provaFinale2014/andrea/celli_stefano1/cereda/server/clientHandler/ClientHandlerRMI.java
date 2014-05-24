package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import java.rmi.registry.Registry;
import java.util.logging.Logger;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkHandlerRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarterRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMICostants;

/**
 * The rmi version of a socket handler. This class will be deployed on the
 * server and as it starts it will search for a remote object representing the
 * client's network handler. As the game manager asks this object to perform an
 * action this object will invoke the clien't correspondent method
 * 
 * @author Stefano
 * 
 */
public class ClientHandlerRMI extends ClientHandler {
	/** The remote object */
	private NetworkHandlerRMI clientObject;

	/**
	 * The constructor takes as input the reference of the server starter that
	 * created this method, the name of the client's object and a reference to
	 * the rmi registry. It tries to lookup the client's object. Differently
	 * from the socket version we don't ask for the client's id as that
	 * operation is already done by the so called RMIConnector, actually the id
	 * is the remoteName passed to this constructor
	 * 
	 * @param registry
	 */
	public ClientHandlerRMI(ServerStarterRMI serverStarterRMI,
			String remoteName, Registry registry) {
		super(serverStarterRMI);

		// wait a while before trying to lookup the client
		try {
			Thread.sleep(RMICostants.WAIT_BEFORE_LOOKUP);

			clientObject = (NetworkHandlerRMI) registry.lookup(remoteName);
		} catch (Exception e) {
			Logger log = Logger
					.getLogger("server.clientHandler.ClientHandlerRMI");
			log.severe("Error while looking the remote object" + e);
		}
	}

	public Move askMove() {
		return clientObject.getMove();
	}

	public void executeMove(Move moveToExecute)
			throws ClientDisconnectedException {
		clientObject.executeMove(moveToExecute);

	}

	public Move sayMoveIsNotValid() {
		return clientObject.notifyNotValidMove();
	}

	public void sendNewStatus(BoardStatus newStatus) {
		clientObject.updateStatus(newStatus);

	}

	public void pingTheClient() {
		clientObject.ping();
	}

}