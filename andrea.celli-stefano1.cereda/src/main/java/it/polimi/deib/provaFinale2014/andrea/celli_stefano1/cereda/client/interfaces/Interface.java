/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;

/**
 * That's the interface of a user interface. It specifies the public methods
 * that will be called by the client game controller
 * 
 * @author Stefano
 * 
 */
public interface Interface {
	public Move getNewMove();

	public void setReferenceToGameController(GameControllerClient gameController);

	public void notifyMove(Move move);

	public void notifyNotValidMove();

	public void notifyDisconnection();

	public void notifyCurrentPlayer(Player newCurrentPlayer);

	public void notifyWinners(ArrayList<Player> winners);

	public Road chooseInitialPosition();
}
