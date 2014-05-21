package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerClient;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;

/**
 * This class manage the game in the client executing the up-dates that are
 * received from the server. There are a finite number of "instruction" from the
 * server that has to be taken into consideration, for each of them this class
 * contains a method that can be invoked in the part of the code dedicated to
 * the communication with the server.
 * 
 * @author Andrea
 * @TODO all
 */
public class GameControllerClient {
	private ExecuteAction executeAction;
	// The boardStatus of the current game
	private BoardStatus boardStatus;

	/**
	 * This method links executeAction with a proper instance of the class. This
	 * method will be overridden in the GameControllerClienExtended class (that
	 * class will inherit from GameControllerClient). In this way the
	 * GameControllerClientExtended class is going to have an
	 * ExecuteActionExtended attribute (it will use the same of its superclass
	 * through dynamic binding.) This method links boardStatus with a proper
	 * instance of the class. This method will be overridden in the
	 * GameControllerExtended class. It will use dynamic binding to use the
	 * boardStatus attribute of the superclass as a BoardStatusExtended.
	 * 
	 * @param numberOfPlayers
	 *            The number of players that will play the match
	 */
	public void initializeGame(int numberOfPlayers) {
		// It starts a new update executer
		executeAction = new ExecuteAction();
		// It creates a new boardStatus
		boardStatus = new BoardStatus(numberOfPlayers);
	}

}
