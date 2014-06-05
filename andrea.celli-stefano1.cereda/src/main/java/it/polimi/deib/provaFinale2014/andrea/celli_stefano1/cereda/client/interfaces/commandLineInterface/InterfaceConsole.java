package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.commandLineInterface;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.MoveCostCalculator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.PlayerAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.TypeOfPlayerMoves;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.RoadMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayerDouble;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class manages the command line interface. It has two main types of
 * methods. The first type is methods to ask moves to the client. The second is
 * methods to update the view. These methods are called in the game controller
 * when new updates are executed.
 * 
 * @author Andrea
 * @TODO showAllStatus
 */
public class InterfaceConsole implements Interface {
	/** the game controller acts as a mediator with the board status */
	GameControllerClient gameController;
	/** the input used by the player */
	Scanner in = new Scanner(System.in);

	/** Specifies if we are controlling the second shepherd */
	boolean usingSecondShepherd = false;

	public void setReferenceToGameController(GameControllerClient gameController) {
		this.gameController = gameController;
	}

	public void showInitialInformation() {
		System.out.println("Welcome to sheepland");
		System.out.println("Brace yourself, sheeps are coming!!");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("You're player "
				+ gameController.getBoardStatus().getPlayerNumber(
						gameController.getControlledPlayer()));
		System.out.println(" ");
		System.out.println("Good luck!");
		System.out.println(" ");
	}

	public void notifyNewStatus() {
		System.out.println("THIS IS THE CURRENT STATUS OF THE GAME");

		System.out.println();
		printPlayers();

		System.out.println();
		printSheepCount();

		System.out.println();
		printAdjacentTerrains();

		System.out.println();
		printRemainingCards();
	}

	public Road chooseInitialPosition() {
		String answer;
		List<Integer> freeRoads;
		Map<Integer, Road> roadMap = gameController.getBoardStatus()
				.getRoadMap().getHashMapOfRoads();

		// finds and shows the free roads (another player may have already
		// Chosen a road)
		System.out
				.println("Choose your initial position among the following roads:");
		freeRoads = gameController.getBoardStatus().findFreeRoads();
		show(freeRoads.toArray());

		// wait for the answer and check if it the string represents a free road
		do {
			System.out.println("Choose a road: ");
			answer = in.nextLine();
		} while (!isCorrectAnswer(freeRoads.toArray(), answer));

		// find the number of the chosen road
		Integer road = Integer.parseInt(answer);
		// returns the road
		return roadMap.get(road);
	}

	public Road chooseSecondInitialPosition() {
		System.out
				.println("This is a game with two players and four shepherd, choose the position of your second shepherd:");
		return chooseInitialPosition();
	}

	/**
	 * This methods shows to the player moves that are performed by others.
	 * Every player also receives moves that he's made. notifyMove() shows only
	 * moves made by other player (A player should know what he has just done).
	 * In order to do that it checks if the player that made the move isn't
	 * equal to the controlledPlayer.
	 * 
	 * Moves that aren't performed by players are always displayed.
	 * 
	 * @param move
	 */
	public void notifyMove(Move move) {
		if (!(move instanceof PlayerAction)) {
			// the move is not performed by a player, therefore it's always
			// displayed
			// moves performed by the system HAVE TO override toString()
			System.out.println(move.toString());

		} else {
			// check if the player is different from the controlled player
			if (!gameController.getControlledPlayer().equals(
					((PlayerAction) move).getPlayer())) {

				if (move instanceof MovePlayer) {
					printMovePlayer((MovePlayer) move);
				} else if (move instanceof MoveSheep) {
					printMoveSheep((MoveSheep) move);

				} else if (move instanceof BuyCardMove) {
					printBuyCardMove((BuyCardMove) move);
				}
			}
		}
	}

