package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.GameConsole;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * This JFrame contains two panels. One contains the map and the other buttons
 * and other "game stuff".
 * 
 * @author Andrea
 * 
 */
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -863823923223326292L;

	/** The panel containing the map */
	private GameMap map;

	/** The panel containing the game console */
	private GameConsole console;

	private Toolkit toolKit = Toolkit.getDefaultToolkit();

	private Dimension screenDimension = new Dimension();

	/**
	 * @param the
	 *            number of player that take part in the game
	 */
	public MainFrame() {

		Container content = this.getContentPane();

		content.setLayout(null);
		setTitle("SHEEPLAND");

		map = new GameMap();
		console = new GameConsole();
		// set the reference to the map in the buttonspanel
		console.getButtonPanel().setReferenceToTheMap(map);

		// get the size of the screen
		screenDimension = toolKit.getScreenSize();

		// calculate the x and y size of the two displayed panels
		int mapWidth = screenDimension.width * 2 / 3;
		int consoleWidth = screenDimension.width / 3;

		// the height is a little reduced to avoid problems with the effective
		// size of the screen (ex. on Macs there's the applications icons cut
		// the frame)
		int height = screenDimension.height - screenDimension.height / 10;

		content.setSize(screenDimension.width, height);
		content.setPreferredSize(new Dimension(screenDimension.width, height));

		content.add(map);
		map.setLocation(0, 0);
		map.setSize(new Dimension(mapWidth, height));

		content.add(console);
		console.setLocation(mapWidth, 0);
		console.setSize(new Dimension(consoleWidth, height));

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		// centre the window
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);

	}

	public GameMap getMap() {
		return map;
	}

	public GameConsole getConsole() {
		return console;
	}

}
