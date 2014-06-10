package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.MapCoordinates;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import java.awt.Point;

/**
 * This enum defines the origin of the labels displaying black sheep on the map.
 * They are calculated in a 900x1292 image. They will need to be converted in
 * new values depending on the dimension of the screen.
 * 
 * Every terrain has a point that serves as the origin for the black sheeps
 * panel.
 * 
 * @author Andrea
 * 
 */
public enum BlackSheepPositions {

	BP1(Terrain.P1, new Point(75, 315)), BP2(Terrain.P2, new Point(206, 638)), BP3(
			Terrain.P3, new Point(363, 690)), BC1(Terrain.C1, new Point(507,
			250)), BC2(Terrain.C2, new Point(284, 296)), BC3(Terrain.C3,
			new Point(299, 475)), BM1(Terrain.M1, new Point(525, 345)), BM2(
			Terrain.M2, new Point(669, 209)), BM3(Terrain.M3, new Point(782,
			321)), BD1(Terrain.D1, new Point(657, 485)), BD2(Terrain.D2,
			new Point(737, 528)), BD3(Terrain.D3, new Point(708, 738)), BL1(
			Terrain.L1, new Point(551, 683)), BL2(Terrain.L2, new Point(595,
			866)), BL3(Terrain.L3, new Point(468, 963)), BW1(Terrain.W1,
			new Point(210, 764)), BW2(Terrain.W2, new Point(343, 923)), BW3(
			Terrain.W3, new Point(464, 713)), BS(Terrain.SHEEPSBURG, new Point(
			509, 588));

	private Terrain terrain;
	private Point point;

	private BlackSheepPositions(Terrain terrain, Point point) {
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