	/**
	 * This method asks the player for a new move and wait for it. The method
	 * checks if the player input is valid. The method "guides" the player
	 * during the creation of the new move, asking for the correct information
	 * for the specific type of move. For example, if the player has to move a
	 * sheep, the system will display the sheep in their current location and
	 * ask to choose one of them.
	 * 
	 * @return move the move to send to the server
	 */
	public Move getNewMove() {
		String answer;
		System.out.println("Make your move!");
		System.out.println("Types of move:");

		// choose the type of move
		// show options
		for (TypeOfPlayerMoves move : TypeOfPlayerMoves.values()) {
			System.out.print(move.toString() + "  ");
		}
		System.out.println("print status");

		// wait for the player's choice
		do {
			System.out.println("Choose the type of move:");
			answer = in.nextLine();
		} while (!isCorrectAnswer(TypeOfPlayerMoves.values(), answer)
				&& !answer.equals("print status"));

		// depending on the type of move the method goes on asking for
		// information to the player

		// the new move is stored in lastMove
		if (answer.equals(TypeOfPlayerMoves.BUYCARD.toString())) {
			return askForBuyCardMove();
		}
		if (answer.equals(TypeOfPlayerMoves.MOVEPLAYER.toString())) {
			return askForMovePlayer();
		}
		if (answer.equals(TypeOfPlayerMoves.MOVESHEEP.toString())) {
			return askForMoveSheep();
		}
		if (answer.equals("print status")) {
			notifyNewStatus();
			return getNewMove();
		}

		return null;
	}

	public void notifyNotValidMove() {
		System.out
				.println("The move goes against Sheepland's rule! Be more careful");
	}

	public void notifyCurrentPlayer(Player newCurrentPlayer) {
		if (newCurrentPlayer.equals(gameController.getControlledPlayer())) {
			System.out.println("It's your turn, be ready!");
		} else {
			System.out.println("It's now the turn of player "
					+ gameController.getBoardStatus().getPlayerNumber(
							newCurrentPlayer));
		}
	}

	public void notifyShepherd(boolean usingSecond) {
		Player ourPlayer = gameController.getBoardStatus().getEquivalentPlayer(
				gameController.getControlledPlayer());

		// if we are the current player we know what we are using
		if (!ourPlayer.equals(gameController.getBoardStatus()
				.getCurrentPlayer())) {
			String shepherd;
			if (usingSecond) {
				shepherd = "second";
			} else {
				shepherd = "first";
			}

			System.out.println("The current player is using his " + shepherd
					+ " shepherd.");
		}
	}

	public void notifyWinners(List<Player> winners) {
		System.out.println("GAME OVER");
		if (winners.size() < 1) {
			System.out.println("There's only one winner: PLAYER "
					+ gameController.getBoardStatus().getPlayerNumber(
							winners.get(0)));
		} else {
			System.out.println("The winners are:");
			show(winners.toArray());
			System.out.println("IT'S TIME TO BAA, make some noiseee");
		}
		System.out.println("See you soon, mighty sheperd!");
	}

	public void notifyDisconnection() {
		System.out
				.println("We're disconnected from the server, but our shepherd dog is working hard to solve this");
	}

	public boolean chooseShepherd() {
		int answer;

		do {
			System.out
					.println("For this turn you want to use the shepherd number 1 or 2?");
			answer = Integer.parseInt(in.nextLine());
		} while (answer != 1 && answer != 2);

		if (answer == 1) {
			return false;
		}
		return true;
	}

	//
	//
	// HERE STARTS PRIVATE METHODS USED TO ASK SPECIFIC KIND OF MOVES
	//
	//

	/**
	 * This method is used to ask and create a new buyCardMove
	 * 
	 * @return move the new buycard move
	 */
	private Move askForBuyCardMove() {
		String answer;

		printRemainingCards();

		printAdjacentTerrainTypes();

		// ask to choose a card
		do {
			System.out.println("Choose a card ");
			answer = in.nextLine();
		} while (!isCorrectAnswer(gameController.getBoardStatus().getDeck()
				.getBuyableCards().toArray(), answer));

		// looks for the card
		for (Card card : gameController.getBoardStatus().getDeck()) {
			if (answer.equals(card.toString())) {
				// creates the move
				return new BuyCardMove(gameController.getBoardStatus()
						.getCurrentPlayer(), card);
			}
		}

		return null;
	}

