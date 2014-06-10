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

	/** A ping message to the client */
	public static final String PING = "A";

	/** The client answer to a ping */
	public static final String PONG = "B";

	/** Send to the client a new status to replace the old one */
	public static final String SEND_NEW_STATUS = "C";

	/** Say to the client you're sending a move to execute */
	public static final String EXECUTE_MOVE = "D";

	/** Ask the client to send a new move */
	public static final String ASK_NEW_MOVE = "E";

	/**
	 * Say to the client that the last move wasn't valid and you're expecting a
	 * new one
	 */
	public static final String NOT_VALID_MOVE = "F";

	/** Tell the client who is the current player */
	public static final String SET_CURRENT_PLAYER = "G";

	/** Tell the client that the game is over and send a list of winners */
	public static final String SEND_WINNERS = "H";

	/** ask the client to send an initial position */
	public static final String ASK_INITIAL_POSITION = "I";

	/** Tell to the client that you're sending the controlled player */
	public static final String NOTIFY_CONTROLLED_PLAYER = "J";

	/** Ask the player to use the shepherd that he wants to control in this turn */
	public static final String CHOOSE_SHEPHERD = "K";

	/** Ask the user to choose an initial road for his second shepherd */
	public static final String ASK_SECOND_INITIAL_POSITION = "L";

	/**
	 * Tell the user that the current player has made a choice for the
	 * controlled shepherd
	 */
	public static final String NOTIFY_SHEPHERD = "M";

	/** Ask the client to send a List of MarketOffers */
	public static final String ASK_MARKET_OFFERS = "N";

	/** Send a list of available market offers and get back a list of market buy */
	public static final String ASK_MARKET_BUY = "O";

	/** Hide the default constructor */
	private SocketMessages() {
	}

}
