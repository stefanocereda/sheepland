/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * This class represents the action of killing a sheep. A player can try to kill
 * a sheep on am adjacent terrain. The player rolls the dice, if the number is
 * equal to the number of the road the move can be executed. Every player that
 * is on a adjacent road rolls the dice, every player that get a number > 5
 * receives 2 coins from the player, if the player can't pay the move is not
 * executed. The move only represents the player and the killed sheep, because
 * like the action of mating this move is executed only by the server and the
 * obtained Board status is sent to all the clients.
 * 
 * @author Stefano
 * 
 */
public class Butchering extends AdvancedPlayerAction {

	private static final long serialVersionUID = 7611124975542158430L;
	/** The sheep that the player wants to kill */
	private Sheep killedSheep;

	/**
	 * @param player
	 *            The player performing the move
	 * @param sheppToKill
	 *            The sheep you want to kill
	 */
	public Butchering(Player player, Sheep sheppToKill) {
		super(player);
		killedSheep = sheppToKill;
	}

	/** @return The sheep that has to be killed */
	public Sheep getKilledSheep() {
		return killedSheep;
	}

	@Override
	public boolean isValid(BoardStatus boardStatus) {
		return RuleChecker.isValidButchering(this,
				(BoardStatusExtended) boardStatus);
	}

	@Override
	public void execute(BoardStatus boardStatus) {
		ExecuteAction
				.executeButchering(this, (BoardStatusExtended) boardStatus);
	}

}
