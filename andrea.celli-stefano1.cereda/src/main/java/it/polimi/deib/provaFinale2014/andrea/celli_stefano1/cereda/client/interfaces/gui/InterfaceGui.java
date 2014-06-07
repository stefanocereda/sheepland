package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Linker;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.GameConsole;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
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
	private GameController gameController;

	/** The frame that contains all the elements of the GUI */
	private MainFrame frame;

	/**
	 * The map displayed in the gui. Given its frequent use it's better not to
	 * access it every time through the Frame. (Even though it could be done)
	 */
	private Map map;

	/**
	 * The game console displayed in the gui, same reason for having an
	 * attribute ad before
	 */
	private GameConsole console;

	/**
	 * The object that contains all the links between colors and terrains,
	 * colors and roads etc..
	 */
	private Linker linker;

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

		this.console = frame.getConsole();
		this.map = frame.getMap();

		this.linker = new Linker();

	}

	public void setReferenceToGameController(
			GameControllerClient gameControllerClient) {
		// TODO Auto-generated method stub

	}

	public void showInitialInformation() {
		// TODO Auto-generated method stub

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
}