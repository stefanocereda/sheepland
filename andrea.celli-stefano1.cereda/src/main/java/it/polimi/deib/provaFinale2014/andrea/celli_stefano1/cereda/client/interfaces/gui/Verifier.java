package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;


/**
 * This class implements methods to verify that the starting point of the d&d
 * and the end of it are performed on valid positions and on valid labels.
 * 
 * It needs to have acces to the boardStatus of the game.
 * 
 * This class implements the singleton pattern.
 * 
 * @author Andrea
 * 
 */
public class Verifier {

	/**
	 * The interfaceGUI allows to get acces to the updated boardStatus and call
	 * methods to comunicates with it
	 */
	private InterfaceGui interfaceGui;

	/**
	 * The linker instance used to fill the gap between view and model
	 */
	private Linker linker;

	/**
	 * The constructor sets a reference to the interfaceGUI and to the linker.
	 * 
	 * @param gameController
	 * @param mapDimension
	 *            (effective dimension of the displayed map)
	 */
	public Verifier(InterfaceGui interfaceGui) {
		this.interfaceGui = interfaceGui;

		linker = Linker.getLinkerInsance();
	}

}
