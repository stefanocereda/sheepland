package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
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
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Dice;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler.ListOfClientHandler;

import java.util.Set;
import java.util.logging.Logger;

/**
 * This is the basic game controller, manages the communication from clients to
 * the game model, also taking care of handling the move checking and execution.
 * It's like a state machine that steps into the game process
 * 
 * @author Stefano,Andrea TODO test e dove ci sono errori. Sviluppo del gioco
 */

public class GameController implements Runnable {
	/** The array of client handlers */
	private ListOfClientHandler clients;

	/** The actual status of this game */
	private BoardStatus boardStatus;

	/** An object for rules validation */
	private RuleChecker ruleChecker;

	/** an object for move cost calculation */
	private MoveCostCalculator moveCostCalculator;

	/** an attribute used to store the move that has to be managed */
	private Move newMove;

	/** an object for updating the boardStatus */
	private ExecuteAction executeMove;

	/**
	 * an counter that keeps track of the number of move made by one player in
	 * his turn
	 */
	private int numberOfMoves;

	/**
	 * GameController constructor
	 * 
	 * @param playerClients
	 *            The list of clientHandlers for the players
	 */
	public GameController(ListOfClientHandler playerClients) {
		clients = playerClients;
	}

	// TODO manage all the game
	/** Start the game */
	public void run() {

		initializeAll();

		manageGame();
	}

	/**
	 * Manage the game process. The system has to behave as a FSM. For that
	 * reason each method invoked determines,depending on the result of its
	 * computation, the next method to be called. Every method that is called in
	 * manageGame() returns a string cointaining the name of the next method.
	 * Using getMethod(...) it gets the Method Object which is later invoked
	 * using invoke(...). The process terminates when a gameOver exception is
	 * raised. After that the winner is determined and the game ends.
	 * 
	 * @author Andrea
	 */
	private void manageGame() {

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
	}

	/**
	 * Create all the players and add them to the board, notify to the client
	 * handlers the reference to the game and to their player
	 */
	private void addPlayersToGame() {
		// number of players = number of client handlers
		for (int i = 0; i < clients.size(); i++) {
			// create a player and add it to the board
			Player p = new Player();
			p.setID();
			boardStatus.addPlayerToBoardStatus(p);

			// notify the client handler
			clients.get(i).setGame(this);
			clients.get(i).setPlayer(p);
		}
	}

	/**
	 * Create all the sheep and them on the map, one per terrain ecluding
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
	 * Send the status to all the players
	 */
	private void sendStatusToAllPlayers() {
		for (ClientHandler client : clients.toArray(new ClientHandler[clients
				.size()])) {
			try {
				client.sendNewStatus(boardStatus);
			} catch (ClientDisconnectedException e) {
				Logger log = Logger.getAnonymousLogger();
				log.severe("A CLIENT DISCONNECTED: " + e);
				notifyDisconnection(e.getPlayer());
			}
		}
	}

	/** Delete all the remaining initial cards in the deck */
	private void deleteRemainingInitialCards() {
		boardStatus.getDeck().deleteRemainingInitialCards();
	}

	/**
	 * This method handle the disconnection of a player following the specs of
	 * the project
	 * 
	 * @param pc
	 *            the player to suspend
	 */
	public void notifyDisconnection(Player pc) {
		// a client disconnected
		pc.setNotConnected();

		// first of all we suspend this a game for a fixed time
		try {
			Thread.sleep(Costants.WAITING_FOR_CLIENT_RECONNECT);
		} catch (InterruptedException e) {
			Logger log = Logger.getAnonymousLogger();
			log.fine("thread stopped: " + e);
		}

		// now check if client reconnected, if yes go on, otherwise suspend it
		// and go on without him
		if (!pc.isConnected()) {
			pc.suspend();
		}
	}

	/** This method reconnect a player changing his ConnectionHandler */
	public void notifyReconnection(Player player, ClientHandler newClienthandler) {
		// search the old client handler
		for (int i = 0; i < clients.size(); i++) {
			// and change the client handler
			if (clients.get(i).getPlayer() == player)
				clients.set(i, newClienthandler);
		}

		// set the parameters
		newClienthandler.setGame(this);
		newClienthandler.setPlayer(player);

		// now wake the player
		player.setConnected();
		player.resume();
	}

	/**
	 * This method has to be invoked after the moves session. It searchs for the
	 * next player that has to play and state if the game is finished or not. If
	 * the game is over it throws a GameOverException that has to be managed in
	 * the manageGame method.
	 * 
	 * @return String the name of the next method that has to be called in
	 *         manageGame()
	 * @author Andrea
	 */
	private String goOn() throws GameOverException {
		do {
			setNewCurrentPlayer();
		} while (boardStatus.getCurrentPlayer().isSuspended() && !gameOver());
		if (gameOver())
			throw new GameOverException();
		return "moveBlackSheep";
	}

