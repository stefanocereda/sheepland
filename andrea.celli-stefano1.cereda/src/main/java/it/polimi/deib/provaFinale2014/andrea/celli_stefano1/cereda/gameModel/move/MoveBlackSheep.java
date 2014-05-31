package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.ExecuteAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.RuleChecker;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

/**
 * MoveBlackSheep defines the action of moving the black sheep (as a "standard"
 * sheep)
 * 
 * @author Andrea Celli
 * 
 */
public class MoveBlackSheep extends Move {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6191726160657360490L;
	private Terrain newPosition;
	private BlackSheep blackSheep;

	/**
	 * @param newPosition
	 *            the new position of the black sheep
	 * @param blackSheep
	 *            the blackSheep of the current game
	 */
	public MoveBlackSheep(Terrain newPosition, BlackSheep blackSheep) {
		super();
		this.blackSheep = blackSheep;
		this.newPosition = newPosition;
	}

	/** @return the new position of the black sheep */
	public Terrain getNewPositionOfTheBlackSheep() {
		return newPosition;
	}

	/** @return the blackSheep of the specific game */
	public BlackSheep getBlackSheep() {
		return blackSheep;
	}

	@Override
	public String toString() {
		return "the black sheep moves to " + newPosition.toString();
	}

	@Override
	public boolean isValid(BoardStatus boardStatus) {
		return RuleChecker.isValidAutoMove(this, boardStatus);
	}

	@Override
	public void execute(BoardStatus boardStatus) {
		ExecuteAction.executeMoveBlackSheep(this, boardStatus);
	}

}
