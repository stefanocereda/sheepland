package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole;

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

	/** the flag is true when the player is in his turn */
	private boolean yourTurn = false;

}
