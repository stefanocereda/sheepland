package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.TimeConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Dice;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.TerrainType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the basic game controller, manages the communication from clients to
 * the game model, also taking care of handling the move checking and execution.
 * It's like a state machine that steps into the game process
 * 
 * @author Stefano,Andrea
 */

public class GameController implements Runnable {
	/** The array of client handlers */
	protected List<ClientHandler> clients;

	/** The actual status of this game */
	protected BoardStatus boardStatus;

	/** A logger */
	private static final Logger LOGGER = Logger.getLogger(GameController.class
			.getName());

	/**
	 * GameController constructor, saves the list of clients
	 * 
	 * @param playerClients
	 *            The list of clientHandlers for the players
	 */
	public GameController(List<ClientHandler> playerClients) {
		clients = playerClients;
	}

	/** Start the game */
	public void run() {
		initializeAll();
		manageGame();
		notifyWinners();
	}

	//
	//
	// HERE STARTS THE METHODS RELATED WITH THE GAME INITIALIZATION
	//
	//

	/**
	 * Initialize the ruleChecker, the moveCostCalculator, the moveExecutor and
	 * the board. Send this initial status to all the players
	 */
	protected void initializeAll() {
		boardStatus = new BoardStatus(clients.size());

		// initialize the board
		initBoard();
	}

	/**
	 * Initialize the board following the rules on the second page
	 * (Preparazione)
	 */
	protected void initBoard() {
		addPlayersToGame();
		initSheeps();
		initBlackSheep();
		initCards();
		deleteRemainingInitialCards();
		chooseFirstPlayer();
	}

	/**
	 * Create all the players and add them to the board, notify to the client
	 * handlers the reference to the game and to their player
	 */
	protected void addPlayersToGame() {
		// number of players = number of client handlers
		for (ClientHandler client : clients) {
			// create a player and add it to the board
			Player p = new Player();
			p.setID();
			p.setMoney(GameConstants.INITIAL_MONEY);
			boardStatus.addPlayerToBoardStatus(p);

			// notify the client handler
			client.setGame(this);
			client.setPlayer(p);
		}
	}

	/**
	 * Create all the sheep and put them on the map, one per terrain excluding
	 * sheepsburg
	 */
	protected void initSheeps() {
		for (Terrain terrain : Terrain.values()) {
			if (!terrain.equals(Terrain.SHEEPSBURG)) {
				Sheep sheep = new Sheep(terrain);
				sheep.setID();
				boardStatus.addSheep(sheep);
			}
		}
	}

	/** Create a BlackSheep and put it in sheepsburg */
	protected void initBlackSheep() {
		BlackSheep blackSheep = new BlackSheep(Terrain.SHEEPSBURG);
		blackSheep.setID();
		boardStatus.addSheep(blackSheep);
		boardStatus.addBlackSheepToBoardStatus(blackSheep);
	}

	/** Give an initial card to each player */
	protected void initCards() {
		Deck deck = boardStatus.getDeck();
		Player[] players = boardStatus.getPlayers();

		for (Player p : players) {
			p.addCard(deck.extractInitialCard());
		}
	}

	/** Delete all the remaining initial cards in the deck */
	protected void deleteRemainingInitialCards() {
		boardStatus.getDeck().deleteRemainingInitialCards();
	}

	/** Extract an initial player and notify to the boardstatus */
	protected void chooseFirstPlayer() {
		Dice dice = Dice.create();
		Player[] players = boardStatus.getPlayers();
		int sorted = dice.roll(players.length) - 1;
		Player firstPlayer = players[sorted];
		boardStatus.setCurrentPlayer(firstPlayer);
		boardStatus.setFirstPlayer(firstPlayer);
	}

	//
	//
	// HERE STARTS THE METHODS RELATED WITH THE CLIENT DISCONNETION/RECONNECTION
	//
	//

	/**
	 * This method handles the disconnection of a player following the specs of
	 * the project. It waits for a while that the client reconnects and then go
	 * on with or without him
	 * 
	 * @param pc
	 *            the player to suspend
	 */
	public void notifyDisconnection(Player pc) {
		// a client disconnected
		pc.setNotConnected();

		// first of all we suspend this game for a fixed time
		try {
			Thread.sleep(TimeConstants.WAITING_FOR_CLIENT_RECONNECT);
		} catch (InterruptedException e) {
			String message = "The thread has been interrupted while waiting for a client reconnection";
			LOGGER.log(Level.SEVERE, message, e);
			return;
		}

		// now check if client has been reconnected by the server, if yes go on,
		// otherwise suspend it and go on without
		if (!pc.isConnected()) {
			pc.suspend();
		}
	}

