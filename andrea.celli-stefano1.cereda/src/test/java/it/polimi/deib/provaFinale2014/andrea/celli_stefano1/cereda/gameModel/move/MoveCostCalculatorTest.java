/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.assertEquals;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
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
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveCostCalculator#getMoveCost(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move)}
	 * .
	 */
	@Test
	public void testGetMoveCostMove() {
		// Test all the possible moves

		// black sheep
		Move mbp = new MoveBlackSheep(null, null); // should be free
		mbp.setID();
		assertEquals(MoveCostCalculator.getMoveCost(mbp, null), 0);

		// player
		Player p = new Player();
		p.setID();
		BoardStatus boardStatus = new BoardStatus(1);
		boardStatus.addPlayerToBoardStatus(p);

		Road r1 = new Road(0, null, null);
		r1.setID();
		Road r2 = new Road(0, null, null);
		r2.setID();
		Road r3 = new Road(0, null, null);
		r3.setID();

		r1.add(r2);// Road.add adds r2 to r1 adjacent list
		r2.add(r1);

		p.move(r1);

		Move mp1 = new MovePlayer(p, r2);// should be free
		mp1.setID();
		Move mp2 = new MovePlayer(p, r3);// should cost 1
		mp2.setID();

		assertEquals(MoveCostCalculator.getMoveCost(mp1, boardStatus), 0);
		assertEquals(MoveCostCalculator.getMoveCost(mp2, boardStatus), 1);

		// sheep
		Move ms = new MoveSheep(null, null, null);// a sheep move is free
		ms.setID();
		assertEquals(MoveCostCalculator.getMoveCost(ms, null), 0);

		// buycard
		for (Card c : Card.values()) {
			Move bcm = new BuyCardMove(null, c);
			bcm.setID();
			assertEquals(MoveCostCalculator.getMoveCost(bcm, null),
					c.getNumber());
		}
	}
}
