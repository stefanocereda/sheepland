/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.PlayerAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class PlayerTest {

    /**
     * Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player#getMoney()}
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player#setMoney(int)}
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player#subtractMoney(int)}
     * 
     */
    @Test
    public void testMoney() {
        Player p = new Player();
        p.setID();
        p.setMoney(10);
        assertEquals(p.getMoney(), 10);

        p.subtractMoney(5);
        assertEquals(p.getMoney(), 5);
    }

    /**
     * Test method for }
     * 
     * 
     * /** Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player#move(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road)}
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player#getPosition()}
     */
    @Test
    public void testPosition() {
        Player p = new Player();
        p.setID();
        Road r = new Road(0, null, null);
        r.setID();
        p.move(r);

        assertEquals(r, p.getPosition());
    }

    /**
     * Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player#addCard(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card)}
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player#getCards()}
     */
    @Test
    public void testCard() {
        Player p = new Player();
        p.setID();

        for (Card c : Card.values()) {
            p.addCard(c);
            assertTrue(p.getCards().contains(c));
        }

    }

    /**
     * Test method for
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player#addLastMove(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move)}
     * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player#getLastMoves()}
     */
    @Test
    public void testLastMove() {
        Player p = new Player();
        p.setID();
        PlayerAction m1 = new MovePlayer(p, null);
        m1.setID();
        PlayerAction m2 = new MovePlayer(p, null);
        m2.setID();

        p.addLastMove(m1);
        assertTrue(p.getLastMoves().contains(m1));
        assertFalse(p.getLastMoves().contains(m2));
    }
}