	/**
	 * This method is used to ask and create a move player move
	 * 
	 * @return move
	 */
	private Move askForMovePlayer() {
		String answer;
		Map<Integer, Road> allRoads = gameController.getBoardStatus()
				.getRoadMap().getHashMapOfRoads();

		// the numbers identifying free roads
		ArrayList<Integer> freeRoad = (ArrayList<Integer>) gameController
				.getBoardStatus().findFreeRoads();

		// shows to the user the free roads
		System.out.println("The free roads are: ");
		show(freeRoad.toArray());

		// ask the user to choose a road
		do {
			System.out.println("Choose a road");
			answer = in.nextLine();
		} while (!isCorrectAnswer(freeRoad.toArray(), answer));
		Integer newRoad = Integer.parseInt(answer);

		// create the move
		return new MovePlayer(gameController.getBoardStatus()
				.getCurrentPlayer(), allRoads.get(newRoad));
	}

	/**
	 * This method is used to ask to the player a new MoveSheep move. In order
	 * to help the player to choose a sheep the method will display the number
	 * of sheep in each terrain of the map.
	 * 
	 * @return move
	 */
	private Move askForMoveSheep() {

		String from;
		String to;
		Terrain terrainFrom = null;
		Terrain terrainTo = null;
		Sheep sheepToMove = null;
		boolean moveBlackSheep = false;
		String answer;

		printSheepCount();

		System.out
				.println("(To move the black sheep choose the terrain where it's located)");

		printAdjacentTerrains();

		// ask for the sheep to move (the player has to choose the terrain in
		// which the sheep is)
		do {
			System.out.println("Choose the terrain where to pick the sheep:");
			from = in.nextLine();
		} while (!isCorrectAnswer(Terrain.values(), from));

		// ask for the new position of the sheep
		do {
			System.out
					.println("Choose the terrain where you want to place the sheep:");
			to = in.nextLine();
		} while (!isCorrectAnswer(Terrain.values(), to));

		// looks for the terrains corresponding to the choices of the player
		for (Terrain terrain : Terrain.values()) {
			if (from.equals(terrain.toString())) {
				terrainFrom = terrain;
			} else if (to.equals(terrain.toString())) {
				terrainTo = terrain;
			}
		}

		// if the terrain from is the one in which the black sheep is the system
		// ask if the player wants to move a standard sheep or the black sheep
		if (terrainFrom.equals(gameController.getBoardStatus().getBlackSheep()
				.getPosition())) {
			do {
				System.out
						.println("Do you want do move the black sheep? (yes/no)");
				answer = in.nextLine();
			} while (!(answer.equals("yes") || answer.equals("no")));
			if (answer.equals("yes")) {
				moveBlackSheep = true;
				sheepToMove = gameController.getBoardStatus().getBlackSheep();
			}
		}

		// looks for the sheep to move
		if (!moveBlackSheep) {
			for (Sheep sheep : gameController.getBoardStatus().getSheeps()) {
				if (sheep.getPosition().equals(terrainFrom)) {
					sheepToMove = sheep;
					break;
				}
			}
		}

		// creates and return the move
		return new MoveSheep(
				gameController.getBoardStatus().getCurrentPlayer(),
				sheepToMove, terrainTo);
	}

	//
	//
	// HERE STARTS THE PRIVATE METHOD USED TO SHOW VALUES AND CONSEQUENT
	// VALIDATION
	//
	//
	/**
	 * This method takes a generic array and checks whether the answer of the
	 * client has a string equivalent in the array.
	 * 
	 * @param array
	 * @param answer
	 *            the answer of the client
	 * @return boolean
	 */

