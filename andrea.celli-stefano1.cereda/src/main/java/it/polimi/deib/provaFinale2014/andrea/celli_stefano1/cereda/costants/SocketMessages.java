/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants;

/**
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

	/** Tell the client that the game is over and send an arraylist of winners */
	public static final Object SEND_WINNERS = "SEND-WINNERS";

	/** Hide the default constructor */
	private SocketMessages() {
	}

}
