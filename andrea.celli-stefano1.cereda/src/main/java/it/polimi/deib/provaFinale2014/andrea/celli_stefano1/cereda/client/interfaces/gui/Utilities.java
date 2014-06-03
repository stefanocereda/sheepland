package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * This class contains static methods that perform utility tasks widely used in
 * the GUI.
 * 
 * @author Andrea
 * 
 */
public class Utilities {

	/**
	 * This method creates buffered images.
	 * 
	 * @param the
	 *            String specifying the path (name of the image)
	 * @return the buffered imgage
	 */
	private BufferedImage getBufferedImage(String path) {
		BufferedImage bimg = null;
		BufferedImage ret = null;

		try {
			bimg = ImageIO.read(getClass().getClassLoader().getResource(path));
		} catch (Exception e) {
			e.printStackTrace();
		}

		ret = new BufferedImage(bimg.getWidth(), bimg.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = ret.createGraphics();
		g.drawImage(bimg, 0, 0, null);
		g.dispose();
		return ret;
	}

}