	private boolean isCorrectAnswer(Object[] array, String answer) {
		for (Object element : array) {
			if (element.toString().equals(answer)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method prints the string values of a generic array of objects.
	 * 
	 * @param toShow
	 */
	private void show(Object[] toShow) {

		for (Object element : toShow) {
			System.out.println(element);
		}
	}

	//
	//
	// HERE STARTS THE METHODS USED TO PRINT INFORMATIONS
	//
	//

	/**
	 * Show all the players with their money and their positions. For the
	 * controlled player also show the cards
	 */
	private void printPlayers() {
		RoadMap roadMap = gameController.getBoardStatus().getRoadMap();

		for (Player p : gameController.getBoardStatus().getPlayers()) {
			String message = "Player "
					+ gameController.getBoardStatus().getPlayerNumber(p)
					+ " is on the road ";

			// if it's a player controlling two shepherd print both the
			// position
			if (p.getClass().equals(PlayerDouble.class)) {
				Road one = ((PlayerDouble) p).getFirstPosition();
				Road two = ((PlayerDouble) p).getSecondposition();
				message += roadMap.getNumberOfRoad(one) + " and on the road "
						+ roadMap.getNumberOfRoad(two);
			} else {
				message += roadMap.getNumberOfRoad(p.getPosition());
			}

			message += " with " + p.getMoney() + " money.";

			System.out.println(message);
		}

		System.out.println();
		System.out.println("Those are your cards:");
		for (Card c : gameController.getControlledPlayer().getCards()) {
			System.out.println(c.toString());
		}
	}

	/**
	 * Print the number of sheep on each terrain and specifies where is the
	 * black sheep
	 */
	private void printSheepCount() {
		System.out.println("These are the numbers of sheep for each terrain");
		Map<Terrain, Integer> map = gameController.getBoardStatus()
				.calculateNumberOfSheepForEachTerrain();
		for (Terrain terrain : Terrain.values()) {
			System.out.println("Terrain " + terrain.toString()
					+ " number of sheep: " + map.get(terrain));
		}

		System.out.println();
		System.out.println("The black sheep is in the terrain: "
				+ gameController.getBoardStatus().getBlackSheep().getPosition()
						.toString());
	}

	/** Print the names of the terrains adjacent the controlled player */
	private void printAdjacentTerrains() {
		Player p = gameController.getBoardStatus().getCurrentPlayer();

		System.out.println("The terrains around you are: ");
		show(p.getPosition().getAdjacentTerrains());

	}

	/** Print the remaining cards in the deck */
	private void printRemainingCards() {
		System.out.println("The cards still in the deck are: ");
		show(gameController.getBoardStatus().getDeck().getBuyableCards()
				.toArray());
	}

	/** Print the terrain types around the player */
	private void printAdjacentTerrainTypes() {
		Player p = gameController.getBoardStatus().getCurrentPlayer();

		System.out.println("Around you there are these kind of terrain:");

		for (Terrain t : p.getPosition().getAdjacentTerrains())
			System.out.println(t.getTerrainType());
	}

	//
	//
	// HERE STARTS METHODS USED TO PRINT MOVES
	//
	//

	/** Print a MovePlayer move, checking for double shepherds */
	private void printMovePlayer(MovePlayer move) {
		Player p = move.getPlayer();
		int numberOfThePlayer = gameController.getBoardStatus()
				.getPlayerNumber(p);
		int numberOfTheRoad = gameController.getBoardStatus().getRoadMap()
				.getNumberOfRoad(move.getNewPositionOfThePlayer());

		String message = "Player "
				+ numberOfThePlayer
				+ " moved to road "
				+ numberOfTheRoad
				+ " paying "
				+ (MoveCostCalculator.getMoveCost(move,
						gameController.getBoardStatus()));

		System.out.println(message);
	}

	/** Print a move sheep, checking if the moved sheep is the blacksheep */
	private void printMoveSheep(MoveSheep move) {
		int numberOfThePlayer = gameController.getBoardStatus()
				.getPlayerNumber(move.getPlayer());

		// check for black sheep
		if (((MoveSheep) move).getMovedSheep().equals(
				gameController.getBoardStatus().getBlackSheep())) {
			System.out.println("Player "
					+ numberOfThePlayer
					+ "moved the black sheep from "
					+ ((MoveSheep) move).getMovedSheep().getPosition()
							.toString() + " to "
					+ ((MoveSheep) move).getNewPositionOfTheSheep().toString());

		} else {
			// standard sheep
			System.out.println("Player "
					+ numberOfThePlayer
					+ " moved a sheep from "
					+ ((MoveSheep) move).getMovedSheep().getPosition()
							.toString() + " to "
					+ ((MoveSheep) move).getNewPositionOfTheSheep().toString());
		}
	}

	/** Print a Buy Card Move */
	private void printBuyCardMove(BuyCardMove move) {
		int numberOfThePlayer = gameController.getBoardStatus()
				.getPlayerNumber(move.getPlayer());

		System.out.println("Player " + numberOfThePlayer + " bought the card "
				+ move.getNewCard());
	}
}