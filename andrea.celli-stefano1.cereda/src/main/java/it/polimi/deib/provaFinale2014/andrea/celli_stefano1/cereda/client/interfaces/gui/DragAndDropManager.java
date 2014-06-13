package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import java.awt.event.MouseEvent;

/**
 * This class implements methods to verify that the starting point of the d&d
 * and the end of it are performed on valid positions and on valid labels.
 * 
 * It needs to have acces to the boardStatus of the game and the panels
 * currently on the map.
 * 
 * This class implements the singleton pattern.
 * 
 * @author Andrea
 * 
 */
public class DragAndDropManager {

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
	 * The map
	 */
	private Map map;

	/**
	 * The class that allows to determine a point position in the game through
	 * its color.
	 */
	private PaintedMap paintedMap;

	/**
	 * The constructor sets a reference to the interfaceGUI and to the linker.
	 * 
	 * It also creates an instance of the painted map.
	 * 
	 * @param gameController
	 * @param mapDimension
	 *            (effective dimension of the displayed map)
	 */
	public DragAndDropManager(InterfaceGui interfaceGui, Map map) {
		this.interfaceGui = interfaceGui;

		this.map = map;

		linker = Linker.getLinkerInsance();

		paintedMap = new PaintedMap(map.getSize());
	}

	/**
	 * This method checks if the mouse is pressed on the right location and
	 * returns the component that has to be dragged.
	 * 
	 * @param e
	 * @param status
	 * @return the piece to be dragged (null if the move is not allowed)
	 */
	public PiecesOnTheMap getPanelToMove(MouseEvent e, GameStatus status) {

		switch (status) {
		case MOVE_PLAYER:
			break;
		case MOVE_SHEEP:
			break;
		case BUTCHERING:
			break;
		case MATING:
			break;
		}

		return null;
	}

}
