package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.MapCoordinates;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import java.awt.Point;

/**
 * This enum defines the origin of the labels displaying lambs on the map. They
 * are calculated in a 900x1292 image. They will need to be converted in new
 * values depending on the dimension of the screen.
 * 
 * Every terrain has a point that serves as the origin for the lambs panel.
 * 
 * @author Andrea
 * 
 */
public enum LambPositions {

    LP1(Terrain.P1, new Point(139, 305)), LP2(Terrain.P2, new Point(190, 487)), LP3(
            Terrain.P3, new Point(326, 646)), LC1(Terrain.C1, new Point(628,
            134)), LC2(Terrain.C2, new Point(406, 244)), LC3(Terrain.C3,
            new Point(325, 443)), LM1(Terrain.M1, new Point(477, 438)), LM2(
            Terrain.M2, new Point(638, 311)), LM3(Terrain.M3, new Point(753,
            395)), LD1(Terrain.D1, new Point(588, 540)), LD2(Terrain.D2,
            new Point(756, 655)), LD3(Terrain.D3, new Point(748, 761)), LL1(
            Terrain.L1, new Point(596, 758)), LL2(Terrain.L2, new Point(586,
            1051)), LL3(Terrain.L3, new Point(353, 1119)), LW1(Terrain.W1,
            new Point(218, 823)), LW2(Terrain.W2, new Point(218, 1008)), LW3(
            Terrain.W3, new Point(489, 802)), LS(Terrain.SHEEPSBURG, new Point(
            457, 622));

    private Terrain terrain;
    private Point point;

    private LambPositions(Terrain terrain, Point point) {
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
