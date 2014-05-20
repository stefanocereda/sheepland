package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.SocketMessages;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;

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
 * TODO I SYNCRONIZED VANNO SINCRONIZZATI SUGLI STREAM
 * 
 * @author Stefano
 * @see ClientHandler
 */
public class SocketClientHandler implements ClientHandler {
	/** The socket linked to the player */
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
	private TimerTask timerTaskStartGame = new TimerTask() {
		public void run() {
			try {
				pingTheClient();
			} catch (ClientDisconnectedException e) {
				notifyClientDisconnection();
			}
		}

		private void notifyClientDisconnection() {
			// TODO Auto-generated method stub
			
		}
	};

	/**
	 * /** Create a clientHandler with a scanner and a writer on the socket
	 * passed; start a timer to periodically ping the client.
	 * 
	 * @param clientSocket
	 *            The socket connected to a client
	 * @throws IOException
	 */
	public SocketClientHandler(Socket clientSocket) throws IOException {
		socket = clientSocket;
		objectIn = new ObjectInputStream(socket.getInputStream());
		objectOut = new ObjectOutputStream(socket.getOutputStream());
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream());

		timer.schedule(timerTaskStartGame, Costants.PING_TIME);
	}

	/**
	 * This method asks the client to send a new move, which is returned. The
	 * client can give an impossible move so it must be checked
	 * 
	 * @return the move returned from the client
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public synchronized Move askMove() throws ClassNotFoundException,
			IOException {
		// Send the message
		out.println(SocketMessages.ASK_NEW_MOVE);
		out.flush();

		Move clientReturned = (Move) objectIn.readObject();
		return clientReturned;
	}

	/**
	 * Send the client a move to be executed. The clients doesn't do any check
	 * on the move so it must be already valid
	 * 
	 * @param moveToExecute
	 *            to move to be executed
	 */
	public synchronized void executeMove(Move moveToExecute) throws IOException {
		out.println(SocketMessages.EXECUTE_MOVE);
		out.flush();

		objectOut.writeObject(moveToExecute);
		objectOut.flush();
	}

	/**
	 * Say to the client that the last move wasn't valid, and waits for a new
	 * one
	 * 
	 * @return a new Move
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public synchronized Move sayMoveIsNotValid() throws ClassNotFoundException,
			IOException {
		out.println(SocketMessages.NOT_VALID_MOVE);
		out.flush();

		Move clientReturned = (Move) objectIn.readObject();
		return clientReturned;
	}

	/**
	 * Send to the client a new status to replace the old one
	 * 
	 * @throws IOException
	 */
	public synchronized void sendNewStatus(BoardStatus newStatus)
			throws IOException {
		out.println(SocketMessages.SEND_NEW_STATUS);
		out.flush();

		objectOut.writeObject(newStatus);
		objectOut.flush();
	}

	/** Ping the client and wait for his answer 
	 * @throws ClientDisconnectedException */
	public synchronized void pingTheClient() throws ClientDisconnectedException {
		out.println(SocketMessages.PING);
		out.flush();

		try {
			Thread.sleep(Costants.PONG_WAITING_TIME);
		} catch (InterruptedException e) {
			System.err
					.println("Error trying to sleep the thread while waiting for the client pong");
			e.printStackTrace();
		}

		if (in.hasNextLine() && in.nextLine().equals(SocketMessages.PONG)) {
			timer.schedule(timerTaskStartGame, Costants.PING_TIME);
		} else
			throw new ClientDisconnectedException();
	}
}
