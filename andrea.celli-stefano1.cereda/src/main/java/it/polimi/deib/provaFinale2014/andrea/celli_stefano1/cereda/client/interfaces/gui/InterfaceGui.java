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
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveWolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.PlayerAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Gate;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayerDouble;

import java.awt.Point;
import java.util.ArrayList;
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
		frame.getMap().getMessageManager()
				.showMessage("Choose your initial position");
		frame.getMap().getListener()
				.setStatus(GameStatus.CHOOSE_INITIAL_POSITION);

		while (initialPosition == null) {
			try {
				synchronized (this) {
					wait();
				}
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
		// TODO
		// messaggio che lo dice
		// togliere dalle carte quella comprata
		// soldi giocatore
	}

	/**
	 * Animate the movement of the wolf and repaint the sheeps
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

		frame.getMap().animateAnimal(panel, move.getNewPosition(), oldTerrain);

		// remove the killed sheep
		Sheep killed = move.getKilledSheep();
		if (killed != null) {
			Terrain crimeScene = killed.getPosition();
			switch (killed.getTypeOfSheep()) {
			case MALESHEEP:
				frame.getMap().removeRam(crimeScene);
				break;
			case FEMALESHEEP:
				frame.getMap().removeSheep(crimeScene);
				break;
			case NORMALSHEEP:
				frame.getMap().removeLamb(crimeScene);
				break;
			default:
				break;
			}
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
				move.getNewPositionOfTheBlackSheep(), oldTerrain);
	}

	/**
	 * Move the sheep of the move to the new position
	 * 
	 * @param move
	 *            The move to show
	 */
	private void notifyMoveSheep(MoveSheep move) {
		// if the moved sheep is the black sheep create a fake move black sheep
		// and call the correspondent method
		if (move.getMovedSheep().getTypeOfSheep()
				.equals(TypeOfSheep.BLACKSHEEP)) {
			MoveBlackSheep fake = new MoveBlackSheep(
					move.getNewPositionOfTheSheep(),
					(BlackSheep) move.getMovedSheep());
			notifyMoveBlackSheep(fake);
		} else {
			Sheep movingSheep = gameController.getBoardStatus()
					.getEquivalentSheep(move.getMovedSheep());
			TypeOfSheep type = movingSheep.getTypeOfSheep();
			Terrain oldPosition = movingSheep.getPosition();

			// add a panel of a moving sheep
			PiecesOnTheMap panelToMove = createSheepPanel(type);
			Point panelPosition = calculateSheepPanelOrigin(type, oldPosition);
			frame.getMap().add(panelToMove);
			panelToMove.setLocation(panelPosition);
			panelToMove.setVisible(true);

			// reduce the number of sheep
			reduceNumberOfSheep(type, oldPosition);

			// repaint and starts the animation
			frame.getMap().repaint();
			frame.getMap().animateAnimal(panelToMove,
					move.getNewPositionOfTheSheep(), oldPosition);

			// wait for the animation to stop, delete the moving panel and
			// increase
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
		case NORMALSHEEP:
			frame.getMap().addLamb(terrain);
			break;
		default:
			break;
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
		case NORMALSHEEP:
			frame.getMap().removeLamb(terrain);
			break;
		default:
			break;
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
		case NORMALSHEEP:
			map = linker.getLambOrigins();
			break;
		case BLACKSHEEP:
			map = linker.getBlackSheepOrigins();
			break;
		default:
			break;
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
		DimensionCalculator dimCalc = frame.getMap().getDimensionCalculator();
		PiecesOnTheMap toReturn = null;

		switch (type) {
		case MALESHEEP:
			toReturn = new RamPanel(ImagePathCreator.findRamPathNoNumber(),
					dimCalc.getRamDimension());
			break;
		case FEMALESHEEP:
			toReturn = new SheepPanel(ImagePathCreator.findSheepPathNoNumber(),
					dimCalc.getSheepDimension());
			break;
		case NORMALSHEEP:
			toReturn = new LambPanel(ImagePathCreator.findLambPathNoNumber(),
					dimCalc.getLambDimension());
			break;
		case BLACKSHEEP:
			toReturn = new BlackSheepPanel(GuiConstants.BLACK_SHEEP,
					dimCalc.getBlackSheepDimension());
			break;
		default:
		}

		return toReturn;
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
			Player player = gameController.getBoardStatus()
					.getEquivalentPlayer(move.getPlayer());
			Road oldPosition = player.getPosition();

			// if the old position is still null we are handling the first
			// positioning, therefore we create a new pawn panel
			if (oldPosition == null) {
				Pawns pawn = Pawns.values()[gameController.getBoardStatus()
						.getPositionOfAPlayer(player)];
				frame.getMap().addPawn(pawn, move.getNewPositionOfThePlayer());
			} else {
				PawnPanel pawn = frame.getMap().getPawnsLocation()
						.get(oldPosition);
				frame.getMap().animatePawn(pawn,
						move.getNewPositionOfThePlayer(), oldPosition);

				placeGate(oldPosition);
			}
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

		if (oldPosition == null) {
			Pawns pawn = Pawns.values()[gameController.getBoardStatus()
					.getPositionOfAPlayer(player)];
			frame.getMap().addPawn(pawn, move.getNewPositionOfThePlayer());
		} else {

			PawnPanel pawn = frame.getMap().getPawnsLocation().get(oldPosition);
			frame.getMap().animatePawn(pawn, move.getNewPositionOfThePlayer(),
					oldPosition);

			placeGate(oldPosition);
		}
	}

	/**
	 * Place a gates on the given road, checking if the gate has to be final or
	 * not
	 */
	private void placeGate(Road road) {
		String path = null;
		if (gameController.getBoardStatus().countStandardGates() >= GameConstants.NUMBER_OF_NON_FINAL_GATES) {
			path = GuiConstants.FINAL_GATE;
		} else {
			path = GuiConstants.GATE;
		}

		frame.getMap().addGate(path, road);
	}

	/**
	 * {@inheritDoc}. The mechanism used to retrieve a move from the user is
	 * similar to chooseInitialPosition()
	 */
	public Move getNewMove() {
		frame.getConsole().getButtonPanel().setActive(true);

		while (returnedMove == null) {
			try {
				synchronized (this) {
					wait();
				}
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

	/**
	 * {@inheritDoc}. The gui version creates a panel with a question a waits
	 * for the answer
	 */
	public boolean chooseShepherd() {
		// TODO chiedere se usare secondo shepherd: mostrare finestra in cui
		// mette si/no
		return false;
	}

	/**
	 * {@inheritDoc}. The gui version tells the user that he is choosing the
	 * position for the second shepherd and then acts as it is the first
	 * position
	 */
	public Road chooseSecondInitialPosition() {
		frame.getMap()
				.getMessageManager()
				.showMessage(
						"This is a game with two players and four shepherd. Choose the position of your second shepherd");
		return chooseInitialPosition();
	}

	/** {@inheritDoc}. The gui version does nothing, it doesn't have sense */
	public void notifyShepherd(boolean usingSecond) {
	}

	public List<MarketOffer> askMarketOffers() {
		// TODO Auto-generated method stub
		return new ArrayList<MarketOffer>();
	}

	public List<MarketBuy> askMarketBuy(List<MarketOffer> offers) {
		// TODO Auto-generated method stub
		return new ArrayList<MarketBuy>();
	}

	/**
	 * This method is used to return the move made by the player to the
	 * interfaceGui
	 */
	public void returnMoveFromGui(Move move) {
		this.returnedMove = move;
		synchronized (this) {
			notify();
		}
	}

	/** This method has to be called when the user has chosen an initial road */
	public void setInitialPosition(Road chosenRoad) {
		initialPosition = chosenRoad;
		synchronized (this) {
			notify();
		}
	}

	/** This method has to reset all the map and paint a brand new status */
	private void rePaintAllStatus() {
		frame.getMap().cleanMap();

		paintPlayers();
		paintSheep();
		paintCards();
		paintGates();

		if (gameController.getBoardStatus() instanceof BoardStatusExtended) {
			paintWolf();
		}
	}

	/** This method prints on the game console the cards the remain in the deck */
	private void paintCards() {
		for (Card c : gameController.getBoardStatus().getDeck()
				.getBuyableCards()) {
			frame.getConsole().getCardsPanel().addCard(c);
		}
	}

	/** Add the wolf to the game map */
	private void paintWolf() {
		Terrain position = ((BoardStatusExtended) gameController
				.getBoardStatus()).getWolf().getPosition();
		frame.getMap().addWolf(position);
	}

	/** This method adds to the game map all the gates of the board status */
	private void paintGates() {
		for (Gate g : gameController.getBoardStatus().getGates()) {
			String path = null;

			if (g.isLast()) {
				path = GuiConstants.FINAL_GATE;
			} else {
				path = GuiConstants.GATE;
			}

			frame.getMap().addGate(path, g.getPosition());
		}
	}

	/**
	 * This method adds to the game map all the sheep contained in the board
	 * status. Including the black sheep
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
			case NORMALSHEEP:
				map.addLamb(t);
				break;
			case BLACKSHEEP:
				map.addBlackSheep(t);
				break;
			default:
				break;
			}
		}
	}

	/** This method adds all the pawns to the map */
	private void paintPlayers() {
		for (Player p : gameController.getBoardStatus().getPlayers()) {
			if (p instanceof PlayerDouble) {
				paintPlayerDouble((PlayerDouble) p);
			} else {
				paintPlayer(p);
			}
		}
	}

	/** Draw two pawns for the given player double */
	private void paintPlayerDouble(PlayerDouble p) {
		GameMap map = frame.getMap();

		Pawns pawn = Pawns.values()[gameController.getBoardStatus()
				.getPositionOfAPlayer(p)];

		Road p1 = p.getFirstPosition();
		Road p2 = p.getSecondposition();

		if (p1 != null) {
			map.addPawn(pawn, p1);
		}
		if (p2 != null) {
			map.addPawn(pawn, p2);
		}
	}

	/** Draw a pawn for the given player */
	private void paintPlayer(Player p) {
		GameMap map = frame.getMap();

		Pawns pawn = Pawns.values()[gameController.getBoardStatus()
				.getPositionOfAPlayer(p)];

		Road position = p.getPosition();

		if (position != null) {
			map.addPawn(pawn, position);
		}
	}

	public MainFrame getFrame() {
		return frame;
	}
}