package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * This panel displays the game map.
 * 
 * @author Andrea
 * 
 */

public class Map extends JPanel {

	// the image to be displayed
	Image img;

	/**
	 * This array list contains all the smaller JLabel that have to be displayed
	 * on the map (for ex. sheeps, pawns...)
	 */

	private List<PiecesOnTheMap> toBeDisplayed = new ArrayList<PiecesOnTheMap>();

	/**
	 * Initially the map is empty. All the "sub-panels" will be added after the
	 * first board status is received by the player.
	 */
	public Map() {
		super();
		// create first an image icon (the source is places in a source folder)
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(
				"/Game_Board.png"));
		// get the displayable image from the image icon
		img = imgIcon.getImage();
		setOpaque(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// set the border color of the panel
		setBackground(GuiConstants.SEACOLOR);
		Graphics2D g2d = (Graphics2D) g;
		// paint the image so that it's entirely displayed in the panel
		g2d.drawImage(img,
				(this.getWidth() - (img.getWidth(null) * this.getHeight())
						/ img.getHeight(null)) / 2, 0,
				(img.getWidth(null) * this.getHeight()) / img.getHeight(null),
				getHeight(), null);
	}

}
