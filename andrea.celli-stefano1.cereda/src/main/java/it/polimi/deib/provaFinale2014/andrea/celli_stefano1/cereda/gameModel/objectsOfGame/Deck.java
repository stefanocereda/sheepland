package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A deck of available territorial cards, every game must have his own
 * 
 * @author Stefano
 * 
 */
public class Deck extends ArrayList<Card> {

	/** Constructor of a deck, automatically populates the deck */
	public Deck() {
		super();
		populateDeck();
	}

	/** Fills the deck with all the cards */
	private void populateDeck() {
		for (Card c : Card.values()) {
			this.add(c);
		}
	}

	/**
	 * Get a random initial card (if exists) and removes it from the deck.
	 * 
	 * @return a random initial card
	 */
	public Card extractInitialCard() {
		List<Card> initials = getInitialCards();
		Random rnd = new Random();

		Card c = initials.get(rnd.nextInt(initials.size()));
		this.remove(c);

		return c;
	}

	/** Get an ArrayList of initial cards */
	private List<Card> getInitialCards() {
		List<Card> initials = new ArrayList<Card>();

		// For every card in the deck check if it's initial and eventually add
		// it
		for (int i = 0; i < this.size(); i++)
			if (this.get(i).isInitial())
				initials.add(this.get(i));

		return initials;
	}

	/** Delete all the remaining initial cards in the deck */
	public void deleteRemainingInitialCards() {
		for (Card c : this.toArray(new Card[this.size()])) {
			if (c.isInitial()) {
				this.remove(c);
			}
		}

	}

	/**
	 * This method checks whether a card has been removed or it's still in the
	 * deck
	 * 
	 * @return true if the card is in the deck
	 */
	public boolean isInTheDeck(Card card) {
		for (int i = 0; i < this.size(); i++)
			if (this.get(i).equals(card))
				return true;
		return false;
	}
}
