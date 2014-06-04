/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayerDouble;
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
	 * Ask the initial position to all the players until they choose a correct
	 * one. If they disconnect we choose a random position. After every choose
	 * we create a move representing the choose and send it to all the clients.
	 * This method is overridden because we have have to ask two positions for
	 * each player
	 */
	@Override
	protected void askInitialPositionToAllPlayers() {
		for (ClientHandler ch : clients) {

			for (int i = 1; i <= 2; i++) {
				Road initial = null;

				if (!ch.getPlayer().isSuspended()) {
					try {

						do {
							initial = ch.askInitialPosition();
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

				Move move = new MovePlayerDouble(ch.getPlayer(), initial, i);
				sendMoveToAllPlayers(move);
				move.execute(boardStatus);
			}
		}
	}
}
