package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole;

import javax.swing.JLabel;

/**
 * A label containing the name of the player.
 * 
 * @author Andrea
 * 
 */

public class NamePlayer extends JLabel {

	// the constructor set the name of the player
	public NamePlayer(String name) {
		super(name);
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
