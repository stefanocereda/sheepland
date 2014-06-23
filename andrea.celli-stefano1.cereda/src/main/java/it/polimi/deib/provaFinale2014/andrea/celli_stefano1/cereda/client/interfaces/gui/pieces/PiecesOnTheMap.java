package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class rapresents a generic object that has to be displayed over the map
 * (the specific classes will inherit from this one). They will all be JPanels
 * and each one of them will display an image.
 * 
 * @author Andrea
 * 
 */
public class PiecesOnTheMap extends JPanel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6342495841308815193L;
    // the image contained in the JLabel
    Image img;

    /**
     * @param path
     *            of the image that has to be displayed in the JLabel
     * @param dimension
     */
    public PiecesOnTheMap(String path, Dimension dimension) {
        super();
        // create first an image icon
        ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(path));
        // get the displayable image from the image icon
        img = imgIcon.getImage();
        setSize(dimension);
        setOpaque(false);
    }

    /**
     * This method adds the image intp the panel fitting it with the actual
     * panel dimension
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        // paint the image so that it's entirely displayed in the panel
        g2d.drawImage(
                img,
                (this.getWidth() - img.getWidth(null) * this.getHeight()
                        / img.getHeight(null)) / 2, 0, img.getWidth(null)
                        * this.getHeight() / img.getHeight(null), getHeight(),
                null);
    }

    public void setImg(String path) {
        ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(path));
        img = imgIcon.getImage();
    }

    /**
     * This method checks whether a point is inside a JPanel displaying a piece
     * of the game.
     * 
     * @param point
     *            (the point to ckeck)
     * @return true (if the point is contained in the panel), false otherwise
     */
    @Override
    public boolean contains(Point point) {
        if (point.x > this.getLocation().x
                && point.x < this.getLocation().x + this.getWidth()
                && point.y > this.getLocation().y
                && point.y < this.getHeight() + this.getLocation().y) {
            return true;
        }

        return false;
    }

}
