package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * This class is used to print information messages.
 * 
 * @author Andrea
 * 
 */
public class MessageManager {

    /** The map on which the messages are printed */
    private GameMap map;

    /** Class logger */
    private static final Logger LOG = Logger.getLogger(MessageManager.class
            .getName());

    /**
     * Constructor
     * 
     * @param map
     */
    public MessageManager(GameMap map) {
        this.map = map;
    }

    /**
     * Create a temporary JTextField with the given message that is shown to the
     * player.
     * 
     * @param message
     *            The message to show
     */
    public synchronized void showMessage(String message) {
        // creates the new text field that has to be displayed
        JTextField textField = new JTextField(message);

        map.add(textField, 0);
        textField.setSize(map.getWidth(), map.getHeight() / 4);
        textField.setLocation(0, map.getHeight() / 3);

        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setBackground(Color.BLUE);
        textField.setEditable(false);
        textField.setFont(new Font("Verdana", Font.BOLD, 20));
        textField.setForeground(Color.WHITE);
        textField.setVisible(true);

        try {
            this.wait(GuiConstants.MESSAGE_TIME);
        } catch (InterruptedException e) {
            LOG.log(Level.WARNING, "Interrupted while showing a message", e);
        }

        map.remove(textField);
        map.repaint();
    }
}
