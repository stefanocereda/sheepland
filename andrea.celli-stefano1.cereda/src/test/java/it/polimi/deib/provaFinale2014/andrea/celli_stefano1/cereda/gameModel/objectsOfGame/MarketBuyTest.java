package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import org.junit.Test;

/**
 * Test for MarketBuy
 * 
 * @author Andrea
 * 
 */
public class MarketBuyTest {

	@Test
	public void marketBuyTest() {

		Player player = new Player();
		player.setID();

		MarketBuy mb = new MarketBuy(player, Card.COUNTRYSIDE1);

		assertFalse(mb.equals(null));
		assertTrue(mb.getBuyer().equals(player));
		assertTrue(mb.getCardBought().equals(Card.COUNTRYSIDE1));
	}

	@Test
	public void isValidBuyTest() {

		BoardStatusExtended bse = new BoardStatusExtended(1);

		Player p = new Player();
		p.setID();
		bse.addPlayerToBoardStatus(p);
		p.setMoney(20);

		Player p2 = new Player();
		p2.setID();
		bse.addPlayerToBoardStatus(p2);
		p2.setMoney(20);

		MarketOffer mo = new MarketOffer(p2, Card.COUNTRYSIDE2, 10);
		mo.setID();
		MarketBuy mb = new MarketBuy(p, Card.COUNTRYSIDE2);
		mb.setID();

	}

}
