package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.TimeConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.ExecuteAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.PlayerAction;
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

import org.hamcrest.core.IsSame;

/**
 * This is the basic game controller, manages the communication from clients to
 * the game model, also taking care of handling the move checking and execution.
 * It's like a state machine that steps into the game process
 * 
 * @author Stefano,Andrea TODO test e dove ci sono errori. Sviluppo del gioco
 */

public class GameController implements Runnable {
	/** The array of client handlers */
	private List<ClientHandler> clients;

	/** The actual status of this game */
	private BoardStatus boardStatus;

	/** An object for rules validation */
	private RuleChecker ruleChecker;

	/** an object for updating the boardStatus */
	private ExecuteAction executeMove;

	/** an object for move cost calculation */
	private MoveCostCalculator moveCostCalculator;

	/** an attribute used to store the move that has to be managed */
	private Move newMove;

	/**
	 * a counter that keeps track of the number of move made by one player in
	 * his turn
	 */
	private int numberOfMoves;

	/** A logger */
	private Logger logger = Logger.getLogger(this.getClass().getName());

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
		calculateWinner();
	}

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
		// the first turn starts with a blackSheep move
		String nextMethod = "blackSheep";

		while (true) {
			try {
				// finds the next method
				Method method = getClass().getDeclaredMethod(nextMethod,
						(Class[]) null);
				// invoke the next method
				nextMethod = (String) method.invoke(this, (Object[]) null);
			} catch (Exception e) {
				// Catching a generic exception is not a good idea, but with
				// java 1.5 there's a problem with the exceptions thrown by
				// method.invoke
				String message = "Problems managing the game";
				logger.log(Level.SEVERE, message, e);
			}

			if (nextMethod.equals("gameOver"))
				break;
		}
	}

	/**
	 * Initialize the ruleChecker, the moveCostCalculator, the board. Send this
	 * initial status to all the players
	 */
	private void initializeAll() {
		ruleChecker = RuleChecker.create();
		moveCostCalculator = MoveCostCalculator.create();
		newMove = null;
		executeMove = new ExecuteAction();
		numberOfMoves = 1;

		// init the board
		initBoard();

		// send the initial status to all the clients
		sendStatusToAllPlayers();
	}

	/**
	 * Initialize the board following the rules on the second page
	 * (Preparazione)
	 */
	private void initBoard() {
		boardStatus = new BoardStatus(clients.size());
		addPlayersToGame();
		initSheeps();
		initBlackSheep();
		initCards();
		deleteRemainingInitialCards();
		chooseFirstPlayer();
		sendStatusToAllPlayers();
		chooseInitialPositions();
	}

	/**
	 * Create all the players and add them to the board, notify to the client
	 * handlers the reference to the game and to their player
	 */
	private void addPlayersToGame() {
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
	private void initSheeps() {
		for (Terrain terrain : Terrain.values()) {
			if (!terrain.equals(Terrain.SHEEPSBURG)) {
				Sheep sheep = new Sheep(terrain);
				sheep.setID();
				boardStatus.addSheep(sheep);
			}
		}
	}

	/** Create a BlackSheep and put it in sheepsburg */
	private void initBlackSheep() {
		BlackSheep blackSheep = new BlackSheep(Terrain.SHEEPSBURG);
		blackSheep.setID();
		boardStatus.addBlackSheepToBoardStatus(blackSheep);
	}

	/** Give an initial card to each player */
	private void initCards() {
		Deck deck = boardStatus.getDeck();
		Player[] players = boardStatus.getPlayers();

		for (Player p : players) {
			p.addCard(deck.extractInitialCard());
		}
	}

	/** Extract an initial player and notify to the boardstatus */
	private void chooseFirstPlayer() {
		Dice dice = Dice.create();
		Player[] players = boardStatus.getPlayers();
		int sorted = dice.roll(players.length);
		Player firstPlayer = players[sorted];
		boardStatus.setCurrentPlayer(firstPlayer);
		boardStatus.setFirstPlayer(firstPlayer);
	}

	/**
	 * This method asks to each player to choose an initial position, if a
	 * player disconnects (or if it's already disconnected) we choose a random
	 * position. When a player choose his position we send all the status to the
	 * clients
	 */
	private void chooseInitialPositions() {
		for (ClientHandler ch : clients) {
			Road initial = null;

			if (!ch.getPlayer().isSuspended()) {
				try {

					do {
						initial = ch.askInitialPosition();
					} while (!boardStatus.isFreeRoad(initial));

					ch.getPlayer().move(initial);

				} catch (ClientDisconnectedException e) {
					String message = "A client disconnected";
					logger.log(Level.INFO, message, e);
					catchDisconnection(e.getPlayer());

					chooseRandomPositionForAPlayer(ch.getPlayer());
				} catch (ClassNotFoundException e) {
					String message = "A client is not aligned with the communication protocol, suspending it";
					logger.log(Level.INFO, message, e);
					ch.getPlayer().suspend();
					ch.getPlayer().setNotConnected();

					chooseRandomPositionForAPlayer(ch.getPlayer());
				}

			} else {
				chooseRandomPositionForAPlayer(ch.getPlayer());
			}
			sendStatusToAllPlayers();
		}
	}

	/** This method moves the given player to the first free road it finds */
	private void chooseRandomPositionForAPlayer(Player player) {
		for (Road r : boardStatus.getRoadMap().getHashMapOfRoads().values()) {
			if (boardStatus.isFreeRoad(r)) {
				player.move(r);
			}
		}
	}

	/** Send the status to all the not suspended players */
	private void sendStatusToAllPlayers() {
		for (ClientHandler client : clients) {
			if (!client.getPlayer().isSuspended())
				try {
					client.sendNewStatus(boardStatus);
				} catch (ClientDisconnectedException e) {
					String message = "A client disconnected";
					logger.log(Level.INFO, message, e);
					catchDisconnection(e.getPlayer());
				}
		}
	}

	/** Delete all the remaining initial cards in the deck */
	private void deleteRemainingInitialCards() {
		boardStatus.getDeck().deleteRemainingInitialCards();
	}

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

		// first of all we suspend this a game for a fixed time
		try {
			Thread.sleep(TimeConstants.WAITING_FOR_CLIENT_RECONNECT);
		} catch (InterruptedException e) {
			String message = "The thread has been interrupted while waiting for a client reconnection";
			logger.log(Level.SEVERE, message, e);
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
	private void catchDisconnection(Player p) {
		for (ClientHandler client : clients)
			if (client.getPlayer().equals(p)) {
				client.notifyClientDisconnection();
				break;
			}
	}

	/** This method reconnect a player changing his ConnectionHandler */
	public void notifyReconnection(Player player, ClientHandler newClientHandler) {
		// search the old client handler
		for (ClientHandler client : clients) {
			// and change the client handler
			if (client.getPlayer().equals(player))
				client = newClientHandler;
		}

		// set the parameters
		newClientHandler.setGame(this);
		newClientHandler.setPlayer(player);

		// now wake the player
		player.setConnected();
		player.resume();

		// and give him the current status
		try {
			newClientHandler.sendNewStatus(boardStatus);
		} catch (ClientDisconnectedException e) {
			String message = "A client disconnected";
			logger.log(Level.INFO, message, e);
			catchDisconnection(e.getPlayer());
		}
	}

	/**
	 * This method has to be invoked after the moves session. It searches for
	 * the next player that has to play and state if the game is finished or
	 * not. If the game is over it communicates it to the caller using the
	 * string "gameOver".
	 * 
	 * @return String the name of the next method that has to be called in
	 *         manageGame() or the signal that the game is finished
	 * @author Andrea
	 */
	private String goOn() {
		// go to the next player
		setNewCurrentPlayer();

		// check if the game is finished
		if (gameOver())
			return "gameOver";

		// now go to the first not suspended player
		while (boardStatus.getCurrentPlayer().isSuspended())
			setNewCurrentPlayer();

		return "communicateNewCurrentPlayer";
	}

	/**
	 * Communicates the new current player to the clients (in this way they
	 * don't have to wait for a move to know who's playing). If the current
	 * player disconnects during the communication the next step will be
	 * choosing another current player. Otherwise it's time to move the black
	 * sheep.
	 * 
	 * @return String nextmethod to be called in manageGame()
	 * @author Andrea
	 */
	private String communicateNewCurrentPlayer() {
		// the method send the current player to all the clients
		for (ClientHandler client : clients) {
			try {
				client.setCurrentPlayer(boardStatus.getCurrentPlayer());
			} catch (ClientDisconnectedException e) {
				String message = "A client disconnected";
				logger.log(Level.INFO, message, e);
				catchDisconnection(e.getPlayer());

				// if the current player has been suspended another current
				// player has to be selected
				if (boardStatus.getCurrentPlayer().isSuspended())
					return "goOn";
			}
		}
		return "blackSheep";
	}

	/**
	 * This method determines the next player who has to play. The next player
	 * will be the player at the right of the current player.
	 * 
	 * @author Andrea
	 */
	private void setNewCurrentPlayer() {
		Player oldCurrentPlayer = boardStatus.getCurrentPlayer();
		// find the position of the current player in the array of players
		int positionCurrentPlayer = boardStatus
				.getPositionOfAPlayer(oldCurrentPlayer);
		if (positionCurrentPlayer < boardStatus.getPlayers().length - 1) {
			positionCurrentPlayer++;
		} else {
			positionCurrentPlayer = 0;
		}
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
	private boolean gameOver() {
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
			if (boardStatus.countStandardGates() > 20) {
				return true;
			}
		}
		return false;
	}

	/**
	 * moveBlackSheep() moves the black sheep according to the sheepland's
	 * rules.
	 * 
	 * @returns String the name of the next method that has to be called in
	 *          manageGame()
	 * @author Andrea
	 */
	private String blackSheep() {
		Dice dice = Dice.create();
		int diceResult = dice.roll(GameConstants.NUMBER_OF_DICE_SIDES);
		int index;

		// it finds the roads near the black sheep
		Set<Road> roadsNearBlackSheep = boardStatus.getRoadMap()
				.findRoadsAdjacentToATerrain(
						boardStatus.getBlackSheep().getPosition());

		// check if there is a road that has the same number obtained with the
		// dice
		for (Road road : roadsNearBlackSheep)
			if (road.getBoxValue() == diceResult)
				// check if the road is free
				if (boardStatus.isFreeRoad(road)) {
					// find the new terrain
					for (Terrain t : road.getAdjacentTerrains())
						if (!boardStatus.getBlackSheep().getPosition()
								.equals(t))
							// creates a new move that has to be executed and
							// place it in newMove attribute
							newMove = new MoveBlackSheep(t,
									boardStatus.getBlackSheep());
					return "executeNewMove";
				}

		// otherwise the blackSheep remains in its current position and no move
		// has to be executed
		return "askMoveToClient";

	}

	/**
	 * askMoveToClient() asks the client for a new move and checks it.
	 * 
	 * @return String the name of the next method that has to be called in
	 *         manageGame().
	 * @author Andrea
	 */

	private String askMoveToClient() {
		Player currentPlayer = boardStatus.getCurrentPlayer();
		Move newMove;

		// Look for the client that correspond to the current player
		ClientHandler client = null;
		for (ClientHandler ch : clients)
			if (currentPlayer.equals(ch.getPlayer())) {
				client = ch;
				break;
			}

		try {
			newMove = client.askMove();
			if (!ruleChecker.isValidMove(newMove, currentPlayer.getLastMoves(),
					boardStatus))
				// the move is not valid therefore the next step is
				// communicating
				// it to the client and ask for it again
				return "askMoveToClientAgain";

		} catch (ClientDisconnectedException e) {
			String message = "A client disconnected";
			logger.log(Level.INFO, message, e);
			catchDisconnection(e.getPlayer());

			// if the player that have lost the connection is the current
			// player and it's back again the server ask the move again
			if (e.getPlayer().isConnected())
				return "askMoveToClient";
			// if the player has been suspended the system has to decide
			// another currentPlayer, therefore it calls goOn()
			return "goOn";

		} catch (ClassNotFoundException e) {
			String message = "A client is not aligned with the communication protocol, suspending it";
			logger.log(Level.INFO, message, e);
			client.getPlayer().suspend();
			client.getPlayer().setNotConnected();
			return "goOn";
		}

		// if the method reaches this point it means that there's a valid move
		// that has to be executed
		this.newMove = newMove;
		return "executeNewMove";
	}

	/**
	 * askMoveToClientAgain() notifies to the client that the move was not valid
	 * and ask again for it.
	 * 
	 * @return String the name of the next method that has to be called in
	 *         manageGame().
	 * @author Andrea
	 * @TODO test
	 */
	private String askMoveToClientAgain() {
		Player currentPlayer = boardStatus.getCurrentPlayer();
		Move newMove;

		// Look for the client that correspond to the current player
		ClientHandler client = null;
		for (ClientHandler ch : clients)
			if (currentPlayer.equals(ch.getPlayer())) {
				client = ch;
				break;
			}

		try {
			newMove = client.sayMoveIsNotValid();

			// if the move is valid the next step is updating the boardstatus
			if (ruleChecker.isValidMove(newMove, currentPlayer.getLastMoves(),
					boardStatus)) {
				this.newMove = newMove;
				return "executeNewMove";
			}

		} catch (ClientDisconnectedException e) {
			String message = "A client disconnected";
			logger.log(Level.INFO, message, e);
			catchDisconnection(e.getPlayer());

			// if the player that have lost the connection is the current
			// player and it's back again the server ask the move again
			if (e.getPlayer().isConnected())
				return "askMoveToClient";
			// if the player has been suspended the system has to decide
			// another currentPlayer, therefore it calls goOn()
			return "goOn";

		} catch (ClassNotFoundException e) {
			String message = "A client is not aligned with the communication protocol, suspending it";
			logger.log(Level.INFO, message, e);
			client.getPlayer().suspend();
			client.getPlayer().setNotConnected();
			return "goOn";
		}

		return "askMoveToClientAgain";
	}

	/**
	 * executeNewMove() updates the boardStatus of the Server.
	 * 
	 * @return String the name of the next method that has to be called in
	 *         manageGame().
	 * @author Andrea
	 */
	private String executeNewMove() {
		if (newMove instanceof PlayerAction) {
			if (newMove instanceof BuyCardMove) {
				executeMove.executeBuyCardMove((BuyCardMove) newMove,
						boardStatus);
			} else {
				if (newMove instanceof MovePlayer) {
					// set the cost before execute the move
					((MovePlayer) newMove).setCost(moveCostCalculator
							.getMoveCost(newMove));
					executeMove.executeMovePlayer((MovePlayer) newMove,
							boardStatus);
				} else if (newMove instanceof MoveSheep)
					executeMove.executeMoveSheep((MoveSheep) newMove,
							boardStatus);
			}
		} else if (newMove instanceof MoveBlackSheep)
			executeMove.executeMoveBlackSheep((MoveBlackSheep) newMove,
					boardStatus);
		return "updateClients";
	}

	/**
	 * updateClients() send the new move to all the clients.
	 * 
	 * @return String the name of the next method that has to be called in
	 *         manageGame().
	 * @author Andrea
	 * @TODO test
	 */
	private String updateClients() {
		// the method send the move to all the clients
		for (ClientHandler client : clients) {
			try {
				client.executeMove(newMove);
			} catch (ClientDisconnectedException e) {
				String message = "A client disconnected";
				logger.log(Level.INFO, message, e);
				catchDisconnection(e.getPlayer());
			}
		}
		// if the current player has to do other moves the system goes back
		// to askMoveToClient()
		// otherwise it select a new current player
		if (numberOfMoves < 3) {
			numberOfMoves++;
			return "askMoveToClient";
		}
		// a new player has to start his turn
		numberOfMoves = 1;
		return "goOn";
	}

	/**
	 * CalculateWinner calculates the winner of a game and communicates it to
	 * the clients
	 * 
	 * @author Andrea
	 */
	private void calculateWinner() {
		ArrayList<Player> winners = findWinner();
		communicateWinner(winners);
	}

	/**
	 * Finds the winner using Sheepland's rules
	 * 
	 * 
	 * @Returns winners the arrayList of winners
	 * @author Andrea
	 */
	private ArrayList<Player> findWinner() {
		ArrayList<Player> winners = new ArrayList<Player>();
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

		// add one to the terrain in which is located the black sheep
		currentValue = valuesOfCards.get(boardStatus.getBlackSheep()
				.getPosition().getTerrainType());
		valuesOfCards.put(boardStatus.getBlackSheep().getPosition()
				.getTerrainType(), currentValue + 1);

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
			}
			if (player.getMoney() == max) {
				// if there's a draw
				winners.add(player);
			}
		}

		return winners;

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
	private void communicateWinner(ArrayList<Player> winners) {
		for (ClientHandler client : clients) {
			try {
				client.sendWinners(winners);
			} catch (ClientDisconnectedException e) {
				String message = "A client disconnected";
				logger.log(Level.INFO, message, e);
				catchDisconnection(e.getPlayer());
			}
		}
	}
}
