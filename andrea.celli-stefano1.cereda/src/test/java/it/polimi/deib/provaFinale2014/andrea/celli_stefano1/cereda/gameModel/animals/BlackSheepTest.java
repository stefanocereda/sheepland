/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals;

import static org.junit.Assert.assertEquals;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import org.junit.Test;

/**
 * The blacksheep is actually an animal, so there's not much to test
 * 
 * @author Stefano
 * 
 */
public class BlackSheepTest {

    /**
     * Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep#BlackSheep(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain)}
     * .
     */
    @Test
    public void testBlackSheep() {
        Terrain t1 = Terrain.SHEEPSBURG;
        BlackSheep bs = new BlackSheep(t1);
        bs.setID();
        Terrain t2 = bs.getPosition();

        assertEquals(t1, t2);
    }

}
