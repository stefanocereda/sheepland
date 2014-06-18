/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

/**
 * This class is used to print information messages.
 * 
 * @author Andrea
 * 
 */
public class MessageManager {

	/** The map on which the messages are printed */
	private GameMap map;

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

		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBackground(Color.BLUE);
		textField.setEditable(false);
		textField.setFont(new Font("Verdana", Font.BOLD, 20));
		textField.setForeground(Color.WHITE);
		textField.setVisible(true);

		try {
			this.wait(GuiConstants.MESSAGE_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		map.remove(textField);
		map.repaint();
	}
}
