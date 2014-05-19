package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Deck;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ListOfClientHandler;

/**
 * This is the game controller, manages the communication from clients to the
 * game model.
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
	 * TODO FORSE QUESTO VA TOLTO, LE REGOLE AVANZATE VERRANNO GESTITE DA UNA
	 * SOTTOCLASSE The set of rules in use, this shouldn't be needed using
	 * GameController and GameControllerExtended
	 */
	private GameType gameType;

	/**
	 * GameController constructor
	 * 
	 * @param playerClients
	 *            The list of clientHandlers for the players
	 * @param gameType
	 *            The set of rules to use
	 */
	public GameController(ListOfClientHandler playerClients, GameType gameType) {
		clients = playerClients;
		this.gameType = gameType;
	}

	// TODO manage all the game
	public void run() {
		initializeAll();
	}

	/** Initialize the ruleChecker, the moveCostCalculator, the board */
	private void initializeAll() {
		ruleChecker = RuleChecker.create();
		moveCostCalculator = MoveCostCalculator.create();

		initBoard();
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
		chooseInitialPlayersPositions();
	}

	/** Create all the players and add them to the board */
	private void addPlayersToGame() {
		// number of players = number of client handlers
		for (int i = 0; i < clients.size(); i++) {
			Player p = new Player();
			boardStatus.addPlayerToBoardStatus(p);
		}
	}

	/** Create all the sheep and them on the map, one per terrain */
	private void initSheeps() {
		for (Terrain terrain : Terrain.values()) {
			Sheep sheep = new Sheep(terrain);
			boardStatus.addSheep(sheep);
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

	// TODO DA QUI, POSIZIONI INIZIALI E GESTIRE LO SVILUPPO DELLA PARTITA
	private void chooseInitialPlayersPositions() {
		// TODO Auto-generated method stub

	}
}
