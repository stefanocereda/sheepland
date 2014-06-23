package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.GameMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.GameStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This panel contains the buttons that allow the player to choose type of
 * moves.
 * 
 * The panel have a boolean flag that states whether the listener on the button
 * should call the actionPerformed method or not. The flag is set by the GUI
 * controller. In this way players' click that take place during the turn of
 * someone else are not considered.
 * 
 * @author Andrea
 * 
 */

public class ButtonsPanel extends JPanel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 213582368190596833L;

    /** The map */
    private GameMap map;

    /** the flag is true when the player is in his turn */
    private boolean activateListener = false;

    private GridLayout layout = new GridLayout(5, 1);

    /** The button listener */
    private ButtonListener listener = new ButtonListener();

    public ButtonsPanel() {

        setBackground(GuiConstants.COLORGAMECONSOLE);
        this.setLayout(layout);

        JButton movePlayerButton = new JButton("move player");
        JButton moveSheepButton = new JButton("move sheep");
        JButton buyCardButton = new JButton("buy card");
        JButton butcheryButton = new JButton("butchery");
        JButton matingButton = new JButton("mating");

        this.add(movePlayerButton);
        this.add(moveSheepButton);
        this.add(buyCardButton);
        this.add(butcheryButton);
        this.add(matingButton);

        movePlayerButton.addActionListener(listener);
        moveSheepButton.addActionListener(listener);
        buyCardButton.addActionListener(listener);
        butcheryButton.addActionListener(listener);
        matingButton.addActionListener(listener);

    }

    /**
     * This method is used to set a reference to the map. It's needed to change
     * the status of the drag&drop listener.
     * 
     * @param map
     */
    public void setReferenceToTheMap(GameMap map) {
        this.map = map;
    }

    /** Set to true the flag used to enable the listener on the buttons */
    public void setActive(boolean booleanValue) {

        this.activateListener = booleanValue;

    }

    /**
     * The listener of the buttons. It's active when the activateListener flag
     * is true. Its purpose is to let the user choose the type of move he wants
     * to perform.
     * 
     * When a button is clicked the listener change the drag&drop status flag or
     * activate a buy_card panel
     */
    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            // check if the flag is true
            if (activateListener) {

                activateListener = false;

                // find the pressed button
                JButton pressed = (JButton) e.getSource();
                String selectedTypeOfMove = pressed.getText();

                if ("move player".equals(selectedTypeOfMove)) {
                    map.getMessageManager().showMessage("Move your pawn");
                    map.getListener().setStatus(GameStatus.MOVE_PLAYER);

                } else if ("move sheep".equals(selectedTypeOfMove)) {
                    map.getMessageManager().showMessage("Move a sheep");
                    map.getListener().setStatus(GameStatus.MOVE_SHEEP);

                } else if ("buy card".equals(selectedTypeOfMove)) {
                    map.buyNewCard();

                } else if ("butchery".equals(selectedTypeOfMove)) {
                    map.getMessageManager().showMessage(
                            "Choose which sheep you want to kill");
                    map.getListener().setStatus(GameStatus.BUTCHERING);

                } else if ("mating".equals(selectedTypeOfMove)) {
                    map.getMessageManager().showMessage(
                            "Choose where to mate two sheeps");
                    map.getListener().setStatus(GameStatus.MATING);

                }
            }
        }
    }
}