	/**
	 * This method determins the next player who has to play. The next player
	 * will be the player at the right of the current player.
	 * 
	 * @author Andrea
	 * @TODO test
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
	 * @TODO test
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
	 * @TODO test
	 */
	private String blackSheep() {
		Dice dice = Dice.create();
		int diceResult = dice.roll(Costants.NUMBER_OF_DICE_SIDES);
		int index;

		// it finds the road near the black sheep
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
					for (index = 0; index < road.getAdjacentTerrains().length; index++)
						if (boardStatus.getBlackSheep().getPosition() != road
								.getAdjacentTerrains()[index])
							// creates a new move that has to be executed and
							// place it
							// in newMove attribute
							newMove = new MoveBlackSheep(
									road.getAdjacentTerrains()[index],
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
	 * @TODO specify the catch for ClassNotFoundException, Test
	 */

	private String askMoveToClient() {
		Player currentPlayer = boardStatus.getCurrentPlayer();
		int indexOfTheCurrentPlayer;
		boolean found = false;
		// The new move
		Move newMove;
		// Look for the client that corespond to the current player
		for (indexOfTheCurrentPlayer = 0; (indexOfTheCurrentPlayer < clients
				.size()) && !found; indexOfTheCurrentPlayer++)
			if (currentPlayer.equals(clients.get(indexOfTheCurrentPlayer)
					.getPlayer()))
				found = true;
		try {
			newMove = clients.get(indexOfTheCurrentPlayer).askMove();
			if (!ruleChecker.isValidMove(newMove, currentPlayer.getLastMoves(),
					boardStatus))
				// the move is not valid therefore the next step is comunicating
				// it to the client and ask for it again
				return "askMoveToClientAgain";

		} catch (ClientDisconnectedException e) {
			Logger log = Logger.getAnonymousLogger();
			log.severe("A CLIENT DISCONNECTED: " + e);
			notifyDisconnection(e.getPlayer());

			// check if the player that lost the connection is the current
			// player
			if (e.getPlayer().equals(boardStatus.getCurrentPlayer())) {
				// if the player that have lost the connection is the current
				// player and it's back again the server ask the move again
				if (e.getPlayer().isConnected())
					return "askMoveToClient";
				// if the player has been suspended the system has to decide
				// another
				// currentPlayer, therefore it calls goOn()
				return "goOn";
			} else {
				/** @TODO Is it okay? */
				return "askMoveToClient";
			}
		} catch (ClassNotFoundException e) {
			/** @TODO what to do here? */
			return "askMoveToClient";
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
		int indexOfTheCurrentPlayer;
		boolean found = false;
		// The new move
		Move newMove;
		// Look for the client that corespond to the current player
		for (indexOfTheCurrentPlayer = 0; (indexOfTheCurrentPlayer < clients
				.size()) && !found; indexOfTheCurrentPlayer++)
			if (currentPlayer.equals(clients.get(indexOfTheCurrentPlayer)
					.getPlayer()))
				found = true;

		try {
			newMove = clients.get(indexOfTheCurrentPlayer).sayMoveIsNotValid();
			// if the move is valid the next step is updating the boardstatus
			if (ruleChecker.isValidMove(newMove, currentPlayer.getLastMoves(),
					boardStatus)) {
				this.newMove = newMove;
				return "executeNewMove";
			}
		} catch (ClientDisconnectedException e) {
			Logger log = Logger.getAnonymousLogger();
			log.severe("A CLIENT DISCONNECTED: " + e);
			notifyDisconnection(e.getPlayer());

			// check if the player that lose the connection is the currentPlayer
			if (e.getPlayer().equals(currentPlayer)) {
				// if the current player hasBeen suspended the system goes on
				if (currentPlayer.isSuspended())
					return "goOn";
				// if the currentplayer is back the system ask again for a move
				if (currentPlayer.isConnected())
					return "askMoveToClientAgain";
			} else
				// a different player went offline
				return "askMoveToClientAgain";
		} catch (ClassNotFoundException e) {
			/** @TODO what to do here? */
			return "askMoveToClientAgain";
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
				if (newMove instanceof MovePlayer)
					executeMove.executeMovePlayer((MovePlayer) newMove,
							boardStatus);
				else if (newMove instanceof MoveSheep)
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
	 * @TODO test,catch!
	 */
	private String updateClients() {

		// the method send the move to all the clients
		for (ClientHandler client : clients.toArray(new ClientHandler[clients
				.size()])) {
			try {
				client.executeMove(newMove);
			} catch (ClientDisconnectedException e) {
				/** @TODO here */
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
		numberOfMoves = 0;
		return "goOn";
	}
}
