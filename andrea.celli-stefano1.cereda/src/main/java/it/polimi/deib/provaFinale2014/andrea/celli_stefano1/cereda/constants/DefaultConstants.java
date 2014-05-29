package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.TypeOfInterface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameType;

/**
 * Constants for default choices
 * 
 * @author Stefano
 * 
 */
public class DefaultConstants {
	/** The default kind of user interface */
	public static final TypeOfInterface DEFAULT_INTERFACE = TypeOfInterface.CONSOLE;

	/** the default type of game to play (default/extended rules) */
	public static final GameType DEFAULT_GAME_TYPE = GameType.ORIGINAL;

	/** Hide the default constructor */
	private DefaultConstants() {
	}
}
