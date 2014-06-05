/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Gate;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class BoardStatusTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#BoardStatus(int)}
	 * .
	 */
	@Test
	public void testBoardStatus() {
		BoardStatus bs = new BoardStatus(3);

		assertNotNull(bs);
		assertEquals(3, bs.getPlayers().length);

		assertNotNull(bs.getGates());
		assertNotNull(bs.getSheeps());
		assertNotNull(bs.getRoadMap().getHashMapOfRoads());
		assertNotNull(bs.getDeck());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#getCurrentPlayer()}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#setCurrentPlayer(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player)}
	 */
	@Test
	public void testGetSetCurrentPlayer() {
		BoardStatus bs = new BoardStatus(1);
		Player p = new Player();
		p.setID();
		bs.addPlayerToBoardStatus(p);
		bs.setCurrentPlayer(p);
		assertEquals(p, bs.getCurrentPlayer());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#getGates()}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#addPlacedGateToBoardStatus(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Gate)}
	 */
	@Test
	public void testAddGetGates() {
		BoardStatus bs = new BoardStatus(2);

		List<Gate> gates = new ArrayList<Gate>();
		for (int i = 0; i < GameConstants.NUMBER_OF_NON_FINAL_GATES; i++) {
			Gate g = new Gate(false, null);
			g.setID();
			bs.addPlacedGateToBoardStatus(g);
			gates.add(g);
		}
		Gate g = new Gate(true, null);
		g.setID();
		bs.addPlacedGateToBoardStatus(g);
		gates.add(g);

		for (Gate gate : bs.getGates()) {
			assertTrue(gates.contains(gate));
		}

	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#getBlackSheep()}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#addBlackSheepToBoardStatus(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep)}
	 */
	@Test
	public void testAddGetBlackSheep() {
		BoardStatus bs = new BoardStatus(0);
		BlackSheep black = new BlackSheep(null);
		black.setID();
		bs.addBlackSheepToBoardStatus(black);

		assertEquals(black, bs.getBlackSheep());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#getPlayers()}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#addPlayerToBoardStatus(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player)}
	 */
	@Test
	public void testAddGetPlayers() {
		int np = 5;
		Player players[] = new Player[np];
		BoardStatus bs = new BoardStatus(np);

		for (int i = 0; i < np; i++) {
			Player p = new Player();
			p.setID();
			bs.addPlayerToBoardStatus(p);
			players[i] = p;
		}

		for (int i = 0; i < np; i++) {
			assertEquals(players[i], bs.getPlayers()[i]);
		}
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#getDeck()}
	 * .
	 */
	@Test
	public void testGetDeck() {
		BoardStatus bs = new BoardStatus(0);
		assertNotNull(bs.getDeck());

		for (Card c : Card.values()) {
			assertTrue(bs.getDeck().contains(c));
		}
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#isFreeRoad(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road)}
	 * .
	 */
	@Test
	public void testIsFreeRoad() {
		BoardStatus bs = new BoardStatus(1);

		Player p = new Player();
		p.setID();
		bs.addPlayerToBoardStatus(p);

		Map<Integer, Road> roads = bs.getRoadMap().getHashMapOfRoads();

		for (Road r : roads.values()) {
			assertTrue(bs.isFreeRoad(r));
		}

		p.move(roads.get(24));
		assertFalse(bs.isFreeRoad(roads.get(24)));

		Gate g = new Gate(false, roads.get(32));
		g.setID();
		bs.addPlacedGateToBoardStatus(g);
		assertFalse(bs.isFreeRoad(roads.get(32)));
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#addSheep(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep)}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#getSheeps()}
	 */
	@Test
	public void testAddGetSheep() {
		BoardStatus bs = new BoardStatus(0);

		Sheep s = new Sheep(null);
		s.setID();
		bs.addSheep(s);

		assertTrue(bs.getSheeps().contains(s));
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#countStandardGates()}
	 * .
	 */
	@Test
	public void testCountStandardGates() {
		BoardStatus bs = new BoardStatus(0);
		int nGates = 18;

		for (int i = 0; i < nGates; i++) {
			Gate g = new Gate(false, null);
			g.setID();
			bs.addPlacedGateToBoardStatus(g);
		}
		Gate g = new Gate(true, null);
		g.setID();
		bs.addPlacedGateToBoardStatus(g);

		assertEquals(nGates, bs.countStandardGates());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#setFirstPlayer(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player)}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#getFirstPlayer()}
	 */
	@Test
	public void testGetSetFirstPlayer() {
		BoardStatus bs = new BoardStatus(1);
		Player p = new Player();
		p.setID();
		bs.addPlayerToBoardStatus(p);
		bs.setFirstPlayer(p);

		assertEquals(p, bs.getFirstPlayer());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#getPositionOfAPlayer(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player)}
	 * .
	 */
	@Test
	public void testGetPositionOfAPlayer() {
		BoardStatus bs = new BoardStatus(1);
		Player p = new Player();
		p.setID();
		bs.addPlayerToBoardStatus(p);

		assertEquals(0, bs.getPositionOfAPlayer(p));
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#calculateNumberOfSheepForEachTerrain()}
	 * .
	 */
	@Test
	public void testCalculateNumberOfSheepForEachTerrain() {
		BoardStatus bs = new BoardStatus(0);
		Map<Terrain, Integer> nSheeps = bs
				.calculateNumberOfSheepForEachTerrain();

		for (Terrain t : Terrain.values()) {
			assertEquals(nSheeps.get(t), new Integer(0));
		}

		Sheep dolly = new Sheep(Terrain.M3);
		bs.addSheep(dolly);
		nSheeps = bs.calculateNumberOfSheepForEachTerrain();
		assertEquals(new Integer(1), nSheeps.get(Terrain.M3));
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#getPlayerNumber(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player)}
	 * .
	 */
	@Test
	public void testGetPlayerNumber() {
		BoardStatus bs = new BoardStatus(3);
		Player p = new Player();
		p.setID();
		bs.addPlayerToBoardStatus(p);

		assertEquals(bs.getPlayerNumber(p), 1);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus#isClosedByGates(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain)}
	 * .
	 */
	@Test
	public void testIsClosedByGates() {
		BoardStatus bs = new BoardStatus(0);
		Terrain t = Terrain.D1;
		Map<Integer, Road> roads = bs.getRoadMap().getHashMapOfRoads();

		assertFalse(bs.isClosedByGates(t));

		Gate g = new Gate(false, roads.get(10));
		g.setID();
		bs.addPlacedGateToBoardStatus(g);

		g = new Gate(false, roads.get(12));
		g.setID();
		bs.addPlacedGateToBoardStatus(g);

		g = new Gate(false, roads.get(14));
		g.setID();
		bs.addPlacedGateToBoardStatus(g);

		g = new Gate(false, roads.get(24));
		g.setID();
		bs.addPlacedGateToBoardStatus(g);

		g = new Gate(false, roads.get(23));
		g.setID();
		bs.addPlacedGateToBoardStatus(g);

		g = new Gate(false, roads.get(15));
		g.setID();
		bs.addPlacedGateToBoardStatus(g);

		assertTrue(bs.isClosedByGates(t));
	}
}
