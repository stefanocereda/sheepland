package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.SocketMessages;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
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
 * with the client by sending messages and objects on the socket.
 * 
 * @author Stefano
 */
public class ClientHandlerSocket extends ClientHandler {
	/** The object input on the socket */
	private ObjectInputStream in;
	/** The object writer on the socket */
	private ObjectOutputStream out;

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
			id = creator.getNewToken();
		}
		// send back the right id
		out.writeInt(id);
		out.flush();
	}

	public Move askMove() throws ClientDisconnectedException,
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

	public void executeMove(Move moveToExecute)
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

	public Move sayMoveIsNotValid() throws ClassNotFoundException,
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

	public void sendNewStatus(BoardStatus newStatus)
			throws ClientDisconnectedException {

		try {
			out.reset();
			out.writeUTF(SocketMessages.SEND_NEW_STATUS);
			out.writeObject(newStatus);
			out.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public Road askInitialPosition() throws ClientDisconnectedException,
			ClassNotFoundException {
		try {
			out.writeUTF(SocketMessages.ASK_INITIAL_POSITION);
			out.flush();

			return (Road) in.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public Road askSecondInitialPosition() throws ClientDisconnectedException,
			ClassNotFoundException {
		try {
			out.writeUTF(SocketMessages.ASK_SECOND_INITIAL_POSITION);
			out.flush();

			return (Road) in.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public void pingTheClient() throws ClientDisconnectedException {
		try {
			out.writeUTF(SocketMessages.PING);
			out.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public void setCurrentPlayer(Player newCurrentPlayer)
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

	public void sendWinners(List<Player> winners)
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

	public void notifyControlledPlayer(Player controlled)
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

	public boolean chooseShepherd() throws ClientDisconnectedException {
		try {
			out.writeUTF(SocketMessages.CHOOSE_SHEPHERD);
			out.flush();
			return in.readBoolean();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public void sendShepherd(boolean usingSecond)
			throws ClientDisconnectedException {
		try {
			out.writeUTF(SocketMessages.NOTIFY_SHEPHERD);
			out.writeBoolean(usingSecond);
			out.flush();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public List<MarketOffer> askMarketOffers() throws ClassNotFoundException,
			ClientDisconnectedException {
		try {
			out.writeUTF(SocketMessages.ASK_MARKET_OFFERS);
			out.flush();
			return (List<MarketOffer>) in.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}

	public List<MarketBuy> askMarketBuy(List<MarketOffer> offers)
			throws ClassNotFoundException, ClientDisconnectedException {
		try {
			out.writeUTF(SocketMessages.ASK_MARKET_BUY);
			out.writeObject(offers);
			out.flush();
			return (List<MarketBuy>) in.readObject();
		} catch (IOException e) {
			throw new ClientDisconnectedException(gameController,
					controlledPlayer, e);
		}
	}
}
