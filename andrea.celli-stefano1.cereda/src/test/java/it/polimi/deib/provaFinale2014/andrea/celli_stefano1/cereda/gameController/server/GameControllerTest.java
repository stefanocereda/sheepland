package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;

import org.junit.Test;

/**
 * 
 * @author Stefano
 * 
 */
public class GameControllerTest {

	@Test
	public void testConstructor() {

		GameController gc1 = new GameController(null);
		GameController gc2 = new GameController(null);

		assertNotNull(gc1);
		assertNotNull(gc2);
		assertNotEquals(gc1, gc2);
	}
}
