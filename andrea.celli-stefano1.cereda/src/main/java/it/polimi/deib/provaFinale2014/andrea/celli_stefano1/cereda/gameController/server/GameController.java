package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Dice;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ListOfClientHandler;

/**
 * This is the basic game controller, manages the communication from clients to
 * the game model, also taking care of handling the move checking and execution.
 * It's like a state machine that steps into the game process
 * 
 * @author Stefano TODO test e dove ci sono errori. Sviluppo del gioco
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

	/** Manage the game process */
	private void manageGame() {
		// TODO Auto-generated method stub

	}

	/**
	 * Initialize the ruleChecker, the moveCostCalculator, the board. Send this
	 * initial status to all the players
	 */
	private void initializeAll() {
		ruleChecker = RuleChecker.create();
		moveCostCalculator = MoveCostCalculator.create();

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
			boardStatus.addPlayerToBoardStatus(p);

			// notify the client handler
			clients.values()[i].setGame(this);
			clients.values()[i].setPlayer(p);
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
				boardStatus.addSheep(sheep);
			}
		}
	}

	/** Create a BlackSheep and put it in sheepsburg */
	private void initBlackSheep() {
		BlackSheep blackSheep = new BlackSheep(Terrain.SHEEPSBURG);
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
	}

	/**
	 * Send the status to all the players
	 */
	private void sendStatusToAllPlayers() {
		for (ClientHandler client : clients.values()) {
			try {
				client.sendNewStatus(boardStatus);
			} catch (ClientDisconnectedException e) {
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
			e.printStackTrace();
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
			if (clients.values()[i].getPlayer() == player)
				clients.set(i, newClienthandler);
		}

		// set the parameters
		newClienthandler.setGame(this);
		newClienthandler.setPlayer(player);

		// now wake the player
		player.setConnected();
		player.resume();
	}
}
