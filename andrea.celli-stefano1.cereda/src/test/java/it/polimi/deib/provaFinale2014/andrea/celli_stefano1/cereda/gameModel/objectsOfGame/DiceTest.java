package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test for the dice in the game model
 * 
 * @author macosx
 * 
 */
public class DiceTest {

    @Test
    public void rollTest() {
        Dice dice = Dice.create();

        int result = dice.roll(10);

        assertTrue(result <= 10);
        assertTrue(result > 0);
    }

}
