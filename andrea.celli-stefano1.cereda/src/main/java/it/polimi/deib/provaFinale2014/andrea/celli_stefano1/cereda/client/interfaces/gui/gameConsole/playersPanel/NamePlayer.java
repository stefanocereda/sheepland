package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.playersPanel;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * A label containing the name of the player.
 * 
 * @author Andrea
 * 
 */

public class NamePlayer extends JLabel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -579686708686984138L;

    // the constructor set the name of the player and the background color
    public NamePlayer(String name, Color playerColor) {
        super(name);
        setBackground(playerColor);
    }

    /**
     * Get the name of the player
     * 
     * @Return string contaning the name
     * 
     */
    public String getNamePlayer() {
        return this.getText();
    }

}
