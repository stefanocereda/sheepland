package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.PiecesOnTheMap;

import java.awt.Point;
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
	private GameMap map;

	/**
	 * A flag that's used to determine the behaviour of the listeners according
	 * to the current status of the game
	 */
	private GameStatus status;

	/**
	 * A reference to the JPanel which is being dragged
	 */
	private PiecesOnTheMap draggedPanel;

	/**
	 * The class that has to verify the conditions to start and end a d&d action
	 */
	private DragAndDropManager dragAndDropManager;

	/**
	 * A boolean flag used to check if the user is dragging something
	 */
	private boolean dragging;

	//
	//
	//
	//
	/**
	 * @param map
	 * @param verifier
	 */
	public DragAndDrop(GameMap map, DragAndDropManager dragAndDropManager) {
		this.map = map;
		this.dragAndDropManager = dragAndDropManager;
		draggedPanel = null;
		status = GameStatus.NOT_YOUR_TURN;
		dragging = false;
	}

	/**
	 * if the draggedLabel is not null the position of the dragged object is
	 * updated and the map is repainted.
	 */
	public void mouseDragged(MouseEvent e) {
		// chech if the player is dragging something
		if (draggedPanel != null && isInsideTheMapPanel(e.getPoint())) {
			draggedPanel.setLocation(e.getX(), e.getY());
			draggedPanel.repaint();
		}
	}

	/** Check if the dragged panel is entirely inside the map panel */
	private boolean isInsideTheMapPanel(Point point) {
		if (point.x > 0 && (point.x + draggedPanel.getWidth() < map.getWidth())
				&& point.y > 0
				&& (point.y + draggedPanel.getHeight() < map.getHeight()))
			return true;
		return false;
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * The clicks are considered only if the player has to choose the initial
	 * potition, to kill a sheep or to "force a mating".
	 * 
	 */
	public void mouseClicked(MouseEvent e) {

		if (status.equals(GameStatus.CHOOSE_INITIAL_POSITION)) {
			dragAndDropManager.manageInitialPosition(e.getPoint());
		} else {
			if (status.equals(GameStatus.MATING)) {
				dragAndDropManager.manageMating(e.getPoint());
			} else {
				if (status.equals(GameStatus.BUTCHERING)) {
					dragAndDropManager.manageButchering(e.getPoint());
				}
			}
		}

	}

	/**
	 * This method checks, depending on the status flag, wheter the player is
	 * trying to move the right label or not. If the piece is draggable in the
	 * current situation it sets the Panel as the draggedPanel.
	 */
	public void mousePressed(MouseEvent e) {
		if (status.equals(GameStatus.MOVE_PLAYER)
				|| status.equals(GameStatus.MOVE_SHEEP)) {
			draggedPanel = dragAndDropManager.getPanelToMove(e, status);
		}
	}

	/**
	 * Calls methods of the DragAndDropManager to check whether the release
	 * point was "legal" or not and, eventually, to udate the view.
	 */
	public void mouseReleased(MouseEvent e) {
		if (dragging) {
			dragging = false;
			dragAndDropManager.manageDrop(e, status, draggedPanel);
		}

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean isDragging() {
		return dragging;
	}

	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}

	/**
	 * Set the status to the given status
	 * 
	 * @param newStatus
	 *            The new status
	 */
	public void setStatus(GameStatus newStatus) {
		this.status = newStatus;
	}

}
