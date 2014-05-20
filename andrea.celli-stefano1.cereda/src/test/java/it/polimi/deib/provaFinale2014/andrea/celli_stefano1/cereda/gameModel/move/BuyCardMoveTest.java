/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;

import org.junit.Test;

/**
 * @author stefano
 * 
 */
public class BuyCardMoveTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove#BuyCardMove(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card)}
	 * .
	 */
	@Test
	public void testBuyCardMove() {
		BuyCardMove bcm = new BuyCardMove(null, null);
		assertNotNull(bcm);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove#getNewCard()}
	 * .
	 */
	@Test
	public void testGetNewCard() {
		for (Card c : Card.values()) {
			BuyCardMove bcm = new BuyCardMove(null, c);
			assertEquals(c, bcm.getNewCard());
		}
	}

}
