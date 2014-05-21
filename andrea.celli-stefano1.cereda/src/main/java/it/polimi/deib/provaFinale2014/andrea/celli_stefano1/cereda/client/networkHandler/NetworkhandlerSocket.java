package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.SocketMessages;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.client.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;

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
	 */
	public NetworkhandlerSocket(InetSocketAddress serverAddress,
			GameControllerClient controller) {
		socket = new Socket(serverAddress.getAddress(), serverAddress.getPort());
		this.controller = controller;
	}

	/**
	 * This method start listening to the socket for the server orders and sends
	 * them to the client's controller
	 */
	public void run() {
		// we loop waiting for server commands
		while (true) {
			String command = in.nextLine();

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
		}
	}

	private void getAndUpdateStatus() {
		BoardStatus newStatus = (BoardStatus) objectIn.readObject();
		controller.upDateStatus(newStatus);
	}

	private void notifyNotValidMove() {
		controller.
	}

	private void getAndExecuteNewMove() {
		Move newMove = (Move) objectIn.readObject();
		controller.executeMove(newMove);
	}

	private void askAndSendNewMove() {
		controller.
	}
}
