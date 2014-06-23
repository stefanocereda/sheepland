/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import java.util.Random;

/**
 * A random number generator
 * 
 * @author Stefano
 * 
 */
public class Dice {
	private static Random rnd = new Random();
	private static Dice sharedDice = new Dice();

	/** Private constructor for singleton pattern */
	private Dice() {
	}

	/** Singleton constructor */
	public static Dice create() {
		if (sharedDice == null) {
			sharedDice = new Dice();
		}
		return sharedDice;
	}

	/** Get a random number from 1 to max (included) */
	public int roll(int max) {
		return rnd.nextInt(max) + 1;
	}
}
