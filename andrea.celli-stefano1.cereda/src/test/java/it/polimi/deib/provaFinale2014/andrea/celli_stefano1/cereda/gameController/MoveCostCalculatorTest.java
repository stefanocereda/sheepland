/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.MoveCostCalculator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.RoadMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class MoveCostCalculatorTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.MoveCostCalculator#create()}
	 * .
	 */
	@Test
	public void testCreate() {
		MoveCostCalculator mcc1 = MoveCostCalculator.create();
		MoveCostCalculator mcc2 = MoveCostCalculator.create();

		assertNotNull(mcc1);
		assertNotNull(mcc2);
		assertEquals(mcc1, mcc2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.MoveCostCalculator#getMoveCost(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move)}
	 * .
	 */
	@Test
	public void testGetMoveCostMove() {
		MoveCostCalculator calc = MoveCostCalculator.create();
		// Test all the possible moves

		// black sheep
		Move mbp = new MoveBlackSheep(null, null, null); // should be free
		assertEquals(calc.getMoveCost(mbp), 0);

		// player
		Player p = new Player();

		Road r1 = new Road(0, null, null);
		Road r2 = new Road(0, null, null);
		Road r3 = new Road(0, null, null);

		r1.add(r2);// Road.add adds r2 to r1 adjacent list
		r2.add(r1);

		p.move(r1);

		MovePlayer mp1 = new MovePlayer(p, r2, 0);// should be free
		Move mp2 = new MovePlayer(p, r3, 0);// should cost 1

		assertEquals(calc.getMoveCost(mp1), 0);// this fails!!!the getMoveCost
												// is not dynamically binded to
												// the right method
		assertEquals(calc.getMoveCost(mp2), 1);
	}
}