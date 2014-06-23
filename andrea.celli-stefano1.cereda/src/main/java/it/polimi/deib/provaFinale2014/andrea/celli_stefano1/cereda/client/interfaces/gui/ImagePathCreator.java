package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

/**
 * This class defines methods that allow, given the number of animal, to find
 * the right image name in the resources
 * 
 * @author Andrea
 * 
 */
public class ImagePathCreator {
	/** Hide the default constructor */
	private ImagePathCreator() {
	}

	public static final String findSheepPath(int numberOfSheep) {
		return "/pecora" + numberOfSheep + ".png";
	}

	public static final String findLambPath(int numberOfLambs) {
		return "/agnello" + numberOfLambs + ".png";
	}

	public static final String findRamPath(int numberOfRam) {
		return "/montone" + numberOfRam + ".png";
	}

	public static final String findSheepPathNoNumber() {
		return "/sheepNoNumber.png";
	}

	public static final String findLambPathNoNumber() {
		return "/lambNoNumber.png";
	}

	public static final String findRamPathNoNumber() {
		return "/ramNoNumber.png";
	}

}
