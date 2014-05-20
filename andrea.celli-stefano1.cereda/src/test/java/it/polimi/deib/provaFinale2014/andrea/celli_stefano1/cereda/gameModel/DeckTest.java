/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class DeckTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck#Deck()}
	 * .
	 */
	@Test
	public void testDeck() {
		Deck d1 = new Deck();
		Deck d2 = new Deck();

		assertNotNull(d1);
		assertNotNull(d2);

		assertFalse(d1 == d2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck#extractInitialCard()}
	 * .
	 */
	@Test
	public void testExtractInitialCard() {
		// try to extract all the initial cards
		Deck deck = new Deck();

		// first of all search all the initial cards
		ArrayList<Card> initialCards = new ArrayList<Card>();
		for (Card c : Card.values())
			if (c.name().contains("i"))
				initialCards.add(c);

		// now build a list of extracted initial cards
		ArrayList<Card> extractedCards = new ArrayList<Card>();
		for (int i = 0; i < initialCards.size(); i++)
			extractedCards.add(deck.extractInitialCard());

		// Now check if the extracted cards are initials
		for (Card c : extractedCards)
			assertTrue(c.isInitial());

		// Check that they are different from one another
		for (int i = 0; i < extractedCards.size() - 1; i++)
			for (int j = i + 1; j < extractedCards.size(); j++)
				assertNotEquals(extractedCards.get(i), extractedCards.get(j));

		// Check that we have actually extracted all the cards
		for (Card c : extractedCards)
			initialCards.remove(c);
		assertEquals(initialCards.size(), 0);
	}
}