	/**
	 * This method handles the disconnection of a client. It searches the
	 * correspondent client handler and sends it the disconnection, the client
	 * handler will send the notification both to the server acceptor(so the
	 * client can reconnect) and to the notifyDisconnection method of this class
	 * that will actually suspend the player.
	 */
	protected void catchDisconnection(Player p) {
		searchClientHandler(p).notifyClientDisconnection();
	}

	/** This method reconnect a player changing his ConnectionHandler */
	public void notifyReconnection(Player player, ClientHandler newClientHandler) {
		// search the old client handler and change with the new one
		ClientHandler old = searchClientHandler(player);
		clients.set(clients.indexOf(old), newClientHandler);

		// set the parameters
		newClientHandler.setGame(this);
		newClientHandler.setPlayer(player);

		// now wake the player
		player.deleteLastMoves();
		player.setConnected();
		player.resume();

		// and give him his player and the current status
		try {
			newClientHandler.notifyControlledPlayer(player);
			newClientHandler.sendNewStatus(boardStatus);
		} catch (ClientDisconnectedException e) {
			String message = "A client disconnected";
			LOGGER.log(Level.INFO, message, e);
			catchDisconnection(e.getPlayer());
		}
	}

	/** @return the client handler of the given player */
	protected ClientHandler searchClientHandler(Player player) {
		for (ClientHandler ch : clients) {
			if (ch.getPlayer().equals(player)) {
				return ch;
			}
		}
		return null;
	}

	//
	//
	// HERE STARTS THE METHODS RELATED WITH CLIENT COMMUNICATIONS
	//
	//

	/** Send the status to all the not suspended players */
	protected void sendStatusToAllPlayers() {
		for (ClientHandler client : clients) {
			if (!client.getPlayer().isSuspended()) {
				try {
					client.sendNewStatus(boardStatus);
				} catch (ClientDisconnectedException e) {
					String message = "A client disconnected";
					LOGGER.log(Level.INFO, message, e);
					catchDisconnection(e.getPlayer());
				}
			}
		}
	}

	/**
	 * Communicates the new current player to the clients (in this way they
	 * don't have to wait for a move to know who's playing).
	 * 
	 * @author Andrea
	 */
	protected void sendNewCurrentPlayerToAllPlayers() {
		for (ClientHandler client : clients) {
			if (!client.getPlayer().isSuspended()) {
				try {
					client.setCurrentPlayer(boardStatus.getCurrentPlayer());
				} catch (ClientDisconnectedException e) {
					String message = "A client disconnected";
					LOGGER.log(Level.INFO, message, e);
					catchDisconnection(e.getPlayer());
				}
			}
		}
	}

	/** Send to all the clients a move to be executed */
	protected void sendMoveToAllPlayers(Move moveToExecute) {
		for (ClientHandler client : clients) {
			if (!client.getPlayer().isSuspended()) {
				try {
					client.executeMove(moveToExecute);
				} catch (ClientDisconnectedException e) {
					String message = "A client disconnected";
					LOGGER.log(Level.INFO, message, e);
					catchDisconnection(e.getPlayer());
				}
			}
		}
	}

	/** Tell to each client who is his controlled player */
	private void sendControlledPlayerToAllPlayers() {
		for (ClientHandler ch : clients) {
			if (!ch.getPlayer().isSuspended()) {
				try {
					ch.notifyControlledPlayer(ch.getPlayer());
				} catch (ClientDisconnectedException e) {
					String message = "A client disconnected";
					LOGGER.log(Level.INFO, message, e);
					catchDisconnection(e.getPlayer());
				}
			}
		}
	}

	/**
	 * This method communicates the winners to the clients. This message clearly
	 * states that the games's over. Therefore clients will start to tear down
	 * their connection.
	 * 
	 * @param winners
	 *            the ArrayList containing the winners
	 * @author Andrea
	 */
	private void sendWinnersToAllPlayers(List<Player> winners) {
		for (ClientHandler client : clients) {
			if (!client.getPlayer().isSuspended()) {
				try {
					client.sendWinners(winners);
				} catch (ClientDisconnectedException e) {
					String message = "A client disconnected";
					LOGGER.log(Level.INFO, message, e);
					catchDisconnection(e.getPlayer());
				}
			}
		}
	}

	/**
	 * asks the client for a new move and returns it.
	 * 
	 * @author Andrea
	 */

	private Move askMoveToCurrentPlayer() {
		// search the client handler of the current player
		ClientHandler client = searchClientHandler(boardStatus
				.getCurrentPlayer());

		if (!client.getPlayer().isSuspended()) {
			try {
				Move toReturn = client.askMove();
				toReturn.setID();
				return toReturn;
			} catch (ClientDisconnectedException e) {
				String message = "A client disconnected";
				LOGGER.log(Level.INFO, message, e);
				catchDisconnection(e.getPlayer());
			} catch (ClassNotFoundException e) {
				String message = "A client is not aligned with the communication protocol, suspending it";
				LOGGER.log(Level.INFO, message, e);
				client.getPlayer().suspend();
				client.getPlayer().setNotConnected();
			}
		}

		return null;
	}

	/**
	 * Tell the current player that the last move wasn't valid, returns the new
	 * move
	 */
	private Move reAskMoveToCurrentPlayer() {
		// search the client handler of the current player
		ClientHandler client = searchClientHandler(boardStatus
				.getCurrentPlayer());

		if (!client.getPlayer().isSuspended()) {
			try {
				Move toReturn = client.sayMoveIsNotValid();
				toReturn.setID();
				return toReturn;
			} catch (ClientDisconnectedException e) {
				String message = "A client disconnected";
				LOGGER.log(Level.INFO, message, e);
				catchDisconnection(e.getPlayer());
			} catch (ClassNotFoundException e) {
				String message = "A client is not aligned with the communication protocol, suspending it";
				LOGGER.log(Level.INFO, message, e);
				client.getPlayer().suspend();
				client.getPlayer().setNotConnected();
			}
		}

		return null;
	}

