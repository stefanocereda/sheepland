package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerClient.ExecuteAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.RoadMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import org.junit.Test;

/**
 * This test class tests ExecuteAction's method
 * 
 * @author Andrea
 * 
 */
public class ExecuteActionTest {

	@Test
	public void executeMoveSheepTest() {
		ExecuteAction executer = new ExecuteAction();
		Player player = new Player();
		Sheep sheep = new Sheep(0, TypeOfSheep.NORMALSHEEP, Terrain.C1);
		MoveSheep move = new MoveSheep(player, sheep, Terrain.C2);
		executer.executeMoveSheep(move);
		assertEquals(move,
				player.getLastMoves().get(player.getLastMoves().size() - 1));
		assertEquals(move.getNewPositionOfTheSheep(), sheep.getPosition());
	}

	@Test
	public void executeMoveBlackSheepTest() {
		ExecuteAction executer = new ExecuteAction();
		BlackSheep blackSheep = new BlackSheep(Terrain.SHEEPSBURG);
		MoveBlackSheep move = new MoveBlackSheep(Terrain.C1, blackSheep);
		executer.executeMoveBlackSheep(move);
		assertEquals(move.getNewPositionOfTheBlackSheep(),
				blackSheep.getPosition());
	}

	@Test
	public void executeMovePlayerTest() {
		ExecuteAction executer = new ExecuteAction();
		BoardStatus boardStatus = new BoardStatus(4);
		RoadMap roadMap = RoadMap.getRoadMap();
		int initialMoneyOfthePlayer = 5;
		Player player = new Player(initialMoneyOfthePlayer, null, roadMap
				.getHashMapOfRoads().get(2));
		MovePlayer move = new MovePlayer(player, roadMap.getHashMapOfRoads()
				.get(1), 0);
		executer.executeMovePlayer(move, boardStatus);
		// It checks the position of the player after the move
		assertEquals(move.getNewPositionOfThePlayer(), player.getPosition());
		// It checks the position of the gate
		assertEquals(
				boardStatus.getGates().get(boardStatus.getGates().size() - 1)
						.getPosition(), roadMap.getHashMapOfRoads().get(2));
		// It checks the last move of the player
		assertEquals(player.getLastMoves()
				.get(player.getLastMoves().size() - 1), move);
		// It checks the money after the move
		assertEquals(initialMoneyOfthePlayer - player.getMoney(),
				move.getCost());

		// Now make some more moves to start placing final gates and increase
		// the test coverage
		player.setMoney(1000);
		for (Road r : boardStatus.getRoadMap().getHashMapOfRoads().values()) {
			MovePlayer m = new MovePlayer(player, r, 0);
			executer.executeMovePlayer(m, boardStatus);
			assertEquals(player.getPosition(), r);
		}
	}

	@Test
	public void executeBuyCardMoveTest() {
		ExecuteAction executer = new ExecuteAction();
		BoardStatus boardStatus = new BoardStatus(4);
		int initialMoneyOfThePlayer = 5;
		Player player = new Player(initialMoneyOfThePlayer, null, null);
		BuyCardMove move = new BuyCardMove(player, Card.COUNTRYSIDE2);
		executer.executeBuyCardMove(move, boardStatus);
		// Checks if the card has been removed from the deck
		assertFalse(boardStatus.getDeck().contains(Card.COUNTRYSIDE2));
		// Checks if the card has been added to player's card
		assertEquals(player.getCards().get(player.getCards().size() - 1),
				move.getNewCard());
		// Checks if the price of the card has been subtracted from the player's
		// money
		assertEquals(player.getMoney(), initialMoneyOfThePlayer
				- move.getNewCard().getNumber());
	}
}
