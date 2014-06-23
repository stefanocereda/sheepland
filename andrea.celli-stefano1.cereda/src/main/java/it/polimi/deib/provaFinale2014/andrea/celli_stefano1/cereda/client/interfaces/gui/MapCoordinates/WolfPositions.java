package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.MapCoordinates;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import java.awt.Point;

/**
 * This enum defines the origin of the labels displaying the wolf on the map.
 * They are calculated in a 900x1292 image. They will need to be converted in
 * new values depending on the dimension of the screen.
 * 
 * Every terrain has a point that serves as the origin for wolf's panel.
 * 
 * @author Andrea
 * 
 */
public enum WolfPositions {

    WP1(Terrain.P1, new Point(153, 365)), WP2(Terrain.P2, new Point(154, 640)), WP3(
            Terrain.P3, new Point(344, 802)), WC1(Terrain.C1,
            new Point(554, 99)), WC2(Terrain.C2, new Point(349, 295)), WC3(
            Terrain.C3, new Point(336, 412)), WM1(Terrain.M1, new Point(481,
            359)), WM2(Terrain.M2, new Point(742, 252)), WM3(Terrain.M3,
            new Point(752, 428)), WL1(Terrain.L1, new Point(633, 623)), WL2(
            Terrain.L2, new Point(651, 969)), WL3(Terrain.L3, new Point(485,
            1062)), WD1(Terrain.D1, new Point(656, 452)), WD2(Terrain.D2,
            new Point(753, 495)), WD3(Terrain.D3, new Point(760, 813)), WW1(
            Terrain.W1, new Point(172, 909)), WW2(Terrain.W2, new Point(358,
            965)), WW3(Terrain.W3, new Point(472, 860)), WS(Terrain.SHEEPSBURG,
            new Point(397, 583));

    private Terrain terrain;
    private Point point;

    private WolfPositions(Terrain terrain, Point point) {
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
