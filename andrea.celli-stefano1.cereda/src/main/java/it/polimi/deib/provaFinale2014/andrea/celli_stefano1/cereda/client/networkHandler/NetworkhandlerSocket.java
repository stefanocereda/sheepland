package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.SocketMessages;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.client.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/** A socket version of a network handler */
public class NetworkhandlerSocket implements Runnable {
	/** A reference to the client's controller */
	GameControllerClient controller;
	/** The socket connected to the server */
	private Socket socket = new Socket();
	/** The server address */
	private InetSocketAddress serverAddress;
	/** A printWriter on the socket */
	private PrintWriter out;
	/** A scanner on the socket */
	private Scanner in;
	/** An object input on the socket */
	private ObjectInputStream objectIn;
	/** An object output on the stream */
	private ObjectOutputStream objectOut;

	/** A timer used to reply to server's ping */
	private Timer timer = new Timer();
	/** the timer task to execute at the end of the timers */
	private TimerTask timerTaskPong = new TimerTask() {
		public void run() {
			checkForPing();
		}
	};

	/**
	 * the constructor of a socket network handler takes as parameter the
	 * address of the server and a reference to the client's game controller
	 * 
	 * @throws IOException
	 *             if is not possible to open the socket
	 */
	public NetworkhandlerSocket(InetSocketAddress serverAddress,
			GameControllerClient controller) throws IOException {
		// save the server address
		this.serverAddress = serverAddress;
		// save the controller
		this.controller = controller;
		// try to connect
		connect();
		// open the streams
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream());
		objectIn = new ObjectInputStream(socket.getInputStream());
		objectOut = new ObjectOutputStream(socket.getOutputStream());
	}

	/**
	 * This method start listening to the socket for the server orders and sends
	 * them to the client's controller. It also starts a timer to check server's
	 * ping (on another thread)
	 */
	public void run() {
		// we loop waiting for server commands
		while (true) {
			// check for commands that aren't ping, the pings are handled by
			// another thread
			if (in.hasNextLine() && !in.hasNext(SocketMessages.PING)) {
				String command = in.nextLine();

				try {
					if (command.equals(SocketMessages.ASK_NEW_MOVE)) {
						askAndSendNewMove();
					} else if (command.equals(SocketMessages.EXECUTE_MOVE)) {
						getAndExecuteNewMove();
					} else if (command.equals(SocketMessages.NOT_VALID_MOVE)) {
						notifyNotValidMove();
						askAndSendNewMove();
					} else if (command.equals(SocketMessages.SEND_NEW_STATUS)) {
						getAndUpdateStatus();
					}
				} catch (IOException e) {
					// we are disconnected
					// log the exception
					Logger log = Logger.getAnonymousLogger();
					log.severe("DISCONNECTED: " + e);
					// try to reconnect and stop checking for ping
					timer.cancel();
					notifyDisconnection();
					tryToReconnect();
				} catch (ClassNotFoundException e) {
					Logger log = Logger.getAnonymousLogger();
					log.severe("CLASS NOT FOUND, PROBABLY PROBLEM IN THE NETWORK PROTOCOL: "
							+ e);
				}
			}
		}
	}

	/**
	 * This method tells the controller to inform the user that we're
	 * disconnected and trying to reconnect
	 */
	private void notifyDisconnection() {
		controller.notifyDisconnection();
	}

	/**
	 * This method tries to reconnect until it is possible, with a little
	 * waiting in the while
	 */
	private void tryToReconnect() {
		try {
			connect();
		} catch (IOException e) {
			Logger log = Logger.getAnonymousLogger();
			log.fine("UNABLE TO RECONNECT: " + e);

			try {
				Thread.sleep(Costants.WAIT_FOR_RECONNECTION);
			} catch (InterruptedException e1) {
				log.fine("Thread interrupted: " + e);
			}

			tryToReconnect();
		}

	}

	/**
	 * This method connects the socket to the serverAddress and start a timer to
	 * reply pings
	 * 
	 * @throws IOException
	 */
	private void connect() throws IOException {
		socket.connect(serverAddress);
		// start the ping timer
		timer.scheduleAtFixedRate(timerTaskPong, Costants.PING_TIME,
				Costants.PING_TIME);
	}

	/** This method check for ping and replies with pong */
	private void checkForPing() {
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

	/**
	 * This method asks the controller to notify the graphics that the last move
	 * wasn't valid
	 */
	private void notifyNotValidMove() {
		controller.notifyNotValidMove();
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
