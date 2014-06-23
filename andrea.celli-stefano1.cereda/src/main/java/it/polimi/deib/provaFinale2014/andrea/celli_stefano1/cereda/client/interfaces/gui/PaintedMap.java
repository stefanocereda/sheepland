package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * This class contains the buffered image with the "custom colors". It's used to
 * understand the location of a point (in terms of terrains/roads).
 * 
 * @author Andrea
 * 
 */
public class PaintedMap {

    /** the painted map */
    private BufferedImage paintedMap;

    /** the border that surrounds the displayed map */
    private double border;

    /** the dimension that the final painted map has to have */
    private Dimension paintedMapDimension = new Dimension();

    /** class logger */
    private static final Logger LOG = Logger.getLogger(PaintedMap.class
            .getName());

    /**
     * The constructor
     * 
     * @param panelMapDimension
     *            (the dimension of the panel that contains the displayed image)
     */
    public PaintedMap(Dimension panelMapDimension) {

        // calculate the dimension and the border
        paintedMapDimension.setSize(GuiConstants.MAP_WIDTH
                * panelMapDimension.height / GuiConstants.MAP_HEIGHT,
                panelMapDimension.height);

        border = (panelMapDimension.width - paintedMapDimension.width) / 2;

        paintedMap = loadAndResize();
    }

    /**
     * This method load the buffered image and then scale it to fit the
     * dimension of the actual map
     * 
     * @return bufferedImage
     */
    private BufferedImage loadAndResize() {

        BufferedImage bimg = null;
        BufferedImage ret = null;

        // load the initial buffered image
        try {
            bimg = ImageIO.read(getClass().getClassLoader().getResource(
                    GuiConstants.PAINTED_MAP));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unable to load the painted map file", e);
        }

        ret = new BufferedImage(GuiConstants.MAP_WIDTH,
                GuiConstants.MAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = ret.createGraphics();
        g.drawImage(bimg, 0, 0, null);
        g.dispose();

        // scale the bufferedImage
        Image scaledImage = ret.getScaledInstance(paintedMapDimension.width,
                paintedMapDimension.height, Image.SCALE_DEFAULT);

        // creates a new buffered image from the scaled image
        BufferedImage finalBufferedImage = new BufferedImage(
                paintedMapDimension.width, paintedMapDimension.height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = finalBufferedImage.getGraphics();
        g2.drawImage(scaledImage, 0, 0, null);
        g2.dispose();

        return finalBufferedImage;

    }

    /**
     * This method allows to find the color of a given point in the painted map
     */
    public Color findColor(Point point) {
        return new Color(paintedMap.getRGB((int) (point.x - border), point.y));
    }
}
