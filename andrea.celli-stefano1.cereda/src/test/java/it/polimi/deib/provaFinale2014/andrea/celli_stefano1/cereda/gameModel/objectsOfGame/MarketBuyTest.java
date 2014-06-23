package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;

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
	public void isValidBuyAndExecuteTest() {

		BoardStatusExtended bse = new BoardStatusExtended(2);

		Player p = new Player();
		p.setID();
		bse.addPlayerToBoardStatus(p);
		p.setMoney(20);

		Player p2 = new Player();
		p2.setID();
		bse.addPlayerToBoardStatus(p2);
		p2.setMoney(20);

		ArrayList<MarketOffer> offers = new ArrayList<MarketOffer>();

		MarketOffer mo = new MarketOffer(p2, Card.COUNTRYSIDE2, 10);
		mo.setID();
		offers.add(mo);
		p2.addCard(Card.COUNTRYSIDE2);

		MarketOffer mo2 = new MarketOffer(p2, Card.COUNTRYSIDE3, 40);
		mo.setID();
		offers.add(mo2);
		p2.addCard(Card.COUNTRYSIDE3);

		MarketBuy mb = new MarketBuy(p, Card.COUNTRYSIDE2);
		mb.setID();
		MarketBuy mb2 = new MarketBuy(p, Card.COUNTRYSIDE3);
		mb2.setID();
		MarketBuy mb3 = new MarketBuy(p, Card.MOUNTAIN3);
		mb3.setID();

		assertTrue(mb.isValidBuy(offers, p));
		assertFalse(mb2.isValidBuy(offers, p));
		assertFalse(mb3.isValidBuy(offers, p));

		mb.execute(offers, bse);

		// check money of the two player
		assertEquals(p.getMoney(), 10);
		assertEquals(p2.getMoney(), 30);

		// check cards of the two players
		ArrayList<Card> cardsP = (ArrayList<Card>) p.getCards();
		ArrayList<Card> cardsP2 = (ArrayList<Card>) p2.getCards();

		assertTrue(cardsP.size() == 1);
		assertTrue(cardsP2.size() == 1);
		assertEquals(cardsP.get(0), Card.COUNTRYSIDE2);
		assertEquals(cardsP2.get(0), Card.COUNTRYSIDE3);

		// check the offers' list
		assertTrue(offers.size() == 1);
		assertTrue(offers.get(0).equals(mo2));

	}

}
