package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.cardsPanel;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This JPanel displays a buyable card of a certain terrain type.
 * 
 * @author Andrea
 * @TODO findImage
 */
public class SingleCardPanel extends JPanel {

	// the image to display
	Image image;
	// the Card displayed
	Card card;

	/**
	 * The constructor takes as input the card that has to be displayed
	 * 
	 * @param card
	 */
	public SingleCardPanel(Card card) {
		setBackground(GuiConstants.COLORGAMECONSOLE);
		// finds the image of the specified card
		image = findImage(card);
	}

	/**
	 * Paints the image on the JPanel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	/**
	 * This method loads the image coresponding to the specified card.
	 * 
	 * @param card
	 * @return the image
	 */
	private Image findImage(Card card) {
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(
				card.getImageName()));
		return imgIcon.getImage();
	}

	/**
	 * UpDates the panel showing the new buyable card.
	 * 
	 * @param newCard
	 */
	public void upDate(Card newCard) {

		// load the new image
		image = findImage(newCard);

		repaint();

	}

}
