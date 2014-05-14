/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class AnimalTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Animal#Animal(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain)}
	 * .
	 */
	@Test
	public void testAnimal() {
		Animal a = new Animal(null);
		assertNotNull(a);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Animal#move(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain)}
	 * .
	 */
	@Test
	public void testMove() {
		Animal a = new Animal(null);

		for (Terrain t1 : Terrain.values()) {
			a.move(t1);
			Terrain t2 = a.getPosition();
			assertEquals(t1, t2);
		}
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Animal#getPosition()}
	 * .
	 */
	@Test
	public void testGetPosition() {
		for (Terrain t : Terrain.values()) {
			Animal a = new Animal(t);
			assertEquals(t, a.getPosition());
		}
	}

}
