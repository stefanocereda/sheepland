/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class WolfTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Wolf#Wolf(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain)}
	 * .
	 */
	@Test
	public void testWolf() {
		Wolf w = new Wolf(null);
		assertNotNull(w);
	}

}
