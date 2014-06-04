package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;

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

	/**
	 * Mark the player as current player changeing the back ground color
	 */
	public void markAsCurrentPlayer() {
		this.setBackground(GuiConstants.COLORCURRENTPLAYER);
		repaint();
	}

	/**
	 * Mark the player as wainting for his turn setting to "default" its
	 * background color
	 */
	public void markAsWaiting() {
		this.setBackground(GuiConstants.COLORGAMECONSOLE);
		repaint();
	}

}
