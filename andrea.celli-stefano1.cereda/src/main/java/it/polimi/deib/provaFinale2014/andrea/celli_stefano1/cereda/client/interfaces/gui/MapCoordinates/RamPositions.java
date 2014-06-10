package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.MapCoordinates;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import java.awt.Point;

/**
 * This enum defines the origin of the labels displaying rams on the map. They
 * are calculated in a 900x1292 image. They will need to be converted in new
 * values depending on the dimension of the screen.
 * 
 * Every terrain has a point that serves as the origin for the ram panel.
 * 
 * @author Andrea
 * 
 */

public enum RamPositions {

	RP1(Terrain.P1, new Point(135, 244)), RP2(Terrain.P2, new Point(197, 583)), RP3(
			Terrain.P3, new Point(326, 750)), RC1(Terrain.C1, new Point(542,
			208)), RC2(Terrain.C2, new Point(332, 240)), RC3(Terrain.C3,
			new Point(318, 523)), RM1(Terrain.M1, new Point(524, 384)), RM2(
			Terrain.M2, new Point(661, 253)), RM3(Terrain.M3, new Point(788,
			353)), RL1(Terrain.L1, new Point(597, 701)), RL2(Terrain.L2,
			new Point(583, 980)), RL3(Terrain.L3, new Point(393, 1069)), RD1(
			Terrain.D1, new Point(588, 497)), RD2(Terrain.D2, new Point(743,
			609)), RD3(Terrain.D3, new Point(702, 839)), RW1(Terrain.W1,
			new Point(157, 854)), RW2(Terrain.W2, new Point(291, 998)), RW3(
			Terrain.W3, new Point(460, 750)), RS(Terrain.SHEEPSBURG, new Point(
			451, 576));

	private Terrain terrain;
	private Point point;

	private RamPositions(Terrain terrain, Point point) {
		this.terrain = terrain;
		this.point = point;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public Point getPoint() {
		return point;
	}

	public int getX() {
		return point.x;
	}

	public int getY() {
		return point.y;
	}

}
