package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import java.awt.Dimension;

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
	 * The constructor also creates an instance of the linker and initializes it
	 * 
	 * @param gameController
	 * @param mapDimension
	 *            (effective dimension of the displayed map)
	 */
	public Verifier(InterfaceGui interfaceGui, Dimension mapDimension) {
		this.interfaceGui = interfaceGui;

		linker = Linker.getLinkerInsance();
		linker.initLinker(interfaceGui.getGameController().getBoardStatus(),
				mapDimension);
	}

}
