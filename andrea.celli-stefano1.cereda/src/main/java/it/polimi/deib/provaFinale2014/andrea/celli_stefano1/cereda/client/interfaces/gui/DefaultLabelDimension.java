package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import java.awt.Dimension;

/**
 * This enum contains the width and hight of JLabels rapresenting sheep ecc..
 * The measures are taken in a 900x1292 image. They will be use as the starting
 * point to calculate their real dimesion.
 * 
 * @author Andrea
 * 
 */
public enum DefaultLabelDimension {
	SHEEP(new Dimension(50, 36)), RAM(new Dimension(50, 36)), LAMB(
			new Dimension(45, 27)), BLACKSHEEP(new Dimension(35, 24)), WOLF(
			new Dimension(35, 22)), PAWN(new Dimension(46, 46));

	private Dimension defaultDimension;

	private DefaultLabelDimension(Dimension defaultDimension) {
		this.defaultDimension = defaultDimension;
	}

	public Dimension getDefaultDimension() {
		return defaultDimension;
	}

}
