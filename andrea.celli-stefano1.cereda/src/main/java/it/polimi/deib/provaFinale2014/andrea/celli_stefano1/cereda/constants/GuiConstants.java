package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants;

import java.awt.Color;

/**
 * This class contains the constants related to the GUI
 * 
 * @author Andrea
 * 
 */

public class GuiConstants {

	/** Hide the default constructor */
	private GuiConstants() {
	}

	/** The color of the sea (used to fill spaces in the map) */
	public static final Color SEACOLOR = new Color(38, 162, 248);

	/** The color used in the background of the Game Console */
	public static final Color COLORGAMECONSOLE = new Color(234, 234, 234);

	/** The color used to mark the current player */
	public static final Color COLORCURRENTPLAYER = new Color(250, 144, 7);

	/**
	 * The thickness of the border used to mark the current player in the
	 * PlayersPanel
	 */
	public static final int BORDERTHICKNESS = 7;

	/**
	 * Duration of game messages diplayed over the map
	 */
	public static final int MESSAGETIME = 4000;

	/**
	 * The original dimension of the MAP image, used for conversion purposes.
	 */
	public static final int MAP_WIDTH = 900;
	public static final int MAP_HEIGHT = 1292;
}
