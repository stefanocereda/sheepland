package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import static org.junit.Assert.assertEquals;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Wolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for the extended board status
 * 
 * @author Andrea
 * 
 */
public class BoardStatusExtendedTest {

	@Test
	public void wolfTest() {

		BoardStatusExtended bs = new BoardStatusExtended(3);
		Wolf wolf = new Wolf(Terrain.C1);
		wolf.setID();
		Wolf wolf2 = new Wolf(Terrain.C2);
		wolf2.setID();

		bs.addWolfToBoardStatus(wolf);
		bs.addWolfToBoardStatus(wolf2);

		assertEquals(bs.getWolf(), wolf);
	}

	@Test
	public void calculateNumberOfSheepForEachTerrainTest() {

		BoardStatusExtended bse = new BoardStatusExtended(4);

		// creates the "fake" hashMap
		Map<Terrain, Integer> map = new HashMap<Terrain, Integer>();
		// init all to 0
		for (Terrain terrain : Terrain.values()) {
			map.put(terrain, 0);
		}
		// sets some terrains to values different from 0
		map.put(Terrain.C1, 3);
		map.put(Terrain.W2, 2);
		map.put(Terrain.L3, 1);

		// tests for all the types of sheeps
		TypeOfSheep[] types = {TypeOfSheep.NORMALSHEEP, TypeOfSheep.MALESHEEP,
				TypeOfSheep.FEMALESHEEP};
		for (TypeOfSheep type : types) {

			// add shees in C1
			for (int i = 0; i < 3; i++) {
				bse.addSheep(new Sheep(0, type, Terrain.C1));
			}
			// adds sheeps in W2
			for (int i = 0; i < 2; i++) {
				bse.addSheep(new Sheep(0, type, Terrain.W2));
			}
			// adds sheeps in L3
			bse.addSheep(new Sheep(0, type, Terrain.L3));

			// check
			Map<Terrain, Integer> results = bse
					.calculateNumberOfSheepForEachTerrain(type);
			for (Terrain terrain : Terrain.values()) {
				assertEquals(results.get(terrain), map.get(terrain));
			}

		}

	}

	@Test
	public void findASheepTest() {
		BoardStatusExtended bse = new BoardStatusExtended(3);

		TypeOfSheep[] types = {TypeOfSheep.NORMALSHEEP, TypeOfSheep.MALESHEEP,
				TypeOfSheep.FEMALESHEEP};

		for (TypeOfSheep type : types) {
			Sheep sheep = new Sheep(0, type, Terrain.M1);
			sheep.setID();
			bse.addSheep(sheep);

			Sheep foundSheep = bse.findASheep(Terrain.M1, type);
			assertEquals(foundSheep.getTypeOfSheep(), type);

			assertEquals(bse.findASheep(Terrain.C1, type), null);
		}
	}

	@Test
	public void getSellableCardsTest() {
		BoardStatusExtended bse = new BoardStatusExtended(1);

		Player p = new Player();
		p.setID();
		bse.addPlayerToBoardStatus(p);

		assertEquals(new ArrayList<Card>(), bse.getSellableCards(p));

		p.addCard(Card.COUNTRYSIDE_I);

		assertEquals(new ArrayList<Card>(), bse.getSellableCards(p));

		p.addCard(Card.COUNTRYSIDE1);
		p.addCard(Card.MOUNTAIN0);

		Set<Card> set = new HashSet<Card>();
		set.add(Card.COUNTRYSIDE1);
		set.add(Card.MOUNTAIN0);

		Set<Card> result = new HashSet<Card>(bse.getSellableCards(p));

		assertEquals(set, result);
	}
}
