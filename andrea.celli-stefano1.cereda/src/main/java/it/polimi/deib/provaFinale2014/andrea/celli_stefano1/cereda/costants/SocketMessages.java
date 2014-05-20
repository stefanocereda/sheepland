/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants;

/**
 * @author Stefano
 * 
 */
public class SocketMessages {

	/** Send to the client a new status to replace the old one */
	public static String SEND_NEW_STATUS = "SEND-NEW-STATUS";

	/** Say to the client you're sending a move to execute */
	public static String EXECUTE_MOVE = "EXECUTE-MOVE";

	/** Ask the client to send a new move */
	public static String ASK_NEW_MOVE = "ASK-NEW-MOVE";

	/**
	 * Say to the client that the last move wasn't valid and you're expecting a
	 * new one
	 */
	public static String NOT_VALID_MOVE = "NOT-VALID-MOVE";

}
