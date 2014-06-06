package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Wolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveWolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Dice;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;

/**
 * This is the controller for a game with advanced rules
 * 
 * @author stefano
 * 
 */
public class GameControllerExtended extends GameController {

	public GameControllerExtended(List<ClientHandler> playerClients) {
		super(playerClients);
	}

	/**
	 * Initialize the ruleChecker, the moveCostCalculator, the moveExecutor and
	 * the board. Send this initial status to all the players. This method is
	 * overridden in order to create an extended boardStatus
	 */
	@Override
	protected void initializeAll() {
		boardStatus = new BoardStatusExtended(clients.size());

		// initialize the board
		initBoard();
	}

	/**
	 * Initialize the board following the rules on the second page
	 * (Preparazione). We also create a wolf
	 */
	protected void initBoard() {
		addPlayersToGame();
		initSheeps();
		initBlackSheep();
		initWolf();
		initCards();
		deleteRemainingInitialCards();
		chooseFirstPlayer();
	}

	/** Create a wolf, set his id, put it in Sheepsburg and add it to the board */
	private void initWolf() {
		Wolf wolf = new Wolf(Terrain.SHEEPSBURG);
		wolf.setID();
		((BoardStatusExtended) boardStatus).addWolfToBoardStatus(wolf);
	}

	/**
	 * This method checks if the current player has done three moves, in that
	 * case we goes on with another player, otherwise ask a new move. It is
	 * overridden in order to go to moveTheWolf instead of goOn
	 */
	@Override
	public String checkIfPlayerFinishedTurn() {
		if (boardStatus.getCurrentPlayer().getLastMoves().size() == 3) {
			boardStatus.getCurrentPlayer().deleteLastMoves();
			return "moveTheWolf";
		}
		return "retrieveMoveFromCurrentPlayer";
	}

	/**
	 * This method rolls the dice and consequently creates a moveWolf move. If
	 * it is possible it validates, executes and sends to client the move.
	 */
	public String moveTheWolf() {
		Dice dice = Dice.create();
		int roadNum = dice.roll(GameConstants.NUMBER_OF_DICE_SIDES);
		Wolf wolf = ((BoardStatusExtended) boardStatus).getWolf();

		// now search the road adjacent to the wolf that has the wanted number
		Road linkingRoad = null;
		Terrain wolfPosition = wolf.getPosition();
		for (Road r : boardStatus.getRoadMap().findRoadsAdjacentToATerrain(
				wolfPosition)) {
			if (r.getBoxValue() == roadNum) {
				linkingRoad = r;
				break;
			}
		}

		// if linkingRoad is null go on (it happens near the sea)
		if (linkingRoad == null) {
			return "goOn";
		}

		// if we have found a road search the new terrain
		Terrain newPosition = null;
		for (Terrain t : linkingRoad.getAdjacentTerrains()) {
			if (!t.equals(wolfPosition)) {
				newPosition = t;
				break;
			}
		}

		// if newPosition is null (should be impossible) log an error and go on
		if (newPosition == null) {
			logger.log(Level.SEVERE,
					"There are errors in the method moveTheWolf");
			return "goOn";
		}

		// now randomly select a sheep in the new terrain
		List<Sheep> availableVictims = new ArrayList<Sheep>();
		for (Sheep s : boardStatus.getSheeps()) {
			if (s.getPosition().equals(newPosition)) {
				availableVictims.add(s);
			}
		}
		int victimNumber = dice.roll(availableVictims.size());
		Sheep sacrificialLamb = availableVictims.get(victimNumber);

		// create the move and try to execute
		MoveWolf mw = new MoveWolf(wolf, newPosition, sacrificialLamb);
		if (mw.isValid(boardStatus)) {
			executeMove(mw);
		}

		return "goOn";
	}

}
