package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.SocketMessages;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;

/**
 * A socket version of a network handler
 * 
 * @author Stefano
 */
public class NetworkHandlerSocket extends NetworkHandler {
	/** The server socket address */
	private InetSocketAddress serverAddress;

	/** The socket connected to the server */
	private Socket socket = new Socket();
	/** A printWriter on the socket */
	private PrintWriter out;
	/** A scanner on the socket */
	private Scanner in;
	/** An object input on the socket */
	private ObjectInputStream objectIn;
	/** An object output on the stream */
	private ObjectOutputStream objectOut;

	/**
	 * the constructor of a socket network handler takes as parameter the
	 * address of the server and a reference to the client's game controller
	 * 
	 * @throws IOException
	 *             if is not possible to open the socket
	 */
	public NetworkHandlerSocket(InetSocketAddress serverAddress,
			GameControllerClient controller) throws IOException {
		super(controller);

		// save the server address
		this.serverAddress = serverAddress;
		// try to connect
		connect();
	}

	/**
	 * This method connects the socket to the serverAddress and start a timer to
	 * reply ping
	 * 
	 * @throws IOException
	 */
	protected void connect() throws IOException {
		socket.connect(serverAddress);

		// open the streams
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream());
		objectIn = new ObjectInputStream(socket.getInputStream());
		objectOut = new ObjectOutputStream(socket.getOutputStream());

		// if we are connected we send our id and waits for server response
		out.println(myId);
		out.flush();
		myId = Integer.parseInt(in.nextLine());

		// if we are connected start the ping timer
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTaskPong, Costants.PING_TIME,
				Costants.PING_TIME);
	}

	/**
	 * This method start listening to the socket for the server orders and sends
	 * them to the client's controller.
	 */
	public synchronized void start() {
		// we loop waiting for server commands
		while (true) {
			String command = in.nextLine();

			try {
				if (command.equals(SocketMessages.ASK_NEW_MOVE)) {
					askAndSendNewMove();
				} else if (command.equals(SocketMessages.EXECUTE_MOVE)) {
					getAndExecuteNewMove();
				} else if (command.equals(SocketMessages.NOT_VALID_MOVE)) {
					controller.notifyNotValidMove();
					askAndSendNewMove();
				} else if (command.equals(SocketMessages.SEND_NEW_STATUS)) {
					getAndUpdateStatus();
				} else if (command.equals(SocketMessages.PING)) {
					// reply with a pong
					out.println(SocketMessages.PONG);
					out.flush();
				} else if (command.equals(SocketMessages.SET_CURRENT_PLAYER))
					getAndSetNewCurrentPlayer();
			} catch (IOException e) {
				// we are disconnected
				// log the exception
				logger.severe("DISCONNECTED: " + e);
				// try to reconnect and stop checking for ping
				timer.cancel();
				notifyDisconnection();
				reconnect();
			} catch (ClassNotFoundException e) {
				// there was a problem in the network protocol
				logger.severe("CLASS NOT FOUND, PROBABLY PROBLEM IN THE NETWORK PROTOCOL: "
						+ e);
			}

		}
	}

	/**
	 * This method periodically checks for ping and replies with pong. It runs
	 * on a parallel thread in order to be able to respond to ping even when the
	 * "principal" method is stuck executing commands
	 */
	public synchronized void checkConnectivity() {
		if (in.hasNext(SocketMessages.PING)) {
			// throw away the ping
			in.nextLine();
			// reply with a pong
			out.println(SocketMessages.PONG);
			out.flush();
		}
	}

	/**
	 * This method retrieves a new board status from the socket and forwards it
	 * to the controller
	 * 
	 * @throws IOException
	 *             when not connected
	 * @throws ClassNotFoundException
	 *             if something went wrong in the communication protocol
	 */
	private void getAndUpdateStatus() throws ClassNotFoundException,
			IOException {
		BoardStatus newStatus = (BoardStatus) objectIn.readObject();
		controller.upDateStatus(newStatus);
	}

	/** This methos receive a Player and sets it as the new current player */
	private void getAndSetNewCurrentPlayer() throws IOException,
			ClassNotFoundException {
		Player newCurrentPlayer = (Player) objectIn.readObject();
		controller.setCurrentPlayer(newCurrentPlayer);
	}

	/**
	 * This method retrieves a new move from the socket and forwards it to the
	 * controller so it can execute it
	 * 
	 * @throws IOException
	 *             when not connected
	 * @throws ClassNotFoundException
	 *             if something went wrong in the communication protocol
	 */
	private void getAndExecuteNewMove() throws ClassNotFoundException,
			IOException {
		Move newMove = (Move) objectIn.readObject();
		controller.executeMove(newMove);
	}

	/**
	 * This method asks the controller to get a new move and sends it to the
	 * server
	 * 
	 * @throws IOException
	 *             when not connected
	 */
	private void askAndSendNewMove() throws IOException {
		Move newMove = controller.getNewMove();
		objectOut.writeObject(newMove);
		objectOut.flush();
	}
}
