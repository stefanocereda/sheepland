package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.SocketMessages;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;

/**
 * A socket version of a client handler
 * 
 * @author Stefano
 */
public class ClientHandlerSocket extends ClientHandler {
	/** The socket linked to the client */
	private Socket socket;
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
	 * creates a socket client handler, get the id sent by the client and send
	 * back a new id (or the same if different from zero)
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
		this.socket = socket;

		// open the streams
		out = new PrintWriter(socket.getOutputStream());
		in = new Scanner(socket.getInputStream());
		objectOut = new ObjectOutputStream(socket.getOutputStream());
		objectIn = new ObjectInputStream(socket.getInputStream());

		// ask the id
		id = in.nextInt();
		// if the id is zero choose a new one
		if (id == 0)
			id = ++created;
		// send back the right id
		out.print(id);
		out.flush();
	}

	/**
	 * This method asks the client to send a new move, which is returned. The
	 * client can give an impossible move so it must be checked
	 * 
	 * @return the move returned from the client
	 * @throws ClassNotFoundException
	 * @throws ClientDisconnectedException
	 */
	public synchronized Move askMove() throws ClassNotFoundException,
			ClientDisconnectedException {
		clearInput();

		// Send the message
		out.println(SocketMessages.ASK_NEW_MOVE);
		out.flush();

		Move clientReturned = null;

		try {
			clientReturned = (Move) objectIn.readObject();
		} catch (IOException e) {
			Logger log = Logger
					.getLogger("server.clientHandler.ClientHandlerSocket");
			log.severe("SOCKET ERROR: " + e);
			throw new ClientDisconnectedException(gameController,
					controlledPlayer);
		}
		return clientReturned;
	}

	/**
	 * Send the client a move to be executed. The clients doesn't do any check
	 * on the move so it must be already valid
	 * 
	 * @param moveToExecute
	 *            to move to be executed
	 * @throws ClientDisconnectedException
	 */
	public synchronized void executeMove(Move moveToExecute)
			throws ClientDisconnectedException {
		clearInput();

		out.println(SocketMessages.EXECUTE_MOVE);
		out.flush();

		try {
			objectOut.writeObject(moveToExecute);
			objectOut.flush();
		} catch (IOException e) {
			Logger log = Logger
					.getLogger("server.clientHandler.ClientHandlerSocket");
			log.severe("SOCKET ERROR: " + e);
			throw new ClientDisconnectedException(gameController,
					controlledPlayer);
		}
	}

	/**
	 * Say to the client that the last move wasn't valid, and waits for a new
	 * one
	 * 
	 * @return a new Move
	 * @throws ClassNotFoundException
	 * @throws ClientDisconnectedException
	 */
	public synchronized Move sayMoveIsNotValid() throws ClassNotFoundException,
			ClientDisconnectedException {
		clearInput();

		out.println(SocketMessages.NOT_VALID_MOVE);
		out.flush();

		Move clientReturned;

		try {
			clientReturned = (Move) objectIn.readObject();
		} catch (IOException e) {
			Logger log = Logger
					.getLogger("server.clientHandler.ClientHandlerSocket");
			log.severe("SOCKET ERROR: " + e);
			throw new ClientDisconnectedException(gameController,
					controlledPlayer);
		}

		return clientReturned;
	}

	/**
	 * Send to the client a new status to replace the old one
	 * 
	 * @throws ClientDisconnectedException
	 * 
	 */
	public synchronized void sendNewStatus(BoardStatus newStatus)
			throws ClientDisconnectedException {
		clearInput();

		out.println(SocketMessages.SEND_NEW_STATUS);
		out.flush();

		try {
			objectOut.writeObject(newStatus);
			objectOut.flush();
		} catch (IOException e) {
			Logger log = Logger
					.getLogger("server.clientHandler.ClientHandlerSocket");
			log.severe("SOCKET ERROR: " + e);
			throw new ClientDisconnectedException(gameController,
					controlledPlayer);
		}
	}

	/**
	 * Ping the client and wait for his answer
	 * 
	 * @throws ClientDisconnectedException
	 */
	public synchronized void pingTheClient() throws ClientDisconnectedException {
		clearInput();

		out.println(SocketMessages.PING);
		out.flush();

		try {
			Thread.sleep(Costants.PONG_WAITING_TIME);
		} catch (InterruptedException e) {
			Logger log = Logger
					.getLogger("server.clientHandler.ClientHandlerSocket");
			log.fine("thread interrupted: " + e);
			timer.cancel();
		}

		if (!in.hasNextLine() || !in.nextLine().equals(SocketMessages.PONG))
			throw new ClientDisconnectedException(gameController,
					controlledPlayer);
	}

	/** This method empties the input scanner */
	private void clearInput() {
		while (in.hasNext())
			in.next();
	}
}
