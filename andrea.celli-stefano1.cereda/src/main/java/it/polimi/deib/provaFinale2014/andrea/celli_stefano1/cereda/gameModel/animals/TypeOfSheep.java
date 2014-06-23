package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Dice;

/**
 * The type (gender) of sheep, used for advanced rules
 * 
 * @author Stefano
 * */
public enum TypeOfSheep {
	NORMALSHEEP, MALESHEEP, FEMALESHEEP, BLACKSHEEP;

	/** @Return a random type of sheep from the advanced ones */
	public static TypeOfSheep getRandomAndvancedTypeOfSheep() {
		Dice dice = Dice.create();

		if (dice.roll(2) == 1) {
			return MALESHEEP;
		} else {
			return FEMALESHEEP;
		}
	}

	/** @Return a random type of sheep() excluding black sheep */
	public static TypeOfSheep getRandomTypeOfSheep() {
		Dice dice = Dice.create();
		int roll = dice.roll(TypeOfSheep.values().length - 1) - 1;

		return TypeOfSheep.values()[roll];
	}

	/** @return if it's a standard type of sheep */
	public boolean isNormal() {
		return this.equals(NORMALSHEEP);
	}
}
