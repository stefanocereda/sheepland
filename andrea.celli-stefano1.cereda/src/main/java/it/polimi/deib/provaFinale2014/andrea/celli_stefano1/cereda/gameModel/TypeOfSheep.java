package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import java.util.Random;

/**
 * The type (gender) of sheep, used for advanced rules
 * 
 * @author Stefano
 * */
public enum TypeOfSheep {
	NORMALSHEEP, MALESHEEP, FEMALESHEEP;

	/** Returns a random type of sheep from the advanced ones */
	public static TypeOfSheep getRandomAndvancedTypeOfSheep() {
		Random rnd = new Random();

		if (rnd.nextBoolean() == true) {
			return MALESHEEP;
		} else {
			return FEMALESHEEP;
		}
	}
}
