package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

/**
 * A blacksheep is an animal
 * 
 * @author Stefano
 */
public class BlackSheep extends Animal {

	// * BlackSheep constructor only calls the superclass constructor, I think
	// that creating a default constructor that places the blackSheep at
	// Sheepsburg would be an error, because that's a rule, here it's a model
	public BlackSheep(Terrain pos) {
		super(pos);
	}

}
