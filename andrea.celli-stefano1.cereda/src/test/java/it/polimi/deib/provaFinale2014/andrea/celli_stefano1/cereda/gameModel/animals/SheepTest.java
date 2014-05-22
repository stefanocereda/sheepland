/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class SheepTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep#Sheep(int, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain)}
	 * .
	 */
	@Test
	public void testSheepIntTypeOfSheepTerrain() {
		int age = 10;
		TypeOfSheep tos = TypeOfSheep.MALESHEEP;
		Terrain position = Terrain.C1;

		Sheep sheep = new Sheep(age, tos, position);
		sheep.setID();
		assertNotNull(sheep);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep#Sheep(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain)}
	 * .
	 */
	@Test
	public void testSheepTerrain() {
		Terrain position = Terrain.C1;
		Sheep sheep = new Sheep(position);
		sheep.setID();
		assertNotNull(sheep);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep#getAge()}
	 * .
	 */
	@Test
	public void testGetAge() {
		int age = 10;
		TypeOfSheep tos = TypeOfSheep.MALESHEEP;
		Terrain position = Terrain.C1;

		Sheep sheep = new Sheep(age, tos, position);
		sheep.setID();
		assertEquals(sheep.getAge(), age);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep#ageIcrement()}
	 * .
	 */
	@Test
	public void testAgeIcrement() {
		int age = 10;
		TypeOfSheep tos = TypeOfSheep.MALESHEEP;
		Terrain position = Terrain.C1;

		Sheep sheep = new Sheep(age, tos, position);
		sheep.setID();
		sheep.ageIcrement();
		assertEquals(sheep.getAge(), age + 1);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep#getTypeOfSheep()}
	 * .
	 */
	@Test
	public void testGetTypeOfSheep() {
		int age = 10;
		TypeOfSheep tos = TypeOfSheep.MALESHEEP;
		Terrain position = Terrain.C1;

		Sheep sheep = new Sheep(age, tos, position);
		sheep.setID();
		sheep.ageIcrement();
		assertEquals(sheep.getTypeOfSheep(), tos);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep#setNewRandomAndvancedTypeOfSheep()}
	 * .
	 */
	@Test
	public void testSetNewRandomAndvancedTypeOfSheep() {
		Terrain t = Terrain.C1;
		Sheep sheep = new Sheep(t);
		sheep.setID();

		sheep.setNewRandomAndvancedTypeOfSheep();
		assertNotEquals(sheep.getTypeOfSheep(), TypeOfSheep.NORMALSHEEP);
	}

}
