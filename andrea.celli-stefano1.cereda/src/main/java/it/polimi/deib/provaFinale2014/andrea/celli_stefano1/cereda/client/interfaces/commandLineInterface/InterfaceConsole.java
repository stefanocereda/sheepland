package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.commandLineInterface;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.PlayerAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.TypeOfPlayerMoves;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class manages the command line interface. It has two main types of
 * methods. The first type is methods to ask moves to the client. The secondo is
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
		for (TypeOfPlayerMoves move : TypeOfPlayerMoves.values())
			System.out.print(move.getName() + "  ");
		// wait for the player's choice
		do {
			System.out.println("Choose the type of move:");
			answer = in.nextLine();
		} while (!isCorrectAnswer(TypeOfPlayerMoves.values(), answer));

		// depending on the type of move the method goes on asking for
		// information to the player

		// the new move is stored in lastMove
		if (answer.equals(TypeOfPlayerMoves.BUYCARD.getName()))
			return askForBuyCardMove();
		if (answer.equals(TypeOfPlayerMoves.MOVEPLAYER.getName()))
			return askForMovePlayer();
		if (answer.equals(TypeOfPlayerMoves.MOVESHEEP.getName()))
			return askForMoveSheep();
		return null;
	}

	/**
	 * This methods links the InterfaceConsole with the GameController. This
	 * passage couldn't be done in the initialization because they are created
	 * at the same time.
	 * 
	 * @param the
	 *            game controller that has to be linked
	 */
	public void setReferenceToGameController(GameControllerClient gameController) {
		this.gameController = gameController;
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
				// determines the number of the player
				int numberOfThePlayer = gameController.getBoardStatus()
						.getPlayerNumber(((PlayerAction) move).getPlayer());

				if (move instanceof MovePlayer) {
					// looks for the number of the road
					int numberOfTheRoad = 0;
					HashMap<Integer, Road> map = (HashMap<Integer, Road>) gameController
							.getBoardStatus().getRoadMap().getHashMapOfRoads();
					for (int i = 1; i <= GameConstants.NUMBER_OF_ROADS; i++)
						if (((MovePlayer) move).getNewPositionOfThePlayer()
								.equals(map.get(i))) {
							numberOfTheRoad = i;
							break;
						}
					System.out.println("Player " + numberOfThePlayer
							+ " moved to road " + numberOfTheRoad + " paying "
							+ ((MovePlayer) move).getCost());
				} else {

					if (move instanceof MoveSheep) {
						// the black sheep has been moved
						if (((MoveSheep) move).getMovedSheep()
								.equals(gameController.getBoardStatus()
										.getBlackSheep()))
							System.out.println("Player "
									+ numberOfThePlayer
									+ "moved the black sheep from "
									+ ((MoveSheep) move).getMovedSheep()
											.getPosition().toString()
									+ " to "
									+ ((MoveSheep) move)
											.getNewPositionOfTheSheep()
											.toString());
						else
							// standard sheep
							System.out.println("Player "
									+ numberOfThePlayer
									+ "moved a sheep from "
									+ ((MoveSheep) move).getMovedSheep()
											.getPosition().toString()
									+ " to "
									+ ((MoveSheep) move)
											.getNewPositionOfTheSheep()
											.toString());
					} else {
						if (move instanceof BuyCardMove)
							System.out.println("Player "
									+ numberOfThePlayer
									+ " purchased card "
									+ ((BuyCardMove) move).getNewCard()
											.toString());
					}

				}

			}
		}
	}

	public void notifyNotValidMove() {
		System.out
				.println("The move goes against Sheepland's rule! Be more careful");
	}

	public void notifyDisconnection() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method tells the client who has to play
	 * 
	 * @param the
	 *            current player
	 */
	public void notifyCurrentPlayer(Player newCurrentPlayer) {
		if (newCurrentPlayer.equals(gameController.getControlledPlayer()))
			System.out.println("It's your turn, be ready!");
		else
			System.out.println("It's now the turn of player "
					+ gameController.getBoardStatus().getPlayerNumber(
							newCurrentPlayer));
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

	/**
	 * @TODO all
	 */
	public void showAllStatus() {

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

	/**
	 * This method takes a generic array and checks wheter the answer of the
	 * client has a string equivalent in the array.
	 * 
	 * @param array
	 * @param answer
	 *            the answer of the client
	 * @return boolean
	 */

	private boolean isCorrectAnswer(Object[] array, String answer) {
		for (int index = 0; index < array.length; index++)
			if (array[index].toString().equals(answer))
				return true;
		return false;
	}

	/**
	 * This method is used to ask and create a new buyCardMove
	 * 
	 * @return move the new buycard move
	 */
	private Move askForBuyCardMove() {
		String answer;
		// it displays the cards remaining in the deck
		System.out.println("The cards in the deck are: ");
		show(gameController.getBoardStatus().getDeck().toArray());
		// ask to choose a card
		do {
			System.out.println("Choose a card ");
			answer = in.nextLine();
		} while (!isCorrectAnswer(gameController.getBoardStatus().getDeck()
				.toArray(), answer));
		// looks for the card
		for (Card card : gameController.getBoardStatus().getDeck())
			if (answer.equals(card.toString()))
				// creates the move
				return new BuyCardMove(gameController.getBoardStatus()
						.getCurrentPlayer(), card);
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

		// create the move
		Integer newRoad = Integer.parseInt(answer);
		return new MovePlayer(gameController.getBoardStatus()
				.getCurrentPlayer(), allRoads.get(newRoad), 0);
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

		HashMap<Terrain, Integer> map = (HashMap) gameController
				.getBoardStatus().calculateNumberOfSheepForEachTerrain();

		// print the number of sheep for each terrain
		System.out.println("These are the numbers of sheep for each terrain");
		for (Terrain terrain : Terrain.values())
			System.out.println("Terrain " + terrain.toString()
					+ " number of sheep: " + map.get(terrain));
		// specifies where the black sheep is
		System.out
				.println("The black sheep is among the sheep in the terrain: "
						+ gameController.getBoardStatus().getBlackSheep()
								.getPosition().toString());

		System.out
				.println("(To move the black sheep choose the terrain where it's located)");
		// ask for the sheep to move (the player has to choose the terrain in
		// which the seep is)
		do {
			System.out.println("Choose the terrain where to pick the sheep:");
			from = in.nextLine();
		} while (!isCorrectAnswer(Terrain.values(), from));

		// ask for the new position on the sheep
		do {
			System.out
					.println("Choose the terrain where you want to place the sheep:");
			to = in.nextLine();
		} while (!isCorrectAnswer(Terrain.values(), to));

		// looks for the terrains corresponding to the choices of the player
		for (Terrain terrain : Terrain.values()) {
			if (from.equals(terrain.toString()))
				terrainFrom = terrain;
			else if (to.equals(terrain.toString()))
				terrainTo = terrain;
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
			for (Sheep sheep : gameController.getBoardStatus().getSheeps())
				if (sheep.getPosition().equals(terrainFrom)) {
					sheepToMove = sheep;
					break;
				}
		}

		// creates and return the move
		return new MoveSheep(
				gameController.getBoardStatus().getCurrentPlayer(),
				sheepToMove, terrainTo);
	}

	/**
	 * This method is used during the initialization of a game. It allows the
	 * player to choose its initial position.
	 * 
	 * @return the initial position of the player
	 */
	public Road chooseInitialPosition() {
		String answer;
		ArrayList<Integer> freeRoads;
		Map<Integer, Road> roadMap = gameController.getBoardStatus()
				.getRoadMap().getHashMapOfRoads();

		// finds and shows the free roads (another player may have already
		// choosen a road)
		System.out
				.println("Choose your initial position among the following roads:");
		freeRoads = (ArrayList<Integer>) gameController.getBoardStatus()
				.findFreeRoads();
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

	/**
	 * This method prints the string values of a generic array of objects.
	 * 
	 * @param toShow
	 */
	private void show(Object[] toShow) {

		for (int i = 0; i < toShow.length; i++)
			System.out.println(toShow[i]);
	}

}
