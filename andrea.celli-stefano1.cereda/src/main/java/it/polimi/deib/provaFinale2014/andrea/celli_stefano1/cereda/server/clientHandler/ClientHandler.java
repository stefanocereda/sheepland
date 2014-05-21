package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import java.io.IOException;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * An interface for objects that handles the communications between the server
 * and a client
 * 
 * @author Stefano
 * 
 */
public interface ClientHandler {
	/**
	 * This method asks the client to send a new move, which is returned. The
	 * client can give an impossible move so it must be checked
	 * 
	 * @return the move returned from the client
	 * @throws ClassNotFoundException
	 * @throws ClientDisconnectedException
	 */
	public Move askMove() throws ClassNotFoundException,
			ClientDisconnectedException;

	/**
	 * Send the client a move to be executed. The clients doesn't do any check
	 * on the move so it must be already valid
	 * 
	 * @param moveToExecute
	 *            to move to be executed
	 * @throws ClientDisconnectedException
	 */
	public void executeMove(Move moveToExecute)
			throws ClientDisconnectedException;

	/**
	 * Say to the client that the last move wasn't valid, and waits for a new
	 * one
	 * 
	 * @return a new Move
	 * @throws ClassNotFoundException
	 * @throws ClientDisconnectedException
	 */
	public Move sayMoveIsNotValid() throws ClassNotFoundException,
			ClientDisconnectedException;

	/**
	 * Send to the client a new status to replace the old one
	 * 
	 * @throws ClientDisconnectedException
	 * 
	 */
	public void sendNewStatus(BoardStatus newStatus)
			throws ClientDisconnectedException;

	/** Set the game where this client is playing */
	public void setGame(GameController gc);

	/** Set the player controlled */
	public void setPlayer(Player p);

	/** Get the controlled player */
	public Player getPlayer();

	/**
	 * Notify the disconnection of a player to the gamecontroller (so it can
	 * suspend the player) and to the server starter so it can wait for this
	 * player to reconnect and let it play in the same game
	 * 
	 * @param gc
	 *            the game controller to notify
	 * @param pc
	 *            the client disconnected
	 */
	public void notifyClientDisconnection(GameController gc, Player pc);

	/** Get the ip addres and the port of the client */
	public ClientIdentifier getIdentifier();
}
