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
public class SheepTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Sheep#Sheep(int, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.TypeOfSheep, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain)}
	 * .
	 */
	@Test
	public void testSheepIntTypeOfSheepTerrain() {
		int age = 10;
		TypeOfSheep tos = TypeOfSheep.MALESHEEP;
		Terrain position = Terrain.C1;

		Sheep sheep = new Sheep(age, tos, position);
		assertNotNull(sheep);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Sheep#Sheep(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain)}
	 * .
	 */
	@Test
	public void testSheepTerrain() {
		Terrain position = Terrain.C1;
		Sheep sheep = new Sheep(position);
		assertNotNull(sheep);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Sheep#getAge()}
	 * .
	 */
	@Test
	public void testGetAge() {
		int age = 10;
		TypeOfSheep tos = TypeOfSheep.MALESHEEP;
		Terrain position = Terrain.C1;

		Sheep sheep = new Sheep(age, tos, position);
		assertEquals(sheep.getAge(), age);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Sheep#ageIcrement()}
	 * .
	 */
	@Test
	public void testAgeIcrement() {
		int age = 10;
		TypeOfSheep tos = TypeOfSheep.MALESHEEP;
		Terrain position = Terrain.C1;

		Sheep sheep = new Sheep(age, tos, position);
		sheep.ageIcrement();
		assertEquals(sheep.getAge(), age + 1);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Sheep#getTypeOfSheep()}
	 * .
	 */
	@Test
	public void testGetTypeOfSheep() {
		int age = 10;
		TypeOfSheep tos = TypeOfSheep.MALESHEEP;
		Terrain position = Terrain.C1;

		Sheep sheep = new Sheep(age, tos, position);
		sheep.ageIcrement();
		assertEquals(sheep.getTypeOfSheep(), tos);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Sheep#setNewRandomAndvancedTypeOfSheep()}
	 * .
	 */
	@Test
	public void testSetNewRandomAndvancedTypeOfSheep() {
		Terrain t = Terrain.C1;
		Sheep sheep = new Sheep(t);

		sheep.setNewRandomAndvancedTypeOfSheep();
		assertNotEquals(sheep.getTypeOfSheep(), TypeOfSheep.NORMALSHEEP);
	}

}
