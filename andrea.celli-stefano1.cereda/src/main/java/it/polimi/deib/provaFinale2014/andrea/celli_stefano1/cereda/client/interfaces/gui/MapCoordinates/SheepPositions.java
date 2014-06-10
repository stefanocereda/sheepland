package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.MapCoordinates;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import java.awt.Point;

/**
 * This enum defines the origin of the labels displaying sheeps on the map. They
 * are calculated in a 900x1292 image. They will need to be converted in new
 * values depending on the dimension of the screen.
 * 
 * Every terrain has a point that serves as the origin for the sheep panel.
 * 
 * @author Andrea
 * 
 */

public enum SheepPositions {

	SP1(Terrain.P1, new Point(69, 264)), SP2(Terrain.P2, new Point(184, 528)), SP3(
			Terrain.P3, new Point(293, 683)), SC1(Terrain.C1, new Point(543,
			148)), SC2(Terrain.C2, new Point(252, 240)), SC3(Terrain.C3,
			new Point(343, 475)), SM1(Terrain.M1, new Point(466, 387)), SM2(
			Terrain.M2, new Point(728, 197)), SM3(Terrain.M3, new Point(728,
			353)), SL1(Terrain.L1, new Point(594, 650)), SL2(Terrain.L2,
			new Point(581, 912)), SL3(Terrain.L3, new Point(434, 1013)), SW1(
			Terrain.W1, new Point(150, 786)), SW2(Terrain.W2, new Point(268,
			924)), SW3(Terrain.W3, new Point(426, 810)), SD1(Terrain.D1,
			new Point(597, 449)), SD2(Terrain.D2, new Point(728, 565)), SD3(
			Terrain.D3, new Point(694, 786));

	private Terrain terrain;
	private Point point;

	private SheepPositions(Terrain terrain, Point point) {
		this.terrain = terrain;
		this.point = point;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public Point getPoint() {
		return point;
	}

}
