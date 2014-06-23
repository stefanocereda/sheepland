package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants;

import java.awt.Color;

/**
 * This class contains the constants related to the GUI
 * 
 * @author Andrea
 * 
 */

public class GuiConstants {
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

    /**
     * The path of the painted map
     */
    public static final String PAINTED_MAP = "Painted_Map.png";
    /**
     * The path of the black sheep image
     */
    public static final String BLACK_SHEEP = "/blackSheep.png";
    /**
     * The path of a gate image
     */
    public static final String GATE = "/recinto.png";
    /**
     * The path of a final gate image
     */
    public static final String FINAL_GATE = "/recinto_finale.png";
    /**
     * The path to the wolf image
     */
    public static final String WOLF = "/wolf.png";
    /**
     * The path to the sheep image without any number
     */
    public static final String EMPTY_SHEEP = "/sheepNoNumber.png";
    /**
     * The path to the lamb image without any number
     */
    public static final String EMPTY_LAMB = "/lambNoNumber.png";
    /**
     * The path to the ram image without any number
     */
    public static final String EMPTY_RAM = "/ramNoNumber.png";

    /** The number of frames per second to draw during the animations */
    public static final long ANIMATION_FPS = 20;

    /** The duration of the animations in milliseconds */
    public static final long ANIMATION_LENGTH = 1000;

    /** The duration of a message in milliseconds */
    public static final long MESSAGE_TIME = 1500;

    /**
     * 
     * Coeff to adjust panels dimension
     * 
     */
    public static final double SHEEP_ADJUSTING_X = 1.22;
    public static final double SHEEP_ADJUSTING_Y = 1.2;
    public static final double LAMB_ADJUSTING_X = 2;
    public static final double LAMB_ADJUSTING_Y = 2;
    public static final double RAM_ADJUSTING_X = 1.5;
    public static final double RAM_ADJUSTING_Y = 1.5;
    public static final double BLACK_SHEEP_X = 1.5;
    public static final double BLACK_SHEEP_Y = 1.5;
    public static final double PAWN_ADJUSTING_X = 1.2;
    public static final double WOLF_ADJUSTING_X = 3.5;
    public static final double WOLF_ADJUSTING_Y = 3.5;

    /** Hide the default constructor */
    private GuiConstants() {
    }
}
