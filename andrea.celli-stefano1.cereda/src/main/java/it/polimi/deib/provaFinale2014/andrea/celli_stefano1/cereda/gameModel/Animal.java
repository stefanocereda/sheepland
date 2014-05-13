package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

//the class of an animal
public class Animal {

	// the position of this animal
	private Terrain position;

	// move the animal in a new position
	public void move(Terrain newPosition) {
		position = newPosition;
	}

	// get the position of the animal
	public Terrain getPosition() {
		return position;
	}
}
