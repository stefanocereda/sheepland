/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import org.junit.Test;

/**
 * @author stefano
 * 
 */
public class MoveSheepTest {

    /**
     * Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep#MoveSheep(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain)}
     * .
     */
    @Test
    public void testMoveSheep() {
        MoveSheep ms = new MoveSheep(null, null, null);
        ms.setID();
        assertNotNull(ms);
    }

    /**
     * Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep#getMovedSheep()}
     * .
     */
    @Test
    public void testGetMovedSheep() {
        Sheep s = new Sheep(null);
        s.setID();
        MoveSheep ms = new MoveSheep(null, s, null);
        ms.setID();
        assertEquals(s, ms.getMovedSheep());
    }

    /**
     * Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep#getNewPositionOfTheSheep()}
     * .
     */
    @Test
    public void testGetNewPositionOfTheSheep() {
        for (Terrain t : Terrain.values()) {
            MoveSheep ms = new MoveSheep(null, null, t);
            ms.setID();
            assertEquals(t, ms.getNewPositionOfTheSheep());
        }
    }

}
