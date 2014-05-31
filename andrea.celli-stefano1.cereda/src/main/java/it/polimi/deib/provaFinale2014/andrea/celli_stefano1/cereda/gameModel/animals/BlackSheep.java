package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

/**
 * A blacksheep is an animal
 * 
 * @author Stefano
 */
public class BlackSheep extends Sheep {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8282840297106712907L;

	// * BlackSheep constructor only calls the superclass constructor, I think
	// that creating a default constructor that places the blackSheep at
	// Sheepsburg would be an error, because that's a rule, here it's a model
	public BlackSheep(Terrain pos) {
		super(pos);
	}
}
