package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;

import javax.swing.JLabel;

/**
 * A label containing the money of a certain player
 * 
 * @author Andrea
 * 
 */
public class MoneyPlayer extends JLabel {

	/**
	 * the constructor takes as input the number of initial money of the player
	 * 
	 * @param money
	 */
	public MoneyPlayer(Integer money) {
		super(money.toString() + " $");
	}

	/**
	 * Set the money owned by a player.
	 * 
	 * @param money
	 */
	public void setMoneyPlayer(Integer money) {
		this.setText(money.toString() + " $");
		repaint();
	}

	/**
	 * Mark as currentPLayer changeing the background color
	 */
	public void markAsCurrentPlayer() {
		setBackground(GuiConstants.COLORCURRENTPLAYER);
		repaint();
	}

	/**
	 * Mark as waiting for his turn changeing the background color to default
	 */
	public void markAsWaiting() {
		setBackground(GuiConstants.COLORGAMECONSOLE);
		repaint();
	}

}
