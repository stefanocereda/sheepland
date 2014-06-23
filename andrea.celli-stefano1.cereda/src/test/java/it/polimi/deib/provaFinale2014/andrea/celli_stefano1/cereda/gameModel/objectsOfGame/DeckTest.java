/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
        for (Card c : Card.values()) {
            if (c.name().contains("i")) {
                initialCards.add(c);
            }
        }

        // now build a list of extracted initial cards
        ArrayList<Card> extractedCards = new ArrayList<Card>();
        for (int i = 0; i < initialCards.size(); i++) {
            extractedCards.add(deck.extractInitialCard());
        }

        // Now check if the extracted cards are initials
        for (Card c : extractedCards) {
            assertTrue(c.isInitial());
        }

        // Check that they are different from one another
        for (int i = 0; i < extractedCards.size() - 1; i++) {
            for (int j = i + 1; j < extractedCards.size(); j++) {
                assertNotEquals(extractedCards.get(i), extractedCards.get(j));
            }
        }

        // Check that we have actually extracted all the cards
        for (Card c : extractedCards) {
            initialCards.remove(c);
        }
        assertEquals(initialCards.size(), 0);
    }

    /**
     * Test method for getBuyableCard
     * 
     * @author Andrea
     */
    @Test
    public void getBuyableCardTest() {
        Deck deck = new Deck();

        // remove all the initial cards
        deck.deleteRemainingInitialCards();

        // removes all the cards of type plain, all the cards of type
        // mountain except for the fourth, the first lake card
        for (Card card : deck.toArray(new Card[deck.size()])) {
            if (card.getTerrainType().equals(TerrainType.PLAIN)) {
                deck.remove(card);
            }
            if (card.getTerrainType().equals(TerrainType.MOUNTAIN)
                    && card.getNumber() < 4) {
                deck.remove(card);
            }
            if (card.getTerrainType().equals(TerrainType.LAKE)
                    && card.getNumber() < 2) {
                deck.remove(card);
            }
        }

        // calculates the buyable cards remaining in the deck
        List<Card> buyable = deck.getBuyableCards();

        for (Card card : buyable) {
            assertNotEquals(TerrainType.PLAIN, card.getTerrainType());
            if (card.getTerrainType().equals(TerrainType.MOUNTAIN)) {
                assertEquals(card.getNumber(), 4);
            } else if (card.getTerrainType().equals(TerrainType.LAKE)) {
                assertEquals(card.getNumber(), 2);
            } else {
                assertEquals(card.getNumber(), 0);
            }
        }

    }
}
