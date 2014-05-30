package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.RMIInterface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.TimeConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The rmi version of a client handler. This class will be deployed on the
 * server and as it starts it will search for a remote object representing the
 * client's network handler. As the game manager asks this object to perform an
 * action this object will invoke the clien't correspondent method
 * 
 * @author Stefano
 * 
 */
public class ClientHandlerRMI extends ClientHandler {
	/** The remote object */
	private RMIInterface clientObject;

	/** A logger */
	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * The constructor takes as input the reference of the server starter that
	 * will handle reconnection for the client, the name of the client's object
	 * and a reference to the rmi registry. It tries to lookup the client's
	 * object. Differently from the socket version we don't ask for the client's
	 * id as that operation is already done by the so called RMIConnector,
	 * actually the id is the remoteName passed to this constructor. Differently
	 * from the socket version the methods aren't synchronized because there
	 * won't be problems to ping while asking a move
	 * 
	 * @param registry
	 * @throws NotBoundException
	 * @throws RemoteException
	 * @throws AccessException
	 */
	public ClientHandlerRMI(ServerStarter serverStarter, String remoteName,
			Registry registry) throws AccessException, RemoteException,
			NotBoundException {
		super(serverStarter);

		// wait a while before trying to lookup the client
		try {
			Thread.sleep(TimeConstants.RMI_WAITING_LOOKUP);
		} catch (InterruptedException e) {
			String message = "The thread has been stopped while waiting before looking up the client's object";
			logger.log(Level.INFO, message, e);
		}
		clientObject = (RMIInterface) registry.lookup(remoteName);
	}

	public Move askMove() throws ClientDisconnectedException {
		try {
			return clientObject.getMove();
		} catch (RemoteException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public void executeMove(Move moveToExecute)
			throws ClientDisconnectedException {
		try {
			clientObject.executeMove(moveToExecute);
		} catch (RemoteException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public Move sayMoveIsNotValid() throws ClientDisconnectedException {
		try {
			return clientObject.notifyNotValidMove();
		} catch (RemoteException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public void sendNewStatus(BoardStatus newStatus)
			throws ClientDisconnectedException {
		try {
			clientObject.updateStatus(newStatus);
		} catch (RemoteException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public Road askInitialPosition() throws ClientDisconnectedException {
		try {
			return clientObject.askInitialPosition();
		} catch (RemoteException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public void pingTheClient() throws ClientDisconnectedException {
		try {
			clientObject.ping();
		} catch (RemoteException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public void setCurrentPlayer(Player newCurrentPlayer)
			throws ClientDisconnectedException {
		try {
			clientObject.setCurrentPlayer(newCurrentPlayer);
		} catch (RemoteException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public void sendWinners(List<Player> winners)
			throws ClientDisconnectedException {
		try {
			clientObject.sendWinners(winners);
		} catch (RemoteException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public void notifyControlledPlayer(Player controlled)
			throws ClientDisconnectedException {
		try {
			clientObject.notifyControlledPlayer(controlled);
		} catch (RemoteException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}
}