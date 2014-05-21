package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import org.junit.Test;

/**
 * @author Andrea
 */
public class GameControllerClientTest {

	/**
	 * Test for executeMove method
	 */
	@Test
	public void executeMoveTest() {
		GameControllerClient gameController = new GameControllerClient();
		gameController.initializeGame(3);
		Player player1 = new Player(5, gameController.getBoardStatus()
				.getDeck().extractInitialCard(), gameController
				.getBoardStatus().getRoadMap().getHashMapOfRoads().get(1));
		gameController.getBoardStatus().addPlayerToBoardStatus(player1);

		// Check for MoveSheep
		Sheep sheep = new Sheep(Terrain.C1);
		gameController.getBoardStatus().addSheep(sheep);
		Move moveSheep = new MoveSheep(player1, sheep, Terrain.C2);
		gameController.executeMove(moveSheep);
		assertEquals(Terrain.C2, gameController.getBoardStatus().getSheeps()
				.get(0).getPosition());
		assertEquals(
				player1.getLastMoves().get(player1.getLastMoves().size() - 1),
				moveSheep);

		// Check for BuyCardMove
		Move buyCardMove = new BuyCardMove(player1, Card.DESERT1);
		gameController.executeMove(buyCardMove);
		// Check if the last move corresponds to buyCardMove
		assertEquals(
				player1.getLastMoves().get(player1.getLastMoves().size() - 1),
				buyCardMove);
		// Check if the player has now Desert1
		assertEquals(Card.DESERT1,
				player1.getCards().get(player1.getCards().size() - 1));
		// Check if the move it's no longer in the deck
		assertFalse(gameController.getBoardStatus().getDeck()
				.isInTheDeck(Card.DESERT1));
		// Check if the card cost has been subtracted from the player's money
		assertEquals(player1.getMoney(),
				5 - player1.getCards().get(player1.getCards().size() - 1)
						.getNumber());

		// Check for MovePlayer
		MovePlayer movePlayer = new MovePlayer(player1, gameController
				.getBoardStatus().getRoadMap().getHashMapOfRoads().get(38), 1);
		gameController.executeMove(movePlayer);
		// Check if a gate has been placed on road 38
		assertEquals(
				gameController
						.getBoardStatus()
						.getGates()
						.get(gameController.getBoardStatus().getGates().size() - 1)
						.getPosition(), gameController.getBoardStatus()
						.getRoadMap().getHashMapOfRoads().get(1));
		// Check if the move has been added to player's last move
		assertEquals(
				player1.getLastMoves().get(player1.getLastMoves().size() - 1),
				movePlayer);
		// Check if the price of the move has been subtracted from player's
		// money
		assertEquals(player1.getMoney(), 3);
		// Check if player's position corresponds to the new one
		assertEquals(player1.getPosition(),
				movePlayer.getNewPositionOfThePlayer());

		// Check for move blackSheep
		BlackSheep blackSheep = new BlackSheep(Terrain.L1);
		gameController.getBoardStatus().addBlackSheepToBoardStatus(blackSheep);
		MoveBlackSheep moveBlackSheep = new MoveBlackSheep(Terrain.C3,
				blackSheep);
		gameController.executeMove(moveBlackSheep);
		// Check if the new position is correct
		assertEquals(gameController.getBoardStatus().getBlackSheep()
				.getPosition(), Terrain.C3);
	}
}
