package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class give the possibility to perform drag&drop operations.
 * 
 * @author Andrea
 * 
 */
public class DragAndDrop implements MouseListener, MouseMotionListener {

	/**
	 * The map panel
	 */
	private Map map;

	/**
	 * A flag that's used to determine the behaviour of the listeners according
	 * to the current status of the game
	 */
	private GameStatus status;

	/**
	 * A reference to the JLabel which is being dragged
	 */
	private PiecesOnTheMap draggedLabel;

	/**
	 * The linker class
	 */
	private Linker linker;

	/**
	 * The class that has to verify the conditions to start and end a d&d action
	 */
	private Verifier verifier;

	//
	//
	//
	//
	/**
	 * @param map
	 * @param verifier
	 */
	public DragAndDrop(Map map, Verifier verifier) {
		this.map = map;
		this.verifier = verifier;
		draggedLabel = null;
		status = GameStatus.NOT_YOUR_TURN;
	}

	/**
	 * if the draggedLabel is not null the position of the dragged object is
	 * updated and the map is repainted.
	 */
	public void mouseDragged(MouseEvent e) {
		// chech if the player is dragging something
		if (draggedLabel != null) {
			draggedLabel.setLocation(e.getX(), e.getY());
			map.repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * This method checks, depending on the status flag, wheter the player is
	 * trying to move the right label or not. If the piece is draggable in the
	 * current situation it sets the Label as the draggedLabel.
	 */
	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
