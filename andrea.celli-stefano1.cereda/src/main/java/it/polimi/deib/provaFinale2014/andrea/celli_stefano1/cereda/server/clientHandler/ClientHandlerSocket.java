package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.SocketMessages;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * A socket version of a client handler. This class manages the communication
 * with the client by sending messages and objects on the socket. All the
 * methods are synchronized in order to avoid the situation where we send a ping
 * and get back a move
 * 
 * @author Stefano
 */
public class ClientHandlerSocket extends ClientHandler {
	/** The object input on the socket */
	private ObjectInputStream in;
	/** The object writer on the socket */
	private ObjectOutputStream out;

	/** The number of this objects created */
	private static int created = 0;

	/**
	 * The constructor creates a socket client handler, gets the id sent by the
	 * client and sends back a new id (or the same if different from zero)
	 * 
	 * @param creator
	 *            the server starter that created this object
	 * @param socket
	 *            a socket connected to the client
	 * @throws IOException
	 *             when fails to open the stream
	 */
	public ClientHandlerSocket(ServerStarter creator, Socket socket)
			throws IOException {
		super(creator);

		// open the streams
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());

		// ask the id
		id = in.readInt();
		// if the id is zero choose a new one
		if (id == 0) {
			id = ++created;
		}
		// send back the right id
		out.writeInt(id);
		out.flush();
	}

	public synchronized Move askMove() throws ClientDisconnectedException,
			ClassNotFoundException {
		try {
			// Send the message
			out.writeUTF(SocketMessages.ASK_NEW_MOVE);
			out.flush();

			// get the move
			return (Move) in.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized void executeMove(Move moveToExecute)
			throws ClientDisconnectedException {

		try {
			out.writeUTF(SocketMessages.EXECUTE_MOVE);
			out.writeObject(moveToExecute);
			out.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized Move sayMoveIsNotValid() throws ClassNotFoundException,
			ClientDisconnectedException {
		try {
			out.writeUTF(SocketMessages.NOT_VALID_MOVE);
			out.flush();
			return (Move) in.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized void sendNewStatus(BoardStatus newStatus)
			throws ClientDisconnectedException {

		try {
			out.writeUTF(SocketMessages.SEND_NEW_STATUS);
			out.writeObject(newStatus);
			out.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized Road askInitialPosition()
			throws ClientDisconnectedException, ClassNotFoundException {
		try {
			out.writeUTF(SocketMessages.ASK_INITIAL_POSITION);
			out.flush();

			return (Road) in.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized void pingTheClient() throws ClientDisconnectedException {
		try {
			out.writeUTF(SocketMessages.PING);
			out.flush();
			in.readUTF();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized void setCurrentPlayer(Player newCurrentPlayer)
			throws ClientDisconnectedException {
		try {
			out.writeUTF(SocketMessages.SET_CURRENT_PLAYER);
			out.writeObject(newCurrentPlayer);
			out.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized void sendWinners(List<Player> winners)
			throws ClientDisconnectedException {
		try {
			out.writeUTF(SocketMessages.SEND_WINNERS);
			out.writeObject(winners);
			out.flush();
			timerTaskPing.cancel();
			in.close();
			out.close();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized void notifyControlledPlayer(Player controlled)
			throws ClientDisconnectedException {
		try {
			out.writeUTF(SocketMessages.NOTIFY_CONTROLLED_PLAYER);
			out.writeObject(controlled);
			out.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized boolean chooseShepherd()
			throws ClientDisconnectedException {
		try {
			out.writeUTF(SocketMessages.CHOOSE_SHEPHERD);
			out.flush();
			return in.readBoolean();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}
}
