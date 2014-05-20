package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import java.util.ArrayList;
import java.util.Random;

/**
 * A deck of available territorial cards, every game must have his own
 * 
 * @author Stefano
 * 
 */
public class Deck {
	/** the list of available cards */
	private ArrayList<Card> cards;

	/** Constructor of a deck, automatically populates the deck */
	public Deck() {
		cards = new ArrayList<Card>();
		populateDeck();
	}

	/** Fills the deck with all the cards */
	private void populateDeck() {
		for (Card c : Card.values()) {
			cards.add(c);
		}
	}

	/**
	 * Get a random initial card (if exists) and removes it from the deck.
	 * 
	 * @return a random initial card
	 */
	public Card extractInitialCard() {
		ArrayList<Card> initials = getInitialCards();
		Random rnd = new Random();

		Card c = initials.get(rnd.nextInt(initials.size()));
		cards.remove(c);

		return c;
	}

	/** Get an ArrayList of initial cards */
	private ArrayList<Card> getInitialCards() {
		ArrayList<Card> initials = new ArrayList<Card>();

		// For every card in the deck check if it's initial and eventually add
		// it
		for (int i = 0; i < cards.size(); i++)
			if (cards.get(i).isInitial())
				initials.add(cards.get(i));

		return initials;
	}

	/** Removes a card from the deck */
	public void removeCard(Card card) {
		cards.remove(card);
	}

}
