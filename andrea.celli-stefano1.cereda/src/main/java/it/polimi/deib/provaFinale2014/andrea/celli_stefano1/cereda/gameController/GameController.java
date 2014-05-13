package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ListOfClientHandler;

import java.util.ArrayList;

/**
 * This is the game controller, manages the communication from clients to the
 * game model.
 * 
 * @author Stefano
 * TODO
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
		ruleChecker = RuleChecker.create(gameType);
		initBoard();
	}

	private void initBoard() {
		boardStatus = new BoardStatus();
	}

}
