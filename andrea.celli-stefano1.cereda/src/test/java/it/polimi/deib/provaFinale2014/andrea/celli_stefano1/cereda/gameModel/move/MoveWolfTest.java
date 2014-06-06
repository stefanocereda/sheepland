/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Wolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Gate;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class MoveWolfTest {

	/**
	 * Test method for all the methods
	 */
	@Test
	public final void testMoveWolf() {
		BoardStatusExtended bs = new BoardStatusExtended(1);

		Wolf wolf = new Wolf(Terrain.SHEEPSBURG);
		wolf.setID();
		bs.addWolfToBoardStatus(wolf);

		Sheep s = new Sheep(Terrain.C3);
		s.setID();
		bs.addSheep(s);

		MoveWolf mw = new MoveWolf(wolf, Terrain.C3, s);

		assertTrue(mw.isValid(bs));

		// now place a gate on the road
		Gate g = new Gate(false, bs.getRoadMap().getHashMapOfRoads().get(21));
		g.setID();
		bs.addPlacedGateToBoardStatus(g);
		assertFalse(mw.isValid(bs));

		// now go on a terrain without the sheep
		mw = new MoveWolf(wolf, Terrain.M1, s);
		assertFalse(mw.isValid(bs));

		// now go on a terrain with the sheep but not adjacent
		s.move(Terrain.D2);
		mw = new MoveWolf(wolf, Terrain.D2, s);
		assertFalse(mw.isValid(bs));

		// now go on an adjacent terrain without the sheep but don't kill
		mw = new MoveWolf(wolf, Terrain.D1, null);
		assertTrue(mw.isValid(bs));

		// execute it, the wolf should go in D1
		mw.execute(bs);
		assertEquals(wolf, mw.getWolf());
		assertEquals(wolf, bs.getWolf());
		assertEquals(Terrain.D1, wolf.getPosition());

		// now place the sheep in L1 and eat it
		s.move(Terrain.L1);
		mw = new MoveWolf(wolf, Terrain.L1, s);
		assertTrue(mw.isValid(bs));
		mw.execute(bs);
		assertFalse(bs.getSheeps().contains(s));

		// some more test
		assertEquals(Terrain.L1, mw.getNewPosition());
		assertEquals(s, mw.getKilledSheep());
		assertNotNull(mw.toString());
		assertEquals(MoveCostCalculator.getMoveCost(mw, bs), 0);
	}
}
