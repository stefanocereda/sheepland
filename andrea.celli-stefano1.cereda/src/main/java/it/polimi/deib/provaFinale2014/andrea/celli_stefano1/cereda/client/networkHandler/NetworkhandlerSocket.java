package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

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

/** A socket version of a network handler */
public class NetworkhandlerSocket implements Runnable {
	/** A reference to the client's controller */
	GameControllerClient controller;
	/** The socket connected to the client */
	private Socket socket;
	/** A printwriter on the socket */
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
	public NetworkhandlerSocket(InetSocketAddress serverAddress,
			GameControllerClient controller) throws IOException {
		// open the socket
		socket = new Socket(serverAddress.getAddress(), serverAddress.getPort());
		// set the controller
		this.controller = controller;
		// open the streams
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream());
		objectIn = new ObjectInputStream(socket.getInputStream());
		objectOut = new ObjectOutputStream(socket.getOutputStream());
	}

	/**
	 * This method start listening to the socket for the server orders and sends
	 * them to the client's controller
	 */
	public void run() {
		// we loop waiting for server commands
		while (true) {
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
				} else if (command.equals(SocketMessages.PING)) {
					replyToPing();
				}
			} catch (IOException e) {
				// we are disconnected
			} catch (ClassNotFoundException e) {
				// cosa cazzo è successo
			}
		}
	}

	/** This method replies to the server's ping with a pong */
	private void replyToPing() {
		out.println(SocketMessages.PONG);
		out.flush();
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
