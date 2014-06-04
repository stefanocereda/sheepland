/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants;

/**
 * Messages on the socket
 * 
 * @author Stefano
 * 
 */
public class SocketMessages {

	/** The client answer to a ping */
	public static final String PONG = "PONG";

	/** A ping message to the client */
	public static final String PING = "PING";

	/** Send to the client a new status to replace the old one */
	public static final String SEND_NEW_STATUS = "SEND-NEW-STATUS";

	/** Say to the client you're sending a move to execute */
	public static final String EXECUTE_MOVE = "EXECUTE-MOVE";

	/** Ask the client to send a new move */
	public static final String ASK_NEW_MOVE = "ASK-NEW-MOVE";

	/**
	 * Say to the client that the last move wasn't valid and you're expecting a
	 * new one
	 */
	public static final String NOT_VALID_MOVE = "NOT-VALID-MOVE";

	/** Tell the client who is the current player */
	public static final String SET_CURRENT_PLAYER = "SET-CURRENT-PLAYER";

	/** Tell the client that the game is over and send a list of winners */
	public static final String SEND_WINNERS = "SEND-WINNERS";

	/** ask the client to send an initial position */
	public static final String ASK_INITIAL_POSITION = "SEND-INITIAL-POSITION";

	/** Tell to the client that you're sending the controlled player */
	public static final String NOTIFY_CONTROLLED_PLAYER = "NOTIFY-CONTROLLED-PLAYER";

	/** Ask the player to use the shepherd that he wants to control in this turn */
	public static final String CHOOSE_SHEPHERD = "CHOOSE-SHEPHERD";

	/** Hide the default constructor */
	private SocketMessages() {
	}

}
