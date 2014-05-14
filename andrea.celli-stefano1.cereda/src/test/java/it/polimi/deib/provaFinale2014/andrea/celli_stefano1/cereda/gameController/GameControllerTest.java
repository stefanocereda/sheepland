package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientHandler;

import org.junit.Test;

/**
 * 
 * @author Stefano
 * 
 */
public class GameControllerTest {

	@Test
	public void testConstructor() {
		ClientHandler ch = null;
		GameType gt1 = GameType.ORIGINAL;
		GameType gt2 = GameType.EXTENDED;

		GameController gc1 = new GameController(null, gt1);
		GameController gc2 = new GameController(null, gt2);

		assertNotNull(gc1);
		assertNotNull(gc2);
		assertNotEquals(gc1, gc2);
	}
}
