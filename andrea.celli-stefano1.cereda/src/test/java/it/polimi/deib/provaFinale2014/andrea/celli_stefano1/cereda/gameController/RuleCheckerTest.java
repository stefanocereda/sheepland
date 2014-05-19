/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.RuleChecker;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;

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

		assertEquals(rc1, rc2); // it's a singleton pattern

	}

	@Test
	public void testIsValidMove() {
		RuleChecker rc = RuleChecker.create();

		// Start with a move done by a player different from the current one
		Player player1 = new Player();
		Player player2 = new Player();
		BoardStatus status = new BoardStatus(2);// two players
		status.addPlayerToBoardStatus(player1);
		status.addPlayerToBoardStatus(player2);
		status.setCurrentPlayer(player1);

		MovePlayer move = new MovePlayer(player2, null, 0);
		assertFalse(rc.isValidMove(move, null, status));

		// TODO other kinds of incorrect moves
	}
}
