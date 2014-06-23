/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class TerrainTest {

    /**
     * Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain#getTerrainType()}
     * .
     */
    @Test
    public void testGetTerrainType() {
        Terrain t = Terrain.C1;
        assertEquals(t.getTerrainType(), TerrainType.COUNTRYSIDE);
    }

    /**
     * Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain#toString()}
     * .
     */
    @Test
    public void testToString() {
        Terrain t = Terrain.C1;
        assertEquals("c1", t.toString());
    }

    /**
     * Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain#getAdjacentTerrains(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.RoadMap)}
     * .
     */
    @Test
    public void testGetAdjacentTerrains() {
        Terrain t = Terrain.P3;
        RoadMap roadMap = RoadMap.getRoadMap();
        List<Terrain> adj = t.getAdjacentTerrains(roadMap);

        assertTrue(adj.contains(Terrain.C3));
        assertTrue(adj.contains(Terrain.P2));
        assertTrue(adj.contains(Terrain.W1));
        assertTrue(adj.contains(Terrain.W2));
        assertTrue(adj.contains(Terrain.W3));
        assertTrue(adj.contains(Terrain.SHEEPSBURG));
    }

    /**
     * Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain#getLinkWith(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.RoadMap)}
     * .
     */
    @Test
    public void testGetLinkWith() {
        Terrain t1 = Terrain.D2;
        Terrain t2 = Terrain.D1;
        RoadMap roadMap = RoadMap.getRoadMap();
        Road r = roadMap.getHashMapOfRoads().get(14);

        assertEquals(t1.getLinkWith(t2, roadMap), r);
    }
}
