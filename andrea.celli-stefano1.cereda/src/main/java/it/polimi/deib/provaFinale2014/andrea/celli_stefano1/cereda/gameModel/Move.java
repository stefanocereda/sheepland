package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

/**
 * The move is used for client/server communications, for rule checking and to
 * actually execute them
 * 
 * @author Stefano
 * 
 */
public class Move {
	/** The kind of move (ie move a sheep or buy a card) */
	private KindOfMove kindOfMove;
	/** the move involves this player */
	private Player player;
	/** the move involves this road */
	private Road road;
	/** the move involves this terrain */
	private Terrain terrain;
	/** The move involves this animal */
	private Animal animal;
	/** The move involves this card */
	private Card card;

	/**
	 * @return the kind of move
	 */
	public KindOfMove getKindOfMove() {
		return kindOfMove;
	}

	/**
	 * @return the player involved
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the road involved
	 */
	public Road getRoad() {
		return road;
	}

	/**
	 * @return the terrain involved
	 */
	public Terrain getTerrain() {
		return terrain;
	}

	/**
	 * @return the animal involved
	 */
	public Animal getAnimal() {
		return animal;
	}

	/**
	 * @return the card involved
	 */
	public Card getCard() {
		return card;
	}

}