/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayerDouble;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;

import java.util.List;
import java.util.logging.Level;

/**
 * This controller is used for games with basic rules but only two players.
 * Contains only the methods that are different from the basic GameController.
 * We have two clients and two players, but each player have two positions.
 * 
 * @author Stefano
 * 
 */
public class GameControllerTwoPlayers extends GameController {

	/** The passed array must contain two client handlers */
	public GameControllerTwoPlayers(List<ClientHandler> playerClients) {
		super(playerClients);
	}

	/**
	 * Create all the players and add them to the board, notify to the client
	 * handlers the reference to the game and to their player. This method is
	 * overriden because the initial money are different
	 */
	@Override
	protected void addPlayersToGame() {
		// number of players = number of client handlers
		for (ClientHandler client : clients) {
			// create a player and add it to the board
			Player p = new PlayerDouble();
			p.setID();
			p.setMoney(GameConstants.INITIAL_MONEY_TWO_PLAYERS);
			boardStatus.addPlayerToBoardStatus(p);

			// notify the client handler
			client.setGame(this);
			client.setPlayer(p);
		}
	}

	/**
	 * Ask the initial position of the second shepherd to all the players until
	 * they choose a correct one. If they disconnect we choose a random
	 * position. After every choose we create a move representing the choose and
	 * send it to all the clients
	 */
	private void askSecondInitialPositionToAllPlayers() {
		for (ClientHandler ch : clients) {
			Road initial = null;
			((PlayerDouble) ch.getPlayer()).setShepherd(true);

			boardStatus.setCurrentPlayer(boardStatus.getEquivalentPlayer(ch
					.getPlayer()));
			super.sendNewCurrentPlayerToAllPlayers();
			notifyChosenShepherdToAllClients(true);

			if (!ch.getPlayer().isSuspended()) {
				try {

					do {
						initial = ch.askSecondInitialPosition();
					} while (!boardStatus.isFreeRoad(initial));

				} catch (ClientDisconnectedException e) {
					String message = "A client disconnected";
					logger.log(Level.INFO, message, e);
					catchDisconnection(e.getPlayer());

					initial = chooseRandomPositionForAPlayer();
				} catch (ClassNotFoundException e) {
					String message = "A client is not aligned with the communication protocol, suspending it";
					logger.log(Level.INFO, message, e);
					ch.getPlayer().suspend();
					ch.getPlayer().setNotConnected();

					initial = chooseRandomPositionForAPlayer();
				}

			} else {
				initial = chooseRandomPositionForAPlayer();
			}

			Move move = new MovePlayer(ch.getPlayer(), initial);
			sendMoveToAllPlayers(move);
			move.execute(boardStatus);
		}
	}

	/**
	 * Ask to the current player if he wants to use the first or the second
	 * shepherd
	 */
	private boolean askShepherdToCurrentPlayer() {
		ClientHandler client = null;

		for (ClientHandler ch : clients) {
			if (ch.getPlayer().equals(boardStatus.getCurrentPlayer())) {
				client = ch;
				break;
			}
		}

		if (!client.getPlayer().isSuspended()) {
			try {
				return client.chooseShepherd();
			} catch (ClientDisconnectedException e) {
				String message = "A client disconnected";
				logger.log(Level.INFO, message, e);
				catchDisconnection(e.getPlayer());
			}
		}
		return false;
	}

	/**
	 * This method notifies all the client which shepherd the current player has
	 * chosen to use
	 */
	private void notifyChosenShepherdToAllClients(boolean usingSecond) {
		for (ClientHandler ch : clients) {
			try {
				ch.sendShepherd(usingSecond);
			} catch (ClientDisconnectedException e) {
				String message = "A client disconnected";
				logger.log(Level.INFO, message, e);
				catchDisconnection(e.getPlayer());
			}
		}
	}

	/**
	 * The second action of a game is asking all the clients to choose an
	 * initial position. After that we delete the list of moves for all the
	 * clients. This method is overridden because we have to ask two positions.
	 * 
	 * @return moveTheBlackSheep
	 */
	@Override
	public String retrieveInitialPositions() {
		askInitialPositionToAllPlayers();
		askSecondInitialPositionToAllPlayers();

		for (Player p : boardStatus.getPlayers()) {
			p.deleteLastMoves();
		}

		return "moveTheBlackSheep";
	}

	/**
	 * move the black sheep according to the sheepland's rules.
	 * 
	 * @returns retrieveShepherdFromCurrentPlayer
	 * @author Andrea
	 */
	@Override
	public String moveTheBlackSheep() {
		super.moveTheBlackSheep();

		// go on by asking the current player to choose his shepherd
		return "retrieveShepherdFromCurrentPlayer";
	}

	/**
	 * Ask the current player to choose his shepherd for this turn and set it.
	 * Then notifies all the clients
	 */
	public String retrieveShepherdFromCurrentPlayer() {
		boolean controllingSecond = askShepherdToCurrentPlayer();

		((PlayerDouble) boardStatus.getCurrentPlayer())
				.setShepherd(controllingSecond);

		notifyChosenShepherdToAllClients(controllingSecond);

		return "retrieveMoveFromCurrentPlayer";
	}

	/**
	 * This method notifies the current player to all the clients, then goes on
	 * by moving the black sheep or by asking the current player to choose a
	 * shepherd
	 */
	@Override
	public String notifyNewCurrentPlayer() {
		if (super.notifyNewCurrentPlayer().equals(
				"retrieveMoveFromCurrentPlayer"))
			return "retrieveShepherdFromCurrentPlayer";
		else
			return "moveTheBlackSheep";
	}
}
