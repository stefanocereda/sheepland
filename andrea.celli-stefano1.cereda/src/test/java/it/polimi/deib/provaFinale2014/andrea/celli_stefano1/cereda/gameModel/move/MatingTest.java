/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.Map;

import org.junit.Test;

/**
 * @author stefano
 * 
 */
public class MatingTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Mating#isValid(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus)}
	 * . Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Mating#execute(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus)}
	 * .
	 */
	@Test
	public void testIsValidAndExecute() {
		BoardStatusExtended bs = new BoardStatusExtended(1);
		Map<Integer, Road> roadMap = bs.getRoadMap().getHashMapOfRoads();

		Player p = new Player();
		p.setID();
		bs.addPlayerToBoardStatus(p);
		bs.setCurrentPlayer(p);

		Sheep maleSheep = new Sheep(3, TypeOfSheep.MALESHEEP, Terrain.C1);
		Sheep femaleSheep = new Sheep(4, TypeOfSheep.FEMALESHEEP, Terrain.C2);
		Sheep lamb = new Sheep(Terrain.C3);
		maleSheep.setID();
		femaleSheep.setID();
		lamb.setID();
		bs.addSheep(femaleSheep);
		bs.addSheep(maleSheep);
		bs.addSheep(lamb);

		// The player on a distant road
		p.move(roadMap.get(42));
		Move move = new Mating(p, Terrain.C1);
		assertFalse(move.isValid(bs));

		// the player adjacent to a male sheep and a female sheep on two
		// different terrain
		p.move(roadMap.get(2));
		assertFalse(move.isValid(bs));

		// place a lamb on the terrain
		lamb.move(Terrain.C1);
		assertFalse(move.isValid(bs));

		// and finally with a female sheep
		femaleSheep.move(Terrain.C1);
		assertTrue(move.isValid(bs));

		// and without the lamb
		lamb.move(Terrain.W1);
		assertTrue(move.isValid(bs));

		// also execute it
		do {
			move.execute(bs);
		} while (bs.getSheeps().size() < 4);

		Sheep newLamb = null;
		for (Sheep s : bs.getSheeps()) {
			if (!s.equals(lamb) && !s.equals(maleSheep)
					&& !s.equals(femaleSheep)) {
				newLamb = s;
				break;
			}
		}

		assertNotNull(newLamb);
		assertEquals(TypeOfSheep.NORMALSHEEP, newLamb.getTypeOfSheep());
		assertEquals(0, newLamb.getAge());
		assertEquals(Terrain.C1, newLamb.getPosition());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Mating#Mating(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain)}
	 * .
	 */
	@Test
	public void testMating() {
		Mating move = new Mating(null, null);
		assertNotNull(move);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Mating#getTerrain()}
	 * .
	 */
	@Test
	public void testGetTerrain() {
		Terrain t = Terrain.P3;
		Mating move = new Mating(null, t);
		assertEquals(t, move.getTerrain());
	}

}
