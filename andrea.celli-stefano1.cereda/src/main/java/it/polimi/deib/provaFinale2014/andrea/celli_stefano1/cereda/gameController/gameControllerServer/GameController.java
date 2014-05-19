package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ListOfClientHandler;

/**
 * This is the game controller, manages the communication from clients to the
 * game model.
 * 
 * @author Stefano TODO
 */

public class GameController implements Runnable {
	/** The array of client handlers */
	private ListOfClientHandler clients;
	/** The actual status of this game */
	private BoardStatus boardStatus;
	/** An object for rules validation */
	private RuleChecker ruleChecker;
	/** The set of rules in use */
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

	// TODO from here, manage all the game
	public void run() {
		initializeAll();
	}

	private void initializeAll() {
		ruleChecker = RuleChecker.create();
		initBoard();
	}

	private void initBoard() {
		boardStatus = new BoardStatus();
	}

}
