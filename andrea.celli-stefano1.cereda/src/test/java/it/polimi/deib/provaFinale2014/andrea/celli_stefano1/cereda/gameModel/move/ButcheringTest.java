/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.*;

import java.util.Map;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import org.junit.Test;

/**
 * @author Nazzareno
 * 
 */
public class ButcheringTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Butchering#isValid(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus)}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Butchering#Butchering(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep)}
	 */
	@Test
	public void testIsValidAndExecute() {
		BoardStatus bs = new BoardStatusExtended(2);

		Player p = new Player();
		p.setMoney(GameConstants.INITIAL_MONEY);
		p.setID();
		bs.addPlayerToBoardStatus(p);
		bs.setCurrentPlayer(p);

		Sheep s = new Sheep(null);
		s.setID();
		bs.addSheep(s);

		BlackSheep black = new BlackSheep(null);
		black.setID();
		bs.addBlackSheepToBoardStatus(black);

		Map<Integer, Road> roads = bs.getRoadMap().getHashMapOfRoads();

		Butchering bsheep = new Butchering(p, s);
		Butchering bblack = new Butchering(p, black);

		// try to kill a distant sheep
		p.move(roads.get(1));
		s.move(Terrain.C3);
		black.move(Terrain.P3);

		assertFalse(bsheep.isValid(bs));
		assertFalse(bblack.isValid(bs));

		// try to kill an adjacent black sheep
		black.move(Terrain.P1);
		assertFalse(bsheep.isValid(bs));
		assertFalse(bblack.isValid(bs));

		// try to kill an adjacent sheep
		s.move(Terrain.P1);
		assertTrue(bsheep.isValid(bs));
		assertFalse(bblack.isValid(bs));

		// move away the black sheep
		black.move(Terrain.P3);
		assertTrue(bsheep.isValid(bs));
		assertFalse(bblack.isValid(bs));

		// now execute the move, add a player and keep killing the sheep until
		// the other player get a sufficient score
		Player p2 = new Player();
		p2.setID();
		p2.move(roads.get(5));
		bs.addPlayerToBoardStatus(p2);

		do {
			if (!bs.getSheeps().contains(s)) {
				bs.getSheeps().add(s);
			}

			bsheep.execute(bs);
		} while (bs.getSheeps().contains(s) || p2.getMoney() == 0);

		assertFalse(bs.getSheeps().contains(s));
		assertEquals(p2.getMoney(), GameConstants.COINS_GIVEN_IN_BUTCHERING);
		assertEquals(p.getMoney(), GameConstants.INITIAL_MONEY
				- GameConstants.COINS_GIVEN_IN_BUTCHERING);
	}

	/**
	 * Test method for .
	 */
	@Test
	public void testButchering() {
		Move m = new Butchering(null, null);
		assertNotNull(m);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Butchering#getKilledSheep()}
	 * .
	 */
	@Test
	public void testGetKilledSheep() {
		Sheep s = new Sheep(null);
		s.setID();
		Butchering m = new Butchering(null, s);
		assertEquals(s, m.getKilledSheep());
	}

}
