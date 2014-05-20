/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class PlayerTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player#getMoney()}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player#setMoney(int)}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player#subtractMoney(int)}
	 * 
	 */
	@Test
	public void testMoney() {
		Player p = new Player();
		p.setMoney(10);
		assertEquals(p.getMoney(), 10);

		p.subtractMoney(5);
		assertEquals(p.getMoney(), 5);
	}

	/**
	 * Test method for }
	 * 
	 * 
	 * /** Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player#move(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road)}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player#getPosition()}
	 */
	@Test
	public void testPosition() {
		Player p = new Player();
		Road r = new Road(0, null, null);
		p.move(r);

		assertEquals(r, p.getPosition());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player#addCard(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Card)}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player#getCards()}
	 */
	@Test
	public void testCard() {
		Player p = new Player();

		for (Card c : Card.values()) {
			p.addCard(c);
			assertTrue(p.getCards().contains(c));
		}

	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player#addLastMove(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move)}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player#getLastMoves()}
	 */
	@Test
	public void testLastMove() {
		Player p = new Player();
		Move m1 = new Move(p);
		Move m2 = new Move(p);
		p.addLastMove(m1);
		assertTrue(p.getLastMoves().contains(m1));
		assertFalse(p.getLastMoves().contains(m2));
	}

}
