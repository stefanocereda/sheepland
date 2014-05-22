/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Animal;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class AnimalTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Animal#Animal(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain)}
	 * .
	 */
	@Test
	public void testAnimal() {
		Animal a = new Animal(null);
		a.setID();
		assertNotNull(a);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Animal#move(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain)}
	 * .
	 */
	@Test
	public void testMove() {
		Animal a = new Animal(null);
		a.setID();

		for (Terrain t1 : Terrain.values()) {
			a.move(t1);
			Terrain t2 = a.getPosition();
			assertEquals(t1, t2);
		}
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Animal#getPosition()}
	 * .
	 */
	@Test
	public void testGetPosition() {
		for (Terrain t : Terrain.values()) {
			Animal a = new Animal(t);
			a.setID();
			assertEquals(t, a.getPosition());
		}
	}

}
