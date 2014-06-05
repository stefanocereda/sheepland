package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.GameConsole;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.JFrame;

/**
 * This JFrame contains two panels. One contains the map and the other buttons
 * and other "game stuff".
 * 
 * @author Andrea
 * 
 */
public class MainFrame extends JFrame {

	private GroupLayout layout = new GroupLayout(this.getContentPane());

	/** The panel containing the map */
	private GameBoard map;

	/** The panel containing the game console */
	private GameConsole console;

	private Toolkit toolKit = Toolkit.getDefaultToolkit();

	private Dimension screenDimension = new Dimension();

	/**
	 * @param the
	 *            number of player that take part in the game
	 */
	public MainFrame(int numberOfPlayers) {

		this.getContentPane().setLayout(layout);
		setTitle("SHEEPLAND");

		// get the size of the screen
		screenDimension = toolKit.getScreenSize();

		// calculate the x and y size of the two displayed panels
		int mapWidth = (screenDimension.width * 2) / 3;
		int consoleWidth = screenDimension.width / 3;
		// the height is a little reduced to avoid problems with the effective
		// size of the screen (ex. on Macs there's the applications icons cut
		// the frame)
		int height = screenDimension.height - screenDimension.height / 10;

		// set the frame size
		setSize(mapWidth + consoleWidth, height);

		// set the layout adding the two components
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addComponent(map, mapWidth, mapWidth, mapWidth)
				.addComponent(console, consoleWidth, consoleWidth, consoleWidth));
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(map, height, height, height)
				.addComponent(console, height, height, height));

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// centre the window
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);

	}

}
