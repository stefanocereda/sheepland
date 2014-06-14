package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.BlackSheepPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.LambPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.PiecesOnTheMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.RamPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.SheepPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import java.awt.Color;
import java.awt.Point;
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

		if (status.equals(GameStatus.MOVE_PLAYER)) {
			return getPawnToMove(e.getPoint());
		} else {
			if (status.equals(GameStatus.MOVE_SHEEP)) {
				return getSheepToMove(e.getPoint());
			}
		}
		return null;
	}

	/**
	 * Check if the mouse was pressed on the right point and, if so, returns the
	 * "animal panel" that has to be dragged. The moveSheepMove can take place
	 * on sheeps,lambs,rams and on the black sheep. If the mouse is pressed on
	 * one of the first three type of panels the system creates an new panel
	 * without any number. (This panel is used only during the dragging process.
	 * When the mouse is released it will be deleted.)
	 * 
	 * The method checks: 0) if the mouse was pressed on a terrain 1) if the
	 * terrain is adjacent to the current player position 2) if the click was
	 * inside one of the three possible panel
	 * 
	 * @param point
	 * @return SheepPanel (the panel that will be dragged)
	 */
	private PiecesOnTheMap getSheepToMove(Point point) {

		Color colorPressed = paintedMap.findColor(point);
		Terrain pressedTerrain;

		// check 0
		if (linker.getColorsAndTerrain().containsKey(colorPressed)) {
			pressedTerrain = linker.getColorsAndTerrain().get(colorPressed);
		} else {
			return null;
		}
		Terrain[] adjacentTerrains = interfaceGui.getGameController()
				.getControlledPlayer().getPosition().getAdjacentTerrains();

		// check condition 1
		if (pressedTerrain.equals(adjacentTerrains[0])
				|| pressedTerrain.equals(adjacentTerrains[1])) {

			// get the sheep panel, if it doesn't exists returns null
			for (PiecesOnTheMap panel : map.getComponentsInTerrains().get(
					pressedTerrain)) {

				// check condition 2 for each possible type of panel

				if (panel instanceof SheepPanel) {
					// check if the panel contains the pressed terrain
					if (panel.contains(point)) {
						// creates the new panel with the sheep without number
						SheepPanel sheepToBeDragged = new SheepPanel(
								GuiConstants.EMPTY_SHEEP, map
										.getDimensionCalculator()
										.getSheepDimension());
						map.add(sheepToBeDragged);
						// the location is the position where the mouse was
						// pressed
						sheepToBeDragged.setLocation(point);
						sheepToBeDragged.setVisible(true);
						return sheepToBeDragged;
					}
				} else {
					if (panel instanceof LambPanel) {
						// creates the new panel with the lamb without number
						LambPanel lambToBeDragged = new LambPanel(
								GuiConstants.EMPTY_LAMB, map
										.getDimensionCalculator()
										.getLambDimension());
						map.add(lambToBeDragged);
						// the location is the position where the mouse was
						// pressed
						lambToBeDragged.setLocation(point);
						lambToBeDragged.setVisible(true);
						return lambToBeDragged;
					} else {
						if (panel instanceof RamPanel) {
							// creates the new panel with the ram without number
							RamPanel ramToBeDragged = new RamPanel(
									GuiConstants.EMPTY_RAM, map
											.getDimensionCalculator()
											.getRamDimension());
							map.add(ramToBeDragged);
							// the location is the position where the mouse was
							// pressed
							ramToBeDragged.setLocation(point);
							ramToBeDragged.setVisible(true);
							return ramToBeDragged;
						} else {
							if (panel instanceof BlackSheepPanel) {
								return panel;
							}
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * This method checks if the player has pressed the mouse on the road in
	 * which currently lies his pawn. If the selected road is right the method
	 * returns the pawn coresponding to the player. Otherwise returns null.
	 * 
	 * Condition that have to be checked: 1) the player pressed the mouse on a
	 * road 2) the player's pawn is on the selected road
	 * 
	 * @param point
	 * @return pawn
	 */
	private PiecesOnTheMap getPawnToMove(Point point) {

		Color pressedColor = paintedMap.findColor(point);
		Road pressedRoad;

		// check 1
		if (linker.getColorsAndRoad().containsKey(pressedColor)) {
			pressedRoad = linker.getColorsAndRoad().get(pressedColor);
		} else {
			return null;
		}

		// check 2
		if (interfaceGui.getGameController().getControlledPlayer()
				.getPosition().equals(pressedRoad)) {
			return map.getPawnsLocation().get(pressedRoad);
		}

		return null;
	}
}
