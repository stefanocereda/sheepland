package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.BlackSheepPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.PiecesOnTheMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Butchering;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveWolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.PlayerAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages the gui. If contains the methods called by the
 * "communication unit".
 * 
 * @author Andrea
 * @author Stefano
 */

public class InterfaceGui implements Interface {

	/** The game controller used in the game */
	private GameControllerClient gameController;

	/** The frame that contains all the elements of the GUI */
	private MainFrame frame;

	/** The initial position chosen by the player */
	private Road initialPosition = null;
	/** The move passed by the user */
	private Move returnedMove = null;

	/** A logger */
	private static final Logger LOG = Logger.getLogger(InterfaceGui.class
			.getName());

	/**
	 * The constructor create the needed GUI-related objects. Initially the
	 * GUI's map and "information panels" are empty. They're "populated" when
	 * the first board status is received.
	 */
	public InterfaceGui() {
		this.frame = new MainFrame();
	}

	/** {@inheritDoc} */
	public void setReferenceToGameController(
			GameControllerClient gameControllerClient) {
		this.gameController = gameControllerClient;
	}

	/** @return The game controller linked to this interface */
	public GameControllerClient getGameController() {
		return gameController;
	}

	/** {@inheritDoc} */
	public void showInitialInformation() {
		frame.getMap().initMapComponents(this);
		paintAllStatus();
	}

	/** {@inheritDoc} */
	public void notifyNewStatus() {
		paintAllStatus();
	}

	/** {@inheritDoc} */
	public Road chooseInitialPosition() {
		frame.getMap().getListener()
				.setStatus(GameStatus.CHOOSE_INITIAL_POSITION);

		while (initialPosition == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				LOG.log(Level.SEVERE,
						"Interrupted while retrieving the initial position", e);
			}
		}

		Road toReturn = initialPosition;
		initialPosition = null;
		return toReturn;
	}

	/** {@inheritDoc} */
	public void notifyMove(Move move) {
		if (move instanceof PlayerAction) {
			if (!((PlayerAction) move).getPlayer().equals(
					gameController.getControlledPlayer())) {
				notifyPlayerAction((PlayerAction) move);
			}

		} else if (move instanceof MoveWolf) {
			notifyMoveWolf((MoveWolf) move);

		} else if (move instanceof MoveBlackSheep) {
			notifyMoveBlackSheep((MoveBlackSheep) move);
		}

		frame.getMap().animatePawn(draggedPanel, dropTarget);
		frame.getMap().animateAnimal(draggedPanel, dropTarget);
	}

	/**
	 * Show to the user a player action
	 * 
	 * @param move
	 *            The move to show
	 */
	private void notifyPlayerAction(PlayerAction move) {
		if (move instanceof MovePlayer) {
			notifyMovePlayer((MovePlayer) move);
		} else if (move instanceof MoveSheep) {
			notifyMoveSheep((MoveSheep) move);
		} else if (move instanceof BuyCardMove) {
			notifyMoveBuyCard((BuyCardMove) move);
		}
	}

	/**
	 * Animate a move black sheep
	 * 
	 * @param move
	 *            the move to show
	 */
	private void notifyMoveBlackSheep(MoveBlackSheep move) {
		BlackSheepPanel panel = null;
		Terrain oldTerrain = gameController.getBoardStatus().getBlackSheep()
				.getPosition();

		// search the panel of the black sheep
		for (PiecesOnTheMap piece : frame.getMap().getComponentsInTerrains()
				.get(oldTerrain)) {
			if (piece instanceof BlackSheepPanel) {
				panel = (BlackSheepPanel) piece;
			}
		}

		frame.getMap().animateAnimal(panel,
				move.getNewPositionOfTheBlackSheep());
	}

	/**
	 * Move the sheep of the move to the new position
	 * 
	 * @param move
	 *            The move to show
	 */
	private void notifyMoveSheep(MoveSheep move) {
		// TODO
	}

	/**
	 * Move the shepherd of the move to the new position
	 * 
	 * @param move
	 *            The move to execute
	 */
	private void notifyMovePlayer(MovePlayer move) {
		// first of all we search the shepherd
		// TODO dove li trovo gli shepherd???
	}

	public Move getNewMove() {
		frame.getConsole().getButtonPanel().setActive(true);

		while (returnedMove == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Move toReturn = returnedMove;
		returnedMove = null;
		return toReturn;
	}

	public void notifyNotValidMove() {
		// TODO scrivere che la mossa non è valida e ridisegnare tutto lo stato
	}

	public void notifyCurrentPlayer(Player newCurrentPlayer) {
		String name = Linker.getLinkerInsance().getPawn(newCurrentPlayer)
				.get(0).getPlayerName();
		frame.getConsole().getPlayersPanel().markAsCurrentPlayer(name);
	}

	public void notifyWinners(List<Player> winners) {
		// TODO scrivere la lista dei vincitori

	}

	public void notifyDisconnection() {
		// TODO scrivere che si è disconnessi

	}

	public boolean chooseShepherd() {
		// TODO chiedere se usare secondo shepherd: mostrare finestra in cui
		// mette si/no
		return false;
	}

	public Road chooseSecondInitialPosition() {
		// TODO scrivere che stiamo chiedendo la posizione del secondo pastore
		return chooseInitialPosition();
	}

	public void notifyShepherd(boolean usingSecond) {
		// TODO mostrare che il current player sta usando il secondo, si può
		// anche non fare nulla

	}

	public List<MarketOffer> askMarketOffers() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MarketBuy> askMarketBuy(List<MarketOffer> offers) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method is used to return the move made by the player to the
	 * interfaceGui
	 */
	public void returnMoveFromGui(Move move) {
		this.returnedMove = move;
		notify();
	}

	/** This method has to be called when the user has chosen an initial road */
	public void setInitialPosition(Road chosenRoad) {
		initialPosition = chosenRoad;
		notify();
	}

	/** This method has to reset all the map and paint a brand new status */
	private void paintAllStatus() {
		// TODO
	}
}