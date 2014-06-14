package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces;

import java.awt.Graphics;
import java.awt.Image;

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

	// the image contained in the JLabel
	Image img;

	/**
	 * @param path
	 *            of the image that has to be displayed in the JLabel
	 */
	public PiecesOnTheMap(String path) {
		super();
		// create first an image icon
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(path));
		// get the displayable image from the image icon
		img = imgIcon.getImage();
		setOpaque(false);

	}

	/**
	 * This method is empty.It will be overridden in all the subclasses beacuse
	 * the image has to be displayed considering the dimension of the label (it
	 * differs for different types of labels)
	 */
	public void paintComponent(Graphics g) {
	}
}
