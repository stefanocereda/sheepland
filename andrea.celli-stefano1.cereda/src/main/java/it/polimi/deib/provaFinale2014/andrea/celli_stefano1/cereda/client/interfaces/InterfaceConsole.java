package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.TypeOfPlayerMoves;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * This class manages the command line interface. It has two main types of
 * methods. The first type is methods to asl moves to the client. The secondo is
 * methods to update the view. These methods are called in the game controller
 * when new updates are executed.
 * 
 * @author Andrea
 * 
 */
public class InterfaceConsole implements Interface {

	GameControllerClient gameController;
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

		// buy a new card
		if (answer.equals(TypeOfPlayerMoves.BUYCARD.getName()))
			return askForBuyCardMove();
		if (answer.equals(TypeOfPlayerMoves.MOVEPLAYER.getName()))
			return askForMovePlayer();
		if (answer.equals(TypeOfPlayerMoves.MOVESHEEP.getName()))
			return askForMoveSheep();
		return null;
	}

	public void setReferenceToGameController(GameControllerClient gameController) {
		// TODO Auto-generated method stub

	}

	public void notifyMove(Move move) {
		// TODO Auto-generated method stub

	}

	public void notifyNotValidMove() {
		// TODO Auto-generated method stub

	}

	public void notifyDisconnection() {
		// TODO Auto-generated method stub

	}

	public void notifyCurrentPlayer(Player newCurrentPlayer) {
		// TODO Auto-generated method stub

	}

	public void notifyWinners(ArrayList<Player> winners) {
		// TODO Auto-generated method stub

	}

	public void showAllStatus() {

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
		for (Card card : gameController.getBoardStatus().getDeck())
			System.out.println(card.toString());
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
		ArrayList<Integer> freeRoad = new ArrayList<Integer>();
		// looks for the free roads and puts them in an arrayList
		for (int i = 1; i <= Costants.NUMBER_OF_ROADS; i++)
			if (gameController.getBoardStatus().isFreeRoad(allRoads.get(i)))
				freeRoad.add(i);

		// shows to the user the free roads
		System.out.println("The free roads are: ");
		for (Integer road : freeRoad) {
			System.out.print(road + " ");
		}

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

	private Move askForMoveSheep() {
		return null;
	}

	public Road chooseInitialPosition() {
		// TODO Auto-generated method stub
		return null;
	}

}
