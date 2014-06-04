/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class TypeOfSheepTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep#getRandomAndvancedTypeOfSheep()}
	 * .
	 */
	@Test
	public void testGetRandomAndvancedTypeOfSheep() {
		TypeOfSheep type = TypeOfSheep.getRandomAndvancedTypeOfSheep();

		assertTrue(type.equals(TypeOfSheep.FEMALESHEEP)
				|| type.equals(TypeOfSheep.MALESHEEP));
		assertFalse(type.equals(TypeOfSheep.NORMALSHEEP));
	}

}