	/**
	 * Ask the initial position to all the players until they choose a correct
	 * one. If they disconnect we choose a random position. After every choose
	 * we create a move representing the choose and send it to all the clients
	 */
	protected void askInitialPositionToAllPlayers() {
		for (ClientHandler ch : clients) {
			Road initial = null;

			if (!ch.getPlayer().isSuspended()) {
				try {

					do {
						initial = ch.askInitialPosition();
					} while (!boardStatus.isFreeRoad(initial));

				} catch (ClientDisconnectedException e) {
					String message = "A client disconnected";
					LOGGER.log(Level.INFO, message, e);
					catchDisconnection(e.getPlayer());

					initial = chooseRandomPositionForAPlayer();
				} catch (ClassNotFoundException e) {
					String message = "A client is not aligned with the communication protocol, suspending it";
					LOGGER.log(Level.INFO, message, e);
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

	/** This method returns the first free road it finds */
	protected Road chooseRandomPositionForAPlayer() {
		for (Road r : boardStatus.getRoadMap().getHashMapOfRoads().values()) {
			if (boardStatus.isFreeRoad(r)) {
				return r;
			}
		}
		return null;
	}

	//
	//
	// HERE STARTS THE METHODS RELATED WITH THE GAME MANAGING
	//
	//
	/**
	 * Manage the game process. The system has to behave as a FSM. For that
	 * reason each method invoked determines,depending on the result of its
	 * computation, the next method to be called. Every method that is called in
	 * manageGame() returns a string containing the name of the next method.
	 * Using getDeclaredMethod(...) it gets the Method Object which is later
	 * invoked using invoke(...). The process terminates when goOn() return the
	 * string "gameOver". At this point we return to the run method
	 * 
	 * @author Andrea
	 */
	private void manageGame() {
		// the first turn starts notifying the controlled player
		String nextMethod = "notifyControlledPlayers";

		while (true) {
			try {
				// finds the next method
				Method method = getClass()
						.getMethod(nextMethod, (Class[]) null);
				// invoke the next method
				nextMethod = (String) method.invoke(this, (Object[]) null);
			} catch (Exception e) {
				// Catching a generic exception is not a good idea, but with
				// java 1.5 there's a problem with the exceptions thrown by
				// method.invoke
				String message = "Problems managing the game";
				LOGGER.log(Level.SEVERE, message, e);
				break;
			}

			if (nextMethod.equals("gameOver")) {
				break;
			}
		}
	}

	/**
	 * Notify to all the client their player, send the board status for the
	 * first time and return retrieveInitialPositions
	 */
	public String notifyControlledPlayers() {
		sendControlledPlayerToAllPlayers();
		sendStatusToAllPlayers();
		return "retrieveInitialPositions";
	}

	/**
	 * The second action of a game is asking all the clients to choose an
	 * initial position. After that we delete the list of moves for all the
	 * clients
	 * 
	 * @return moveTheBlackSheep
	 */
	public String retrieveInitialPositions() {
		askInitialPositionToAllPlayers();

		for (Player p : boardStatus.getPlayers()) {
			p.deleteLastMoves();
		}

		return "moveTheBlackSheep";
	}

	/**
	 * move the black sheep according to the sheepland's rules.
	 * 
	 * @returns retrieveMoveFromCurrentPlayer
	 * @author Andrea
	 */
	public String moveTheBlackSheep() {
		Dice dice = Dice.create();
		int diceResult = dice.roll(GameConstants.NUMBER_OF_DICE_SIDES);

		// find the roads near the black sheep
		Set<Road> roadsNearBlackSheep = boardStatus.getRoadMap()
				.findRoadsAdjacentToATerrain(
						boardStatus.getBlackSheep().getPosition());

		// check if there is a road that has the same number obtained with the
		// dice
		for (Road road : roadsNearBlackSheep) {
			if (road.getBoxValue() == diceResult
					&& boardStatus.isFreeRoad(road)) {
				// find the new terrain
				for (Terrain t : road.getAdjacentTerrains()) {
					if (!boardStatus.getBlackSheep().getPosition().equals(t)) {
						// creates a new move and send it to clients
						executeMove(new MoveBlackSheep(t,
								boardStatus.getBlackSheep()));
						break;
					}
				}
			}
		}

		// go on by asking the move to the current player
		return "retrieveMoveFromCurrentPlayer";
	}

	/**
	 * ask the current player to send a move, if the client disconnects we check
	 * for reconnection and eventually re ask the move, otherwise choose another
	 * player. If the process goes all right we check if the move is valid, if
	 * yes we execute the move and return checkIfPlayerFinishedTurn, if no we
	 * return reRetrieveMoveFromCurrentPlayer
	 */
	public String retrieveMoveFromCurrentPlayer() {
		// get a move from the current player
		Move returned = askMoveToCurrentPlayer();

		// if the returned move is null the client has disconnected
		if (returned == null) {
			// if the player is back reAsk the move
			if (boardStatus.getCurrentPlayer().isConnected()) {
				return "retrieveMoveFromCurrentPlayer";
			}
			// otherwise go on with another player
			return "goOn";
		}

		// otherwise try to execute the move
		if (returned.isValid(boardStatus)) {
			executeMove(returned);
			return "checkIfPlayerFinishedTurn";
		}

		// if the move isn't valid notify it
		return "reRetrieveMoveFromCurrentPlayer";
	}

	/**
	 * This method acts as "retrieveMoveFromCurrentPlayer" except that it uses
	 * the method that notifies the client that the last move wasn't valid
	 */
	public String reRetrieveMoveFromCurrentPlayer() {
		// get a move from the current player
		Move returned = reAskMoveToCurrentPlayer();

		// if the returned move is null the client has disconnected
		if (returned == null) {
			// if the player is back reAsk the move
			if (boardStatus.getCurrentPlayer().isConnected()) {
				return "reRetrieveMoveFromCurrentPlayer";
			}
			// otherwise go on with another player
			return "goOn";
		}

		// otherwise try to execute the move
		if (returned.isValid(boardStatus)) {
			executeMove(returned);
			return "checkIfPlayerFinishedTurn";
		}

		// if the move isn't valid notify it
		return "reRetrieveMoveFromCurrentPlayer";
	}

	/** This method execute locally the move and notifies the clients */
	public void executeMove(Move moveToExecute) {
		sendMoveToAllPlayers(moveToExecute);
		moveToExecute.execute(boardStatus);
	}

	/**
	 * This method checks if the current player has done three moves, in that
	 * case we goes on with another player, otherwise ask a new move
	 */
	public String checkIfPlayerFinishedTurn() {
		if (boardStatus.getCurrentPlayer().getLastMoves().size() == 3) {
			boardStatus.getCurrentPlayer().deleteLastMoves();
			return "goOn";
		}
		return "retrieveMoveFromCurrentPlayer";
	}

	/*
	 * This method has to be invoked after the moves session. It searches for
	 * the next player that has to play and state if the game is finished or
	 * not. If the game is over it communicates it to the caller using the
	 * string "gameOver", otherwise "notifyNewCurrentPlayer"
	 * 
	 * @return String the name of the next method that has to be called in
	 * manageGame() or the signal that the game is finished
	 * 
	 * @author Andrea
	 */
	public String goOn() {
		// go to the next player
		setNewCurrentPlayer();

		// check if the game is finished
		if (isGameFinished()) {
			return "gameOver";
		}

		// now go to the first connected player
		while (!boardStatus.getCurrentPlayer().isConnected()) {
			setNewCurrentPlayer();
		}

		return "notifyNewCurrentPlayer";
	}

	/**
	 * This method notifies the current player to all the clients, then goes on
	 * by moving the black sheep or by asking a move to the current player
	 */
	public String notifyNewCurrentPlayer() {
		sendNewCurrentPlayerToAllPlayers();
		if (boardStatus.getCurrentPlayer().equals(boardStatus.getFirstPlayer())) {
			return "moveTheBlackSheep";
		}
		return "retrieveMoveFromCurrentPlayer";
	}

	/**
	 * This method determines the next player who has to play. The next player
	 * will be the player at the right of the current player.
	 * 
	 * @author Andrea
	 */
	public void setNewCurrentPlayer() {
		Player oldCurrentPlayer = boardStatus.getCurrentPlayer();

		// find the position of the current player in the array of players
		int positionCurrentPlayer = boardStatus
				.getPositionOfAPlayer(oldCurrentPlayer);

		// go to next one
		positionCurrentPlayer = (positionCurrentPlayer + 1)
				% boardStatus.getPlayers().length;

		// set it
		boardStatus
				.setCurrentPlayer(boardStatus.getPlayers()[positionCurrentPlayer]);
	}

	/**
	 * This method checks whether the game is finished or not. In order to
	 * decide that the method checks two conditions. 1) all the standard gates
	 * have to be used 2) the currentPlayer has to be the first player who have
	 * played
	 * 
	 * 
	 * @author Andrea
	 */
	private boolean isGameFinished() {
		// the position of the first player
		int positionFirstPlayer;
		// the position of the current player
		int positionCurrentPlayer;
		// the first player
		Player firstPlayer = boardStatus.getFirstPlayer();
		// the current player
		Player currentPlayer = boardStatus.getCurrentPlayer();

		// find the position of the first player in the array of players
		positionFirstPlayer = boardStatus.getPositionOfAPlayer(firstPlayer);
		// find the position of the current player in the array of players
		positionCurrentPlayer = boardStatus.getPositionOfAPlayer(currentPlayer);

		if (positionFirstPlayer == positionCurrentPlayer) {
			if (boardStatus.countStandardGates() >= GameConstants.NUMBER_OF_NON_FINAL_GATES) {
				return true;
			}
		}
		return false;
	}

	/** This method computes and notifies the winners to all the clients */
	private void notifyWinners() {
		List<Player> winners = new ArrayList<Player>();

		// let's see who is the winner
		Integer currentValue;
		int max;

		// calculate the number of sheep for each terrain
		Map<TerrainType, Integer> valuesOfCards = new HashMap<TerrainType, Integer>();
		// initialize the HashMap
		valuesOfCards.put(TerrainType.COUNTRYSIDE, 0);
		valuesOfCards.put(TerrainType.DESERT, 0);
		valuesOfCards.put(TerrainType.LAKE, 0);
		valuesOfCards.put(TerrainType.MOUNTAIN, 0);
		valuesOfCards.put(TerrainType.PLAIN, 0);
		valuesOfCards.put(TerrainType.WOOD, 0);
		// calculate the number of sheep in each region
		for (Sheep sheep : boardStatus.getSheeps()) {
			currentValue = valuesOfCards.get(sheep.getPosition()
					.getTerrainType());
			valuesOfCards.put(sheep.getPosition().getTerrainType(),
					currentValue + 1);
		}

		// add one to the terrain in which is located the black sheep, if it's
		// different from sheepsburg
		if (!boardStatus.getBlackSheep().getPosition()
				.equals(Terrain.SHEEPSBURG)) {
			currentValue = valuesOfCards.get(boardStatus.getBlackSheep()
					.getPosition().getTerrainType());
			valuesOfCards.put(boardStatus.getBlackSheep().getPosition()
					.getTerrainType(), currentValue + 1);
		}

		// add the value of cards to player's money
		for (Player player : boardStatus.getPlayers()) {
			for (Card card : player.getCards()) {
				player.addMoney(valuesOfCards.get(card.getTerrainType()));
				;
			}
		}

		// determines the player (or players) with more money
		max = 0;
		for (Player player : boardStatus.getPlayers()) {
			if (player.getMoney() > max) {
				// set the new probable winner
				winners.clear();
				winners.add(player);
				max = player.getMoney();
			} else if (player.getMoney() == max) {
				// if there's a draw
				winners.add(player);
			}
		}

		sendWinnersToAllPlayers(winners);
	}
}