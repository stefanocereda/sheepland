package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Butchering;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages the gui. If contains the methods called by the
 * "communication unit".
 * 
 * @author Andrea
 * 
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

	public GameControllerClient getGameController() {
		return gameController;
	}

	public void showInitialInformation() {
		frame.getMap().initMapComponents(this);
		paintAllStatus();
	}

	public void notifyNewStatus() {
		paintAllStatus();
	}

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

	public void notifyMove(Move move) {
		// TODO mostrare le mosse con questi metodi
		frame.getMap().animatePawn(draggedPanel, dropTarget);
		frame.getMap().animateAnimal(draggedPanel, dropTarget);
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
		//TODO
	}
}