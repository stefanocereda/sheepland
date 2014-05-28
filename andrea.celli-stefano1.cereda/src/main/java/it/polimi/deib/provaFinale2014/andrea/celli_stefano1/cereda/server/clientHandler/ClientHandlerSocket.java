package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.SocketMessages;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.TimeConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;

/**
 * A socket version of a client handler. This class manages the communication
 * with the client by sending messages and objects on the socket. All the
 * methods are synchronized in order to avoid the situation where we send a ping
 * and get back a move
 * 
 * @author Stefano
 */
public class ClientHandlerSocket extends ClientHandler {
	/** The scanner on the socket */
	private Scanner in;
	/** The object input on the socket */
	private ObjectInputStream objectIn;
	/** The socket writer */
	private PrintWriter out;
	/** The object writer on the socket */
	private ObjectOutputStream objectOut;

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
		out = new PrintWriter(socket.getOutputStream());
		in = new Scanner(socket.getInputStream());
		objectOut = new ObjectOutputStream(socket.getOutputStream());
		objectIn = new ObjectInputStream(socket.getInputStream());

		// ask the id
		id = Integer.parseInt(in.nextLine());
		// if the id is zero choose a new one
		if (id == 0)
			id = ++created;
		// send back the right id
		out.println(id);
		out.flush();
	}

	public synchronized Move askMove() throws ClientDisconnectedException,
			ClassNotFoundException {

		// Send the message
		out.println(SocketMessages.ASK_NEW_MOVE);
		out.flush();

		// get the move
		Move clientReturned = null;
		try {
			clientReturned = (Move) objectIn.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}

		return clientReturned;
	}

	public synchronized void executeMove(Move moveToExecute)
			throws ClientDisconnectedException {

		out.println(SocketMessages.EXECUTE_MOVE);
		out.flush();

		try {
			objectOut.writeObject(moveToExecute);
			objectOut.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized Move sayMoveIsNotValid() throws ClassNotFoundException,
			ClientDisconnectedException {

		out.println(SocketMessages.NOT_VALID_MOVE);
		out.flush();

		Move clientReturned;
		try {
			clientReturned = (Move) objectIn.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}

		return clientReturned;
	}

	public synchronized void sendNewStatus(BoardStatus newStatus)
			throws ClientDisconnectedException {

		out.println(SocketMessages.SEND_NEW_STATUS);
		out.flush();

		try {
			objectOut.writeObject(newStatus);
			objectOut.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized Road askInitialPosition()
			throws ClientDisconnectedException, ClassNotFoundException {
		out.println(SocketMessages.ASK_INITIAL_POSITION);
		out.flush();

		try {
			return (Road) objectIn.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized void pingTheClient() throws ClientDisconnectedException {

		out.println(SocketMessages.PING);
		out.flush();

		try {
			Thread.sleep(TimeConstants.PONG_WAITING_TIME);
		} catch (InterruptedException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}

		if (!in.hasNextLine() || !in.nextLine().equals(SocketMessages.PONG))
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, null);
	}

	public synchronized void setCurrentPlayer(Player newCurrentPlayer)
			throws ClientDisconnectedException {
		out.println(SocketMessages.SET_CURRENT_PLAYER);
		out.flush();

		try {
			objectOut.writeObject(newCurrentPlayer);
			objectOut.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public synchronized void sendWinners(ArrayList<Player> winners)
			throws ClientDisconnectedException {
		out.println(SocketMessages.SEND_WINNERS);
		out.flush();

		try {
			objectOut.writeObject(winners);
			objectOut.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}
}
