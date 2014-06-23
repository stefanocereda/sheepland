package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.BlackSheepPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.LambPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.PawnPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.PiecesOnTheMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.RamPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.SheepPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Butchering;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Mating;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private GameMap map;

    /**
     * The class that allows to determine a point position in the game through
     * its color.
     */
    private PaintedMap paintedMap;

    /**
     * Old player position (used during the drag of a pawn)
     */
    private Road oldPawnPosition;

    /**
     * Old animal position (used during the drag of an animal)
     */
    private Terrain oldAnimalPosition;

    /** A class logger */
    private static final Logger LOG = Logger.getLogger(DragAndDropManager.class
            .getName());

    /**
     * The constructor sets a reference to the interfaceGUI and to the linker.
     * 
     * It also creates an instance of the painted map.
     * 
     * @param gameController
     * @param mapDimension
     *            (effective dimension of the displayed map)
     */
    public DragAndDropManager(InterfaceGui interfaceGui, GameMap map) {
        this.interfaceGui = interfaceGui;

        this.map = map;

        linker = Linker.getLinkerInsance();

        paintedMap = new PaintedMap(map.getSize());

        oldAnimalPosition = null;

        oldPawnPosition = null;
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
        // check if the mouse was pressed inside the map. Otherwise the
        // paintedMap wouldn't be able to find a correspondent color
        if (isInsideTheMapImage(e.getPoint())) {
            if (status.equals(GameStatus.MOVE_PLAYER)) {
                return getPawnToMove(e.getPoint());
            } else {
                if (status.equals(GameStatus.MOVE_SHEEP)) {
                    return getSheepToMove(e.getPoint());
                }
            }
        }
        return null;
    }

    /**
     * This method checks if a point is inside the image of the map. This is
     * necessary to get the right answer in the painted map.
     * 
     * @param point
     * @return boolean (true if the point has a correspondent in the painted
     *         map)
     */
    private boolean isInsideTheMapImage(Point point) {

        // the border between the image and the end of the panel
        int border = (map.getWidth() - map.getMapDimension().width) / 2;
        int x = point.x;
        if (x > border && x < border + map.getMapDimension().width) {
            return true;
        }
        return false;
    }

    /**
     * Check if the mouse was pressed on the right point and, if so, returns the
     * "animal panel" that has to be dragged. The moveSheepMove can take place
     * on sheep,lambs,rams and on the black sheep. If the mouse is pressed on
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
                // check if the panel contains the pressed terrain
                if (panel.contains(point)) {
                    if (panel instanceof SheepPanel) {

                        // creates the new panel with the sheep without
                        // number
                        SheepPanel sheepToBeDragged = new SheepPanel(
                                GuiConstants.EMPTY_SHEEP, map
                                        .getDimensionCalculator()
                                        .getSheepDimension());
                        map.add(sheepToBeDragged);
                        // the location is the position where the mouse was
                        // pressed
                        sheepToBeDragged.setLocation(point);
                        sheepToBeDragged.setVisible(true);
                        // set to true the dragging flag in DragAndDrop
                        map.getListener().setDragging(true);
                        // store the old position of the animal
                        oldAnimalPosition = pressedTerrain;
                        return sheepToBeDragged;

                    } else if (panel instanceof LambPanel) {

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
                        // set to true the dragging flag in DragAndDrop
                        map.getListener().setDragging(true);
                        // store the old position of the animal
                        oldAnimalPosition = pressedTerrain;
                        return lambToBeDragged;

                    } else if (panel instanceof RamPanel) {

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
                        // set to true the dragging flag in DragAndDrop
                        map.getListener().setDragging(true);
                        // store the old position of the animal
                        oldAnimalPosition = pressedTerrain;
                        return ramToBeDragged;

                    } else if (panel instanceof BlackSheepPanel) {

                        // set to true the dragging flag in DragAndDrop
                        map.getListener().setDragging(true);
                        // store the old position of the animal
                        oldAnimalPosition = pressedTerrain;
                        return panel;

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
            // set to true the dragging flag in DragAndDrop
            map.getListener().setDragging(true);
            // store the old position of the road
            oldPawnPosition = pressedRoad;
            return map.getPawnsLocation().get(pressedRoad);
        }

        return null;
    }

    /**
     * This method checks if the dragged panel has been dropped into a correct
     * area. If so it updates the related images and creates a move that is
     * "sent" to the interfaceGUI. The d&d creates only moves of type move sheep
     * and move player.
     * 
     * @param point
     */
    public void manageDrop(MouseEvent e, GameStatus status,
            PiecesOnTheMap draggedPanel) {
        // check if the drop was inside the map image
        if (isInsideTheMapImage(e.getPoint())) {
            if (status.equals(GameStatus.MOVE_PLAYER)) {
                manageDropPlayer(e.getPoint(), draggedPanel);
            } else {
                if (status.equals(GameStatus.MOVE_SHEEP)) {
                    manageDropSheep(e.getPoint(), draggedPanel);
                }
            }
        } else {
            animateBack(draggedPanel);
        }
    }

    /**
     * 1)Check if the drop position is correct 2)update the view (taking care of
     * the different type of sheep that may be involved) 3) call a method of the
     * interfaceGUI and send to it a new moveSheep move.
     * 
     * if the drop doesn't take place on an admitted point the dragged panel is
     * taken back to its old position
     * 
     * @param point
     */
    private void manageDropSheep(Point point, PiecesOnTheMap draggedPanel) {

        // for the drop to be in the right position the point has to be in the
        // terrain adjacent to the shepherd position

        Color droppedColor = paintedMap.findColor(point);

        // check if the color links to a terrain
        if (linker.getColorsAndTerrain().containsKey(droppedColor)) {

            Terrain dropTarget = linker.getColorsAndTerrain().get(droppedColor);

            // find the terrains adjacent to the player
            Terrain[] adjacentTerrains = interfaceGui.getGameController()
                    .getControlledPlayer().getPosition().getAdjacentTerrains();

            // if the drop position is right
            if (!dropTarget.equals(oldAnimalPosition)
                    && (dropTarget.equals(adjacentTerrains[0]) || dropTarget
                            .equals(adjacentTerrains[1]))) {

                // Update the view
                // 1)animate the dragged panel to the fixed panel
                // 2)create a move and send it back to the interfaceGui
                // 3)update the fixed panel (not for blackSheep)
                // 4)remove the dragged panel (not for blackSheep)

                map.animateAnimal(draggedPanel, dropTarget, oldAnimalPosition);

                // creates a move depending on the type of sheep moved
                BoardStatusExtended boardStatus = (BoardStatusExtended) interfaceGui
                        .getGameController().getBoardStatus();

                if (draggedPanel instanceof SheepPanel) {
                    interfaceGui.returnMoveFromGui(new MoveSheep(boardStatus
                            .getCurrentPlayer(), boardStatus.findASheep(
                            oldAnimalPosition, TypeOfSheep.FEMALESHEEP),
                            dropTarget));
                } else if (draggedPanel instanceof RamPanel) {
                    interfaceGui.returnMoveFromGui(new MoveSheep(boardStatus
                            .getCurrentPlayer(), boardStatus.findASheep(
                            oldAnimalPosition, TypeOfSheep.MALESHEEP),
                            dropTarget));
                } else if (draggedPanel instanceof LambPanel) {
                    interfaceGui.returnMoveFromGui(new MoveSheep(boardStatus
                            .getCurrentPlayer(), boardStatus.findASheep(
                            oldAnimalPosition, TypeOfSheep.NORMALSHEEP),
                            dropTarget));
                } else if (draggedPanel instanceof BlackSheepPanel) {
                    interfaceGui.returnMoveFromGui(new MoveSheep(boardStatus
                            .getCurrentPlayer(), boardStatus.getBlackSheep(),
                            dropTarget));
                }

                if (!(draggedPanel instanceof BlackSheepPanel)) {

                    // wait for the animation to be completed
                    try {
                        Thread.sleep(GuiConstants.ANIMATION_LENGTH);
                    } catch (InterruptedException e) {
                        LOG.log(Level.WARNING,
                                "Interrupted while executing an animation", e);
                    }

                    updateFixedAnimalPanel(dropTarget, draggedPanel);

                    map.remove(draggedPanel);
                    map.repaint();
                }

                // set the status back to NOT_YOUR_TURN
                map.getListener().setStatus(GameStatus.NOT_YOUR_TURN);
                map.getListener().setDraggedPanel(null);

            } else {
                animateBack(draggedPanel);
            }
        } else {
            animateBack(draggedPanel);
        }
    }

    /**
     * This method takes the dragged panel back to its old position and removes
     * it.
     * 
     * used both for animals and pawns
     * 
     * @param draggedPanel
     */
    private void animateBack(PiecesOnTheMap draggedPanel) {
        Point endPoint;

        if (draggedPanel instanceof PawnPanel) {
            endPoint = map
                    .getPointOfAPanelOnRoad(draggedPanel, oldPawnPosition);
        } else {
            endPoint = map.getPointOfAPanelOnTerrain(draggedPanel,
                    oldAnimalPosition);
        }

        Animator ani = new Animator(draggedPanel, endPoint);
        new Thread(ani).start();

        // wait for the animation to be completed
        try {
            Thread.sleep(GuiConstants.ANIMATION_LENGTH);
        } catch (InterruptedException e) {
            LOG.log(Level.WARNING, "Interrupted while executing an animation",
                    e);
        }

        // removes the panel only if the dragged panel was an animal
        if (!(draggedPanel instanceof PawnPanel)) {
            map.remove(draggedPanel);
            map.repaint();
        }
    }

    /**
     * This method update the image containing the number of ram/lamb/sheep in a
     * specific terrain.
     * 
     * It updates both the starting point and the end point of the move.
     * 
     * @param terrain
     * @param draggedPanel
     */
    private void updateFixedAnimalPanel(Terrain newTerrain,
            PiecesOnTheMap draggedPanel) {

        if (draggedPanel instanceof SheepPanel) {
            // starting terrain
            map.removeSheep(oldAnimalPosition);
            // end terrain
            map.addSheep(newTerrain);
        } else if (draggedPanel instanceof RamPanel) {
            // starting terrain
            map.removeRam(oldAnimalPosition);
            // end terrain
            map.addRam(newTerrain);
        } else if (draggedPanel instanceof LambPanel) {
            // starting terrain
            map.removeLamb(oldAnimalPosition);
            // end terrain
            map.addLamb(newTerrain);
        }
    }

    /**
     * 1)Check if the drop position is correct 2)update the view (also add the
     * gate) 3) call a method of the interfaceGUI and send to it a new
     * movePlayer move.
     * 
     * NB this method doesn't check if the player has enough money to perform
     * the move
     * 
     * @param point
     * @param draggedPanel
     */
    private void manageDropPlayer(Point point, PiecesOnTheMap draggedPanel) {

        Color dropColor = paintedMap.findColor(point);

        // check if the color links to a road
        if (linker.getColorsAndRoad().containsKey(dropColor)) {

            Road dropTarget = linker.getColorsAndRoad().get(dropColor);
            // check if the road is different from the old position of the
            // player
            if (!dropTarget.equals(oldPawnPosition)) {
                // animate the pawn to the exact road position
                map.animatePawn((PawnPanel) draggedPanel, dropTarget,
                        oldPawnPosition);

                addGate(oldPawnPosition);

                sendPlayerMove(draggedPanel, dropTarget);

                // set the status back to NOT_YOUR_TURN
                map.getListener().setStatus(GameStatus.NOT_YOUR_TURN);
                map.getListener().setDraggedPanel(null);
            } else {
                animateBack(draggedPanel);
            }
        } else {
            animateBack(draggedPanel);
        }

    }

    /** Comunicates the new player move to the interfaceGui */
    private void sendPlayerMove(PiecesOnTheMap draggedPanel, Road dropTarget) {

        MovePlayer move = new MovePlayer(interfaceGui.getGameController()
                .getControlledPlayer(), dropTarget);

        interfaceGui.returnMoveFromGui(move);
    }

    /** Calculates if the gate has to be final and call the map's method */
    private void addGate(Road dropTarget) {

        int numberOfStandardGates = interfaceGui.getGameController()
                .getBoardStatus().countStandardGates();

        if (numberOfStandardGates < 20) {
            // the standard gates are not over
            map.addGate(GuiConstants.GATE, dropTarget);
        } else {
            map.addGate(GuiConstants.FINAL_GATE, dropTarget);
        }

    }

    /**
     * This method checks if the click was performed on a free road and adds the
     * player's pawn in that position.
     * 
     * @param point
     */
    public void manageInitialPosition(Point point) {

        if (isInsideTheMapImage(point)) {
            Color clickedColor = paintedMap.findColor(point);

            // if the color is in a road
            if (linker.getColorsAndRoad().containsKey(clickedColor)) {
                Road selectedRoad = linker.getColorsAndRoad().get(clickedColor);

                // check if the road is free
                if (interfaceGui.getGameController().getBoardStatus()
                        .isFreeRoad(selectedRoad)) {
                    Pawns pawn = linker.getPawn(
                            interfaceGui.getGameController()
                                    .getControlledPlayer()).get(0);

                    map.addPawn(pawn, selectedRoad);

                    // comunicates the initial position to the interfaceGui
                    interfaceGui.setInitialPosition(selectedRoad);

                    // set the status back to NOT_YOUR_TURN
                    map.getListener().setStatus(GameStatus.NOT_YOUR_TURN);
                }
            }
        }
    }

    /**
     * This method checks if the click happened on a terrain, if so, it creates
     * a new mating move and send it back to the interfaceGui.
     * 
     * No rulechecking on the move.
     * 
     * @param point
     */
    public void manageMating(Point point) {

        if (isInsideTheMapImage(point)) {
            Color clickedColor = paintedMap.findColor(point);

            if (linker.getColorsAndTerrain().containsKey(clickedColor)) {

                Terrain clickedTerrain = linker.getColorsAndTerrain().get(
                        clickedColor);

                interfaceGui.returnMoveFromGui(new Mating(interfaceGui
                        .getGameController().getControlledPlayer(),
                        clickedTerrain));

                // set the status back to NOT_YOUR_TURN
                map.getListener().setStatus(GameStatus.NOT_YOUR_TURN);
            }
        }
    }

    /**
     * This methods checks if the click was performed on a panel displaying a
     * sheep/lamb/ram. Then it creates a new move and sent it back to the
     * interfaceGui.
     * 
     * No rulecheking on the move.
     * 
     * @param point
     */
    public void manageButchering(Point point) {

        if (isInsideTheMapImage(point)) {
            Color clickedColor = paintedMap.findColor(point);

            if (linker.getColorsAndTerrain().containsKey(clickedColor)) {
                Terrain clickedTerrain = linker.getColorsAndTerrain().get(
                        clickedColor);

                PiecesOnTheMap clickedPanel = null;

                for (PiecesOnTheMap panel : map.getComponentsInTerrains().get(
                        clickedTerrain)) {
                    // if the click happened inside this panel
                    if (panel.contains(point)) {
                        // set this panel as the clicked panel
                        clickedPanel = panel;
                    }
                }
                TypeOfSheep type = null;

                // get the type of sheep that the player decided to kill
                if (clickedPanel instanceof SheepPanel) {
                    type = TypeOfSheep.FEMALESHEEP;
                } else if (clickedPanel instanceof RamPanel) {
                    type = TypeOfSheep.MALESHEEP;
                } else if (clickedPanel instanceof LambPanel) {
                    type = TypeOfSheep.NORMALSHEEP;
                }

                // send the move back to the interfaceGui
                if (type != null) {
                    BoardStatusExtended bs = (BoardStatusExtended) interfaceGui
                            .getGameController().getBoardStatus();
                    interfaceGui.returnMoveFromGui(new Butchering(interfaceGui
                            .getGameController().getControlledPlayer(), bs
                            .findASheep(clickedTerrain, type)));

                    // set the status back to NOT_YOUR_TURN
                    map.getListener().setStatus(GameStatus.NOT_YOUR_TURN);
                }
            }
        }

    }
}
