package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test for TypeOfPlayerMove
 * 
 * @author Andrea
 * 
 */
public class TypeOfPlayerMovesTest {

    @Test
    public void toStringTest() {

        assertEquals(TypeOfPlayerMoves.MOVEPLAYER.toString(), "move player");
        assertEquals(TypeOfPlayerMoves.MOVESHEEP.toString(), "move sheep");
        assertEquals(TypeOfPlayerMoves.BUYCARD.toString(), "buy card");
    }

}
