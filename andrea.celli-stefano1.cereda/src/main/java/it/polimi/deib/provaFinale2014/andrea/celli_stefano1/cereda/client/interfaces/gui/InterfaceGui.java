package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.List;

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

	/**
	 * The constructor create the needed GUI-related objects. Initially the
	 * GUI's map and "information panels" are empty. They're "populated" when
	 * the first board status is received.
	 * 
	 * @TODO eventually change the linker init to implement the singleton
	 *       pattern
	 */

	public InterfaceGui() {

		this.frame = new MainFrame();

	}

	/**
	 * Set the reference to the gameController used in this game and creates the
	 * verifier (this has to be done after setting the gameController in this
	 * class)
	 */
	public void setReferenceToGameController(
			GameControllerClient gameControllerClient) {
		this.gameController = gameControllerClient;
	}

	public GameControllerClient getGameController() {
		return gameController;
	}

	public void showInitialInformation() {
		frame.getMap().initMapComponents(this);
	}

	public void notifyNewStatus() {
		// TODO Auto-generated method stub

	}

	public Road chooseInitialPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	public void notifyMove(Move move) {
		// TODO Auto-generated method stub

	}

	public Move getNewMove() {
		// TODO Auto-generated method stub
		return null;
	}

	public void notifyNotValidMove() {
		// TODO Auto-generated method stub

	}

	public void notifyCurrentPlayer(Player newCurrentPlayer) {
		// TODO Auto-generated method stub

	}

	public void notifyWinners(List<Player> winners) {
		// TODO Auto-generated method stub

	}

	public void notifyDisconnection() {
		// TODO Auto-generated method stub

	}

	public boolean chooseShepherd() {
		// TODO Auto-generated method stub
		return false;
	}

	public Road chooseSecondInitialPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	public void notifyShepherd(boolean usingSecond) {
		// TODO Auto-generated method stub

	}

	public List<MarketOffer> askMarketOffers() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MarketBuy> askMarketBuy(List<MarketOffer> offers) {
		// TODO Auto-generated method stub
		return null;
	}
}