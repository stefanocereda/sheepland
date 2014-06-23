package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import org.junit.Test;

/**
 * Test for MarketOffers
 * 
 * @author Andrea
 * 
 */
public class MarketOfferTest {

	@Test
	public void constructorAndGettersTest() {
		BoardStatusExtended bse = new BoardStatusExtended(1);

		Player p = new Player();
		p.setID();
		bse.addPlayerToBoardStatus(p);

		MarketOffer mo = new MarketOffer(p, Card.COUNTRYSIDE1, 10);

		assertNotNull(mo);
		assertEquals(mo.getOfferer(), p);
		assertEquals(mo.getCardOffered(), Card.COUNTRYSIDE1);
		assertEquals(mo.getPrice(), 10);
	}

	@Test
	public void isValidTest() {

		BoardStatusExtended bse = new BoardStatusExtended(1);

		Player p = new Player();
		p.setID();
		bse.addPlayerToBoardStatus(p);

		p.addCard(Card.COUNTRYSIDE_I);
		p.addCard(Card.COUNTRYSIDE1);

		MarketOffer mo1 = new MarketOffer(p, Card.COUNTRYSIDE1, 10);
		MarketOffer mo2 = new MarketOffer(p, Card.COUNTRYSIDE2, 10);
		MarketOffer mo3 = new MarketOffer(p, Card.COUNTRYSIDE_I, 10);

		assertTrue(mo1.isValidOffer(p));
		assertFalse(mo2.isValidOffer(p));
		assertFalse(mo3.isValidOffer(p));
	}
}
