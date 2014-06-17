package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.BlackSheepPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.LambPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.PawnPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.PiecesOnTheMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.RamPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.SheepPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.WolfPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveWolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.PlayerAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Gate;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayerDouble;

import java.awt.Point;
import java.util.List;
import java.util.Map;
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

	/**
	 * {@inheritDoc}. This gui version also paints the initial version of the
	 * map
	 */
	public void showInitialInformation() {
		frame.getMap().initMapComponents(this);
		rePaintAllStatus();
	}

	/** {@inheritDoc} */
	public void notifyNewStatus() {
		rePaintAllStatus();
	}

	/**
	 * {@inheritDoc}. In the gui we enable the buttons used to choose the
	 * position, when the user choose a position the event is caught by a mouse
	 * handler that translate the event into a road and calls a method of this
	 * class (setInitialPosition) that saves the position and notifies this
	 * method
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	private void notifyMoveBuyCard(BuyCardMove move) {
		// TODO Auto-generated method stub qui cosa si fa?
	}

	/**
	 * Animate the movement of the wolf
	 * 
	 * @param move
	 *            The move to show
	 */
	private void notifyMoveWolf(MoveWolf move) {
		WolfPanel panel = null;
		Terrain oldTerrain = ((BoardStatusExtended) gameController
				.getBoardStatus()).getWolf().getPosition();

		// search the panel of the wolf
		for (PiecesOnTheMap piece : frame.getMap().getComponentsInTerrains()
				.get(oldTerrain)) {
			if (piece instanceof WolfPanel) {
				panel = (WolfPanel) piece;
			}
		}

		frame.getMap().animateAnimal(panel, move.getNewPosition());
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
		Sheep movingSheep = gameController.getBoardStatus().getEquivalentSheep(
				move.getMovedSheep());
		TypeOfSheep type = movingSheep.getTypeOfSheep();
		Terrain oldPosition = movingSheep.getPosition();

		// add a panel of a moving sheep
		PiecesOnTheMap panelToMove = createSheepPanel(type);
		Point panelPosition = calculateSheepPanelOrigin(type, oldPosition);
		panelToMove.setLocation(panelPosition);
		frame.getMap().add(panelToMove);

		// reduce the number of sheep
		reduceNumberOfSheep(type, oldPosition);

		// repaint and starts the animation
		frame.getMap().repaint();
		frame.getMap().animateAnimal(panelToMove,
				move.getNewPositionOfTheSheep());

		// wait for the animation to stop, delete the moving panel and increase
		// the number of sheep
		try {
			Thread.sleep(GuiConstants.ANIMATION_LENGTH);
			frame.getMap().remove(panelToMove);
			increaseNumberOfSheep(type, move.getNewPositionOfTheSheep());
			frame.getMap().repaint();
		} catch (InterruptedException e) {
			LOG.log(Level.SEVERE, "Interrupted while moving a sheep", e);
		}
	}

	/**
	 * This method is similar to decreaseNumberOfSheep except that it increase
	 * the number
	 */
	private void increaseNumberOfSheep(TypeOfSheep type, Terrain terrain) {
		switch (type) {
		case MALESHEEP:
			frame.getMap().addRam(terrain);
			break;
		case FEMALESHEEP:
			frame.getMap().addSheep(terrain);
			break;
		default:
			frame.getMap().addLamb(terrain);
		}
	}

	/**
	 * This method tells to the GameMap to reduce the number of sheep
	 * 
	 * @param type
	 *            The type of sheep to decrease
	 * @param terrain
	 *            The terrain where perform the decrease
	 */
	private void reduceNumberOfSheep(TypeOfSheep type, Terrain terrain) {
		switch (type) {
		case MALESHEEP:
			frame.getMap().removeRam(terrain);
			break;
		case FEMALESHEEP:
			frame.getMap().removeSheep(terrain);
			break;
		default:
			frame.getMap().removeLamb(terrain);
		}
	}

	/**
	 * This method calculates the point where is located the panel for the given
	 * type of sheep in the given terrain
	 * 
	 * @param type
	 *            The type of sheep to check for
	 * @param terrain
	 *            The interested terrain
	 * @return A point representing the position of the panel representing type
	 *         in terrain
	 */
	private Point calculateSheepPanelOrigin(TypeOfSheep type, Terrain terrain) {
		Linker linker = Linker.getLinkerInsance();
		Map<Terrain, Point> map = null;

		switch (type) {
		case MALESHEEP:
			map = linker.getRamOrigins();
			break;
		case FEMALESHEEP:
			map = linker.getSheepOrigins();
			break;
		default:
			map = linker.getLambOrigins();
		}

		return map.get(terrain);
	}

	/**
	 * This method returns a panel representing a sheep with the same type of
	 * the given one
	 * 
	 * @param type
	 *            A type of sheep
	 * @return A panel representing the given type of sheep
	 */
	private PiecesOnTheMap createSheepPanel(TypeOfSheep type) {
		switch (type) {
		case MALESHEEP:
			return new RamPanel(ImagePathCreator.findRamPathNoNumber(), frame
					.getMap().getDimensionCalculator().getRamDimension());
		case FEMALESHEEP:
			return new SheepPanel(ImagePathCreator.findSheepPathNoNumber(),
					frame.getMap().getDimensionCalculator().getSheepDimension());
		default:
			return new LambPanel(ImagePathCreator.findLambPathNoNumber(), frame
					.getMap().getDimensionCalculator().getLambDimension());
		}
	}

	/**
	 * Move the shepherd of the move to the new position
	 * 
	 * @param move
	 *            The move to execute
	 */
	private void notifyMovePlayer(MovePlayer move) {
		if (move.getPlayer() instanceof PlayerDouble) {
			notifyMovePlayerDouble(move);
		} else {
			Road oldPosition = gameController.getBoardStatus()
					.getEquivalentPlayer(move.getPlayer()).getPosition();
			PawnPanel pawn = frame.getMap().getPawnsLocation().get(oldPosition);
			frame.getMap().animatePawn(pawn, move.getNewPositionOfThePlayer());
		}
	}

	/**
	 * This method is used to notify the movement of a player controlling two
	 * shepherds
	 */
	private void notifyMovePlayerDouble(MovePlayer move) {
		PlayerDouble player = (PlayerDouble) gameController.getBoardStatus()
				.getEquivalentPlayer(move.getPlayer());

		Road oldPosition = null;
		if (player.getShepherd()) {
			oldPosition = player.getSecondposition();
		} else {
			oldPosition = player.getFirstPosition();
		}

		PawnPanel pawn = frame.getMap().getPawnsLocation().get(oldPosition);
		frame.getMap().animatePawn(pawn, move.getNewPositionOfThePlayer());
	}

	/**
	 * {@inheritDoc}. The mechanism used to retrieve a move from the user is
	 * similar to chooseInitialPosition()
	 */
	public Move getNewMove() {
		frame.getConsole().getButtonPanel().setActive(true);

		while (returnedMove == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				LOG.log(Level.SEVERE,
						"Interrupted while retrieving a new move", e);
			}
		}

		Move toReturn = returnedMove;
		returnedMove = null;
		return toReturn;
	}

	/**
	 * {@inheritDoc}. The gui version also repaint all the map in order to
	 * delete the invalid move
	 */
	public void notifyNotValidMove() {
		frame.getMap()
				.getMessageManager()
				.showMessage(
						"The move goes against Sheepland's rule! Be more careful");

		rePaintAllStatus();
	}

	/** {@inheritDoc} */
	public void notifyCurrentPlayer(Player newCurrentPlayer) {
		String name = Linker.getLinkerInsance().getPawn(newCurrentPlayer)
				.get(0).getPlayerName();
		frame.getConsole().getPlayersPanel().markAsCurrentPlayer(name);
	}

	/** {@inheritDoc} */
	public void notifyWinners(List<Player> winners) {
		MessageManager mm = frame.getMap().getMessageManager();

		String msg = "Game over. ";

		if (winners.size() == 1) {
			msg += "There's only one winner:";
		} else {
			msg += "The winners are:";
		}

		for (Player p : winners) {
			msg += " Player "
					+ gameController.getBoardStatus().getPlayerNumber(p);
		}

		if (winners.size() != 1
				&& winners.contains(gameController.getControlledPlayer())) {
			msg += "IT'S TIME TO BAA, make some noiseee";
		}

		mm.showMessage(msg);
	}

	/** {@inheritDoc} */
	public void notifyDisconnection() {
		frame.getMap()
				.getMessageManager()
				.showMessage(
						"We're disconnected from the server, but our shepherd dog is working hard to solve this");
	}

	public boolean chooseShepherd() {
		// TODO chiedere se usare secondo shepherd: mostrare finestra in cui
		// mette si/no
		return false;
	}

	public Road chooseSecondInitialPosition() {
		// TODO scrivere che stiamo chiedendo la posizione del secondo pastore.
		// controllare se per come viene scelta la posizione è lecito fare così
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
	private void rePaintAllStatus() {
		frame.getMap().removeAll();

		paintPlayers();
		paintSheep();
		paintCards();
		paintGates();
		paintBlackSheep();

		if (gameController.getBoardStatus() instanceof BoardStatusExtended) {
			paintWolf();
		}
	}

	private void paintCards() {
		// TODO Auto-generated method stub
	}

	/** Add the wolf to the game map */
	private void paintWolf() {
		Terrain position = ((BoardStatusExtended) gameController
				.getBoardStatus()).getWolf().getPosition();
		frame.getMap().addWolf(position);
	}

	/** Add to the game map the black sheep */
	private void paintBlackSheep() {
		Terrain position = gameController.getBoardStatus().getBlackSheep()
				.getPosition();
		frame.getMap().addBlackSheep(position);
	}

	/** This method adds to the game map all the gates of the board status */
	private void paintGates() {
		for (Gate g : gameController.getBoardStatus().getGates()) {
			String path = null;

			if (g.isLast()) {
				path = null;// TODO
			} else {
				path = null;// TODO
			}

			frame.getMap().addGate(path, g.getPosition());
		}
	}

	/**
	 * This method adds to the game map all the sheep contained in the board
	 * status
	 */
	private void paintSheep() {
		GameMap map = frame.getMap();
		for (Sheep s : gameController.getBoardStatus().getSheeps()) {
			Terrain t = s.getPosition();

			switch (s.getTypeOfSheep()) {
			case MALESHEEP:
				map.addRam(t);
				break;
			case FEMALESHEEP:
				map.addSheep(t);
				break;
			default:
				map.addLamb(t);
			}
		}
	}

	/** This method adds all the pawns to the map */
	private void paintPlayers() {
		GameMap map = frame.getMap();

		for (Player p : gameController.getBoardStatus().getPlayers()) {
			if (p instanceof PlayerDouble) {
				// TODO
			} else {
				// TODO
			}
		}
	}

	public MainFrame getFrame() {
		return frame;
	}
}