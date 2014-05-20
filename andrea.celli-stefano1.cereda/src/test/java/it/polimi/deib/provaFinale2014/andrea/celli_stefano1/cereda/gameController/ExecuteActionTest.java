package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController;

import static org.junit.Assert.assertEquals;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerClient.ExecuteAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;

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
}
