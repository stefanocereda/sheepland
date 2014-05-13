package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ListOfClientHandler;

import java.util.ArrayList;

//TODO
//This is the game controller, manages the communication from clients to the game model and checks the rules
public class GameController implements Runnable {
	// The array of client handlers
	private ListOfClientHandler clients;
	// an object representing the actual status of this game
	private BoardStatus boardStatus;
	// an object for rules validation, it will be shared between multiple games
	private RuleChecker ruleChecker;
	// the type of game / rules used
	private GameType gameType;

	public GameController(ListOfClientHandler playerClients,
			GameType gameType) {
		clients = playerClients;
		this.gameType = gameType;
	}

	public void run() {
		initializeAll();
	}

	// initialize the game
	private void initializeAll() {
		ruleChecker = RuleChecker.create(gameType);
		initBoard();
	}
	
	private void initBoard(){
		boardStatus = new BoardStatus();
	}
	
	
	
	
}
