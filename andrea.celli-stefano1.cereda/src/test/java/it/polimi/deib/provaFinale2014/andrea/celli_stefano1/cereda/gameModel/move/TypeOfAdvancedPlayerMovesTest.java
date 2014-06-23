package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test for TypeOfAdvancedPlay
 * 
 * @author Andrea
 * 
 */
public class TypeOfAdvancedPlayerMovesTest {

    @Test
    public void toStringTest() {

        for (TypeOfAdvancedPlayerMoves type : TypeOfAdvancedPlayerMoves
                .values()) {
            if (type.equals(TypeOfAdvancedPlayerMoves.MATING)) {
                assertEquals("Mating", type.toString());
            } else {
                assertEquals("Butchering", type.toString());
            }
        }
    }
}
