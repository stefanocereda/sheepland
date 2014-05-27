/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import static org.junit.Assert.*;

import java.util.Map;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.RuleChecker;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Gate;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.RoadMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class RuleCheckerTest {

	@Test
	public void testCreate() {
		RuleChecker rc1 = RuleChecker.create();
		RuleChecker rc2 = RuleChecker.create();

		assertNotNull(rc1);
		assertNotNull(rc2);

		assertTrue(rc1 == rc2); // it's a singleton pattern

	}

	@Test
	/**A move is invalid if done by another player*/
	public void testIsValidMoveIncorrectPlayer() {
		RuleChecker rc = RuleChecker.create();

		Player player1 = new Player();
		player1.setID();

		Player player2 = new Player();
		player2.setID();

		BoardStatus status = new BoardStatus(2);// two players
		status.addPlayerToBoardStatus(player1);
		status.addPlayerToBoardStatus(player2);
		status.setCurrentPlayer(player1);

		Move moveIncorrect = new MovePlayer(player2, null, 0);
		assertFalse(rc.isValidMove(moveIncorrect, player2.getLastMoves(),
				status));
	}

	@Test
	/**A move is invalid if not affordable*/
	public void testIsValidMoveNotAffordable() {
		RuleChecker rc = RuleChecker.create();

		Player player1 = new Player();
		player1.setID();

		BoardStatus status = new BoardStatus(1);// two players
		status.addPlayerToBoardStatus(player1);
		status.setCurrentPlayer(player1);

		// two not adjacent roads
		Road one = status.getRoadMap().getHashMapOfRoads().get(1);
		Road two = status.getRoadMap().getHashMapOfRoads().get(39);

		player1.move(one);

		Move incorrect = new MovePlayer(player1, two, 0);
		assertFalse(rc.isValidMove(incorrect, null, status));
	}

	@Test
	/**A shepherd can't move on an occupied road*/
	public void testIsValidMoveOccupiedRoad() {
		RuleChecker rc = RuleChecker.create();

		Player player = new Player();
		player.setID();
		BoardStatus status = new BoardStatus(1);
		status.addPlayerToBoardStatus(player);
		RoadMap roadMap = status.getRoadMap();
		roadMap.setID();
		Road road = roadMap.getHashMapOfRoads().get(1);
		road.setID();
		Gate gate = new Gate(false, road);
		gate.setID();
		status.addPlacedGateToBoardStatus(gate);

		Move incorrectMove = new MovePlayer(player, road, 0);
		incorrectMove.setID();
		assertFalse(rc
				.isValidMove(incorrectMove, player.getLastMoves(), status));
	}

	@Test
	/**A sheep can move between the two territories adjacent to the road of the shepherd*/
	public void testIsValidMoveNotAdjacentTerrain() {
		RuleChecker rc = RuleChecker.create();

		Player player = new Player();
		player.setID();
		BoardStatus status = new BoardStatus(1);
		status.addPlayerToBoardStatus(player);

		Road road = status.getRoadMap().getHashMapOfRoads().get(1);
		road.setID();
		Terrain[] adjacentTerrains = road.getAdjacentTerrains();

		player.move(road);
		Terrain coming = adjacentTerrains[0];

		Sheep sheep = new Sheep(coming);
		sheep.setID();

		for (Terrain going : Terrain.values()) {// search a not adjacent terrain
			Move moveSheep = new MoveSheep(player, sheep, going);
			moveSheep.setID();
			if (!going.equals(adjacentTerrains[0])
					&& !going.equals(adjacentTerrains[1])) {
				assertFalse(rc.isValidMove(moveSheep, player.getLastMoves(),
						status));
			}
		}

		// now try the same but for a sheep coming from a distant territory
		for (Terrain from : Terrain.values()) {
			if (!from.equals(adjacentTerrains[0])
					&& !from.equals(adjacentTerrains[1])) {
				Terrain to = adjacentTerrains[0];
				sheep.move(from);
				Move moveSheep = new MoveSheep(player, sheep, to);
				moveSheep.setID();
				assertFalse(rc.isValidMove(moveSheep, player.getLastMoves(),
						status));
			}
		}
	}

	@Test
	/**A card can't be bought if it's type is different from the adjacent terrains*/
	public void testIsValidMoveNotRightType() {
		RuleChecker rc = RuleChecker.create();

		Player player = new Player();
		player.setID();
		BoardStatus status = new BoardStatus(1);
		status.addPlayerToBoardStatus(player);

		Road road = status.getRoadMap().getHashMapOfRoads().get(1);
		road.setID();
		Terrain[] adjacentTerrains = road.getAdjacentTerrains();

		player.move(road);

		for (Card c : Card.values()) {// search a not valid card
			Move buyCard = new BuyCardMove(player, c);
			buyCard.setID();
			if (!c.getTerrainType()
					.equals(adjacentTerrains[0].getTerrainType())
					&& !c.getTerrainType().equals(
							adjacentTerrains[1].getTerrainType())) {
				assertFalse(rc.isValidMove(buyCard, player.getLastMoves(),
						status));
			}
		}
	}

	@Test
	/**A card can't be bought if it's not present in the deck or if there's a cheaper card in the deck*/
	public void testIsValidMoveDeckProblems() {
		RuleChecker rc = RuleChecker.create();

		Player player = new Player();
		player.setID();
		BoardStatus status = new BoardStatus(1);
		status.addPlayerToBoardStatus(player);

		Deck deck = status.getDeck();
		deck.remove(0);

		Move move = new BuyCardMove(player, deck.get(0));
		assertFalse(rc.isValidMove(move, null, status));

		move = new BuyCardMove(player, deck.get(3));
		assertFalse(rc.isValidMove(move, null, status));
	}

	@Test
	/**Test the three moves rules*/
	public void testIsValidMoveIncorrectTurn() {
		RuleChecker rc = RuleChecker.create();

		Player player = new Player();
		player.setID();
		BoardStatus status = new BoardStatus(1);
		status.addPlayerToBoardStatus(player);
		status.setCurrentPlayer(player);
		Map<Integer, Road> roads = status.getRoadMap().getHashMapOfRoads();

		// start with a valid first move
		player.move(roads.get(1));
		Sheep sheep = new Sheep(Terrain.P1);
		sheep.setID();
		status.addSheep(sheep);
		Move firstMove = new MoveSheep(player, sheep, Terrain.C2);

		assertTrue(rc.isValidMove(firstMove, player.getLastMoves(), status));
		player.addLastMove(firstMove);

		// an invalid second move
		Move secondMove = new MoveSheep(player, sheep, Terrain.C2);
		assertFalse(rc.isValidMove(secondMove, player.getLastMoves(), status));

		// now do a valid second move
		Deck deck = status.getDeck();
		player.setMoney(100);
		for (Card c : deck)
			if (c.getTerrainType().equals(
					player.getPosition().getAdjacentTerrains()[0]
							.getTerrainType())) {
				secondMove = new BuyCardMove(player, c);
				break;
			}
		assertTrue(rc.isValidMove(secondMove, player.getLastMoves(), status));
		player.addLastMove(secondMove);

		// an invalid third move
		Move thirdMove = null;
		for (Card c : deck)
			if (c.getTerrainType().equals(
					player.getPosition().getAdjacentTerrains()[0]
							.getTerrainType())) {
				thirdMove = new BuyCardMove(player, c);
				break;
			}
		assertFalse(rc.isValidMove(thirdMove, player.getLastMoves(), status));

		// and a valid third move
		player.move(roads.get(1));
		thirdMove = new MovePlayer(player, roads.get(5), 0);
		firstMove.setID();
		assertTrue(rc.isValidMove(thirdMove, player.getLastMoves(), status));
		player.addLastMove(thirdMove);
	}

	@Test
	/**Test right moves*/
	public void testIsValidMoveCorrectMoves() {
		RuleChecker rc = RuleChecker.create();

		Player player = new Player();
		player.setID();
		BoardStatus status = new BoardStatus(1);
		status.addPlayerToBoardStatus(player);
		status.setCurrentPlayer(player);
		player.setMoney(10);

		// First buy a card
		Road shepherdRoad = status.getRoadMap().getHashMapOfRoads().get(1);
		shepherdRoad.setID();
		player.move(shepherdRoad);
		Move move1 = null;
		for (Card c : Card.values()) {// search a right card
			if (c.getTerrainType().equals(
					shepherdRoad.getAdjacentTerrains()[0].getTerrainType())) {
				move1 = new BuyCardMove(player, c);
				move1.setID();
				break;
			}
		}
		assertTrue(rc.isValidMove(move1, player.getLastMoves(), status));
		player.addLastMove(move1);

		// Then move the shepherd
		Road newRoad = status.getRoadMap().getHashMapOfRoads().get(2);
		newRoad.setID();
		Move move2 = new MovePlayer(player, newRoad, 0);
		move2.setID();
		assertTrue(rc.isValidMove(move2, player.getLastMoves(), status));
		player.move(newRoad);
		player.addLastMove(move2);

		// Then move a sheep
		Terrain coming = player.getPosition().getAdjacentTerrains()[0];
		Terrain going = player.getPosition().getAdjacentTerrains()[1];

		Sheep sheep = new Sheep(coming);
		sheep.setID();
		status.addSheep(sheep);

		Move move3 = new MoveSheep(player, sheep, going);
		move3.setID();
		assertTrue(rc.isValidMove(move3, player.getLastMoves(), status));
	}
}
