package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.socket;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.SocketMessages;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ServerStarter;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientIdentifier;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.DisconnectedClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A socket version of a ClientHandler
 * 
 * @author Stefano
 * @see ClientHandler
 */
public class SocketClientHandler implements ClientHandler {
	/**
	 * A reference to the game that this client is playing, it is used to
	 * suspend the player when it disconnects
	 */
	private GameController game = null;
	/** A reference to the player controlled */
	private Player playerControlled = null;
	/** A reference to the server that created this object */
	private ServerStarter serverStarter;

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

	/** A timer used to ping the client */
	private Timer timer = new Timer();
	/** the timer task to execute at the end of the timers */
	private TimerTask timerTaskPing = new TimerTask() {
		public void run() {
			try {
				pingTheClient();
			} catch (ClientDisconnectedException e) {
				notifyClientDisconnection(e.getGameController(), e.getPlayer());
			}
		}
	};

	/**
	 * /** Create a clientHandler with a scanner and a writer on the socket
	 * passed; start a timer to periodically ping the client.
	 * 
	 * @param clientSocket
	 *            The socket connected to a client
	 * @param server
	 *            the server that creates this object, it's used to notify when
	 *            a client disconnects
	 * @throws IOException
	 */
	public SocketClientHandler(Socket clientSocket, ServerStarter server)
			throws IOException {
		socket = clientSocket;
		out = new PrintWriter(socket.getOutputStream());
		in = new Scanner(socket.getInputStream());
		objectOut = new ObjectOutputStream(socket.getOutputStream());
		objectIn = new ObjectInputStream(socket.getInputStream());

		timer.scheduleAtFixedRate(timerTaskPing, Costants.PING_TIME,
				Costants.PING_TIME);

		serverStarter = server;
	}

	/** Set the game where this client is playing */
	public void setGame(GameController gc) {
		game = gc;
	}

	/** Set the player controlled */
	public void setPlayer(Player p) {
		playerControlled = p;
	}

	/**
	 * Notify the disconnection of a player to the gamecontroller (so it can
	 * suspend the player) and to the server starter so it can wait for this
	 * player to reconnect and let it play in the same game
	 * 
	 * @param gc
	 *            the game controller to notify
	 * @param pc
	 *            the client disconnected
	 */
	public void notifyClientDisconnection(GameController gc, Player pc) {
		gc.notifyDisconnection(pc);
		serverStarter.notifyDisconnection(new DisconnectedClient(
				getIdentifier(), pc, gc));
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
		// Send the message
		out.println(SocketMessages.ASK_NEW_MOVE);
		out.flush();

		Move clientReturned = null;

		try {
			clientReturned = (Move) objectIn.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(game, playerControlled);
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
		out.println(SocketMessages.EXECUTE_MOVE);
		out.flush();

		try {
			objectOut.writeObject(moveToExecute);
			objectOut.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(game, playerControlled);
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
		out.println(SocketMessages.NOT_VALID_MOVE);
		out.flush();

		Move clientReturned;

		try {
			clientReturned = (Move) objectIn.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(game, playerControlled);
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
		out.println(SocketMessages.SEND_NEW_STATUS);
		out.flush();

		try {
			objectOut.writeObject(newStatus);
			objectOut.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(game, playerControlled);
		}
	}

	/**
	 * Ping the client and wait for his answer
	 * 
	 * @throws ClientDisconnectedException
	 */
	public synchronized void pingTheClient() throws ClientDisconnectedException {
		out.println(SocketMessages.PING);
		out.flush();

		try {
			Thread.sleep(Costants.PONG_WAITING_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (!in.hasNextLine())
			throw new ClientDisconnectedException(game, playerControlled);
	}

	public ClientIdentifier getIdentifier() {
		return new ClientIdentifier(socket.getInetAddress(), socket.getPort());
	}

	public Player getPlayer() {
		return playerControlled;
	}
}
