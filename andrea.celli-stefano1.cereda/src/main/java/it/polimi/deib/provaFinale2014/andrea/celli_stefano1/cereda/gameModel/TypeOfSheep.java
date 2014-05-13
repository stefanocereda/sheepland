package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import java.util.Random;

//The type of sheep, used for advanced rules
public enum TypeOfSheep {
	NORMALSHEEP, MALESHEEP, FEMALESHEEP;
	
	//returns a random type from the advanced ones
	public static TypeOfSheep getRandomAndvancedTypeOfSheep(){
		Random rnd = new Random();
		
		if (rnd.nextBoolean() == true){
			return MALESHEEP;
		}
		else{
			return FEMALESHEEP;
		}
	}
}
