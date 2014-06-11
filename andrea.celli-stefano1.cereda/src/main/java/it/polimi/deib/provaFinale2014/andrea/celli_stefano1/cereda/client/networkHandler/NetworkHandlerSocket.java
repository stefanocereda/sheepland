package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.SocketMessages;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;

/**
 * A socket version of a network handler. It connects to the server and starts
 * listening for commands
 * 
 * @author Stefano
 */
public class NetworkHandlerSocket extends NetworkHandler {
	/** The server socket address */
	private InetSocketAddress serverAddress;

	/** The socket connected to the server */
	private Socket socket = new Socket();
	/** An object input on the socket */
	private ObjectInputStream in;
	/** An object output on the stream */
	private ObjectOutputStream out;

	/**
	 * the constructor of a socket network handler takes as parameter the
	 * address of the server and a reference to the client's game controller
	 * 
	 * @param token
	 * 
	 * @throws IOException
	 *             if is not possible to open the socket
	 */
	public NetworkHandlerSocket(InetSocketAddress serverAddress,
			GameControllerClient controller, int token) throws IOException {
		super(controller, token);

		// save the server address
		this.serverAddress = serverAddress;
		// try to connect
		connect();
	}

	/**
	 * This method connects the socket to the serverAddress
	 * 
	 * @throws IOException
	 */
	@Override
	protected void connect() throws IOException {
		socket.connect(serverAddress);

		// open the streams
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());

		// if we are connected we send our id and waits for server response
		out.writeInt(myId);
		out.flush();
		myId = in.readInt();
		logger.log(Level.INFO, "You have received the token: " + myId);
	}

	/**
	 * This method start listening to the socket for the server orders and sends
	 * them to the client's controller.
	 */
	public void start() {
		// we loop waiting for server commands
		while (true) {
			String command;

			try {
				command = in.readUTF();
				if (command.equals(SocketMessages.PING)) {
					;
				} else if (command.equals(SocketMessages.ASK_NEW_MOVE)) {
					askAndSendNewMove();
				} else if (command.equals(SocketMessages.EXECUTE_MOVE)) {
					getAndExecuteNewMove();
				} else if (command.equals(SocketMessages.NOT_VALID_MOVE)) {
					controller.notifyNotValidMove();
					askAndSendNewMove();
				} else if (command.equals(SocketMessages.SEND_NEW_STATUS)) {
					getAndUpdateStatus();
				} else if (command.equals(SocketMessages.SET_CURRENT_PLAYER)) {
					getAndSetNewCurrentPlayer();
				} else if (command.equals(SocketMessages.SEND_WINNERS)) {
					getWinners();
					break;
				} else if (command.equals(SocketMessages.ASK_INITIAL_POSITION)) {
					chooseInitialPosition();
				} else if (command
						.equals(SocketMessages.NOTIFY_CONTROLLED_PLAYER)) {
					getControlledPlayer();
				} else if (command.equals(SocketMessages.CHOOSE_SHEPHERD)) {
					chooseShepherd();
				} else if (command
						.equals(SocketMessages.ASK_SECOND_INITIAL_POSITION)) {
					chooseSecondInitialPosition();
				} else if (command.equals(SocketMessages.NOTIFY_SHEPHERD)) {
					getShepherd();
				} else if (command.equals(SocketMessages.ASK_MARKET_OFFERS)) {
					askMarketOffers();
				} else if (command.equals(SocketMessages.ASK_MARKET_BUY)) {
					askMarketBuy();
				} else {
					logger.log(Level.SEVERE,
							"Received an unrecognized message: " + command);
				}
			} catch (IOException e) {
				// we are disconnected
				// log the exception
				String message = "We are disconnected";
				logger.log(Level.FINE, message, e);
				notifyDisconnection();
				reconnect();
			} catch (ClassNotFoundException e) {
				// there was a problem in the network protocol
				String message = "There's been a communication problem, we're not aligned with the server. Shutting down";
				logger.log(Level.SEVERE, message, e);
				notifyDisconnection();
				break;
			}

		}
	}

	/**
	 * This method is used to let the client know which shepherd is using the
	 * current player
	 * 
	 * @throws IOException
	 */
	private void getShepherd() throws IOException {
		boolean usingSecond = in.readBoolean();
		controller.notifyShepherd(usingSecond);
	}

	/**
	 * This method receives the controlled player and give it to the game
	 * controller
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void getControlledPlayer() throws IOException,
			ClassNotFoundException {
		Player controlled = (Player) in.readObject();
		controller.setControlledPlayer(controlled);
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
		BoardStatus newStatus = (BoardStatus) in.readObject();
		controller.upDateStatus(newStatus);
	}

	/** This method receive a Player and sets it as the new current player */
	private void getAndSetNewCurrentPlayer() throws IOException,
			ClassNotFoundException {
		Player newCurrentPlayer = (Player) in.readObject();
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
		Move newMove = (Move) in.readObject();
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
		out.writeObject(newMove);
		out.flush();
	}

	/**
	 * This method receives an ArrayList of Players representing the winners, it
	 * notifies the interface and closes the network
	 * 
	 * @throws ClassNotFoundException
	 */
	private void getWinners() throws IOException, ClassNotFoundException {

		@SuppressWarnings("unchecked")
		List<Player> winners = (List<Player>) in.readObject();

		controller.notifyWinners(winners);

		in.close();
		out.close();
	}

	/** This method ask the user to choose the initial position and returns it */
	private void chooseInitialPosition() throws IOException {
		Road toReturn = controller.chooseInitialPosition();
		out.writeObject(toReturn);
		out.flush();
	}

	/**
	 * This method ask the user to choose the initial position of the second
	 * shepherd
	 */
	private void chooseSecondInitialPosition() throws IOException {
		Road toReturn = controller.chooseSecondInitialPosition();
		out.writeObject(toReturn);
		out.flush();
	}

	/**
	 * Ask the user to choose a shepherd
	 * 
	 * @throws IOException
	 */
	private void chooseShepherd() throws IOException {
		out.writeBoolean(controller.getShepherd());
		out.flush();
	}

	/** Ask the user to choose some cards to sell */
	private void askMarketOffers() throws IOException {
		out.writeObject(controller.askMarketOffers());
		out.flush();
	}

	/**
	 * Ask the user to choose a list of cards to buy
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void askMarketBuy() throws ClassNotFoundException, IOException {
		List<MarketOffer> offers = (List<MarketOffer>) in.readObject();
		out.writeObject(controller.askMarketBuy(offers));
		out.flush();
	}
}
