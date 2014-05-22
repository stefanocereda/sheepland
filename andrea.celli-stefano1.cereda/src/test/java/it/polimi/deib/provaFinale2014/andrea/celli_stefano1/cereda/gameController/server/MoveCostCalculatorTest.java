/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.MoveCostCalculator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class MoveCostCalculatorTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.MoveCostCalculator#create()}
	 * .
	 */
	@Test
	public void testCreate() {
		MoveCostCalculator mcc1 = MoveCostCalculator.create();
		MoveCostCalculator mcc2 = MoveCostCalculator.create();

		assertNotNull(mcc1);
		assertNotNull(mcc2);
		assertTrue(mcc1 == mcc2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.MoveCostCalculator#getMoveCost(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move)}
	 * .
	 */
	@Test
	public void testGetMoveCostMove() {
		MoveCostCalculator calc = MoveCostCalculator.create();
		// Test all the possible moves

		// black sheep
		Move mbp = new MoveBlackSheep(null, null); // should be free
		mbp.setID();
		assertEquals(calc.getMoveCost(mbp), 0);

		// player
		Player p = new Player();
		p.setID();

		Road r1 = new Road(0, null, null);
		r1.setID();
		Road r2 = new Road(0, null, null);
		r2.setID();
		Road r3 = new Road(0, null, null);
		r3.setID();

		r1.add(r2);// Road.add adds r2 to r1 adjacent list
		r2.add(r1);

		p.move(r1);

		Move mp1 = new MovePlayer(p, r2, 0);// should be free
		mp1.setID();
		Move mp2 = new MovePlayer(p, r3, 0);// should cost 1
		mp2.setID();

		assertEquals(calc.getMoveCost(mp1), 0);
		assertEquals(calc.getMoveCost(mp2), 1);

		// sheep
		Move ms = new MoveSheep(null, null, null);// a sheep move is free
		ms.setID();
		assertEquals(calc.getMoveCost(ms), 0);

		// buycard
		for (Card c : Card.values()) {
			Move bcm = new BuyCardMove(null, c);
			bcm.setID();
			assertEquals(calc.getMoveCost(bcm), c.getNumber());
		}
	}
}
