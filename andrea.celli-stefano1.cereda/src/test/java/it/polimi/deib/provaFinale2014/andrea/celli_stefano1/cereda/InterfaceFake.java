package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Dice;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InterfaceFake implements Interface {
	GameControllerClient gameController;
	Random rnd = new Random();

	public Move getNewMove() {
		System.out.println("the server asked a move");
		Move move = newCorrectMove();
		System.out.println("found a correct move");
		return move;
	}

	private Move newCorrectMove() {
		BoardStatus status = gameController.getBoardStatus();
		Player player = status.getCurrentPlayer();

		// try to move on an adjacent road
		player.hashCode();
		player.getPosition();
		player.getPosition().getNextRoads();

		for (Road r : player.getPosition().getNextRoads()) {
			if (status.isFreeRoad(r)) {
				Move move = new MovePlayer(player, r, 0);
				return move;
			}
		}

		// if it wasn't possible try to move a sheep
		for (Sheep s : status.getSheeps()) {
			if (s.getPosition().equals(
					player.getPosition().getAdjacentTerrains()[0])) {
				Move move = new MoveSheep(player, s, player.getPosition()
						.getAdjacentTerrains()[1]);
				return move;
			}
			if (s.getPosition().equals(
					player.getPosition().getAdjacentTerrains()[1])) {
				Move move = new MoveSheep(player, s, player.getPosition()
						.getAdjacentTerrains()[0]);
				return move;
			}
		}

		// if it wasn't possible try to buy a card
		Deck deck = status.getDeck();
		for (Card c : deck) {
			if (c.getTerrainType().equals(
					player.getPosition().getAdjacentTerrains()[0])
					|| c.getTerrainType().equals(
							player.getPosition().getAdjacentTerrains()[1])) {
				Move move = new BuyCardMove(player, c);
				return move;
			}
		}

		// if it wasn't possible try to go on a random road
		for (int i = 1; i <= status.getRoadMap().getHashMapOfRoads().size(); i++)
			if (status.isFreeRoad(status.getRoadMap().getHashMapOfRoads()
					.get(i))) {
				Move move = new MovePlayer(player, status.getRoadMap()
						.getHashMapOfRoads().get(i), 0);
				return move;
			}

		// if it wasn't possible cazzi tuoi
		System.out
				.println("l'interfaccia fasulla non riesce a generare una mossa");
		return null;
	}

	public void setReferenceToGameController(GameControllerClient gameController) {
		this.gameController = gameController;
		System.out.println("Setted the reference to game controller");
	}

	public void notifyMove(Move move) {
		System.out.println("The server sended a move");
	}

	public void notifyNotValidMove() {
		System.out.println("Server said that the last move wasn't valid");
	}

	public void notifyDisconnection() {
		System.out.println("we're disconnected");
	}

	public void notifyCurrentPlayer(Player newCurrentPlayer) {
		System.out.println("the server sent a new current player");
	}

	public void notifyWinners(List<Player> winners) {
		System.out.println("game ended");
		for (Player p : winners) {
			System.out.println(p + " is a winner");
		}
	}

	public Road chooseInitialPosition() {
		System.out.println("the server asked our initial position");
		Dice dice = Dice.create();
		Map<Integer, Road> roads = gameController.getBoardStatus().getRoadMap()
				.getHashMapOfRoads();
		System.out.println("choosen a random initial position");
		return roads.get(dice.roll(roads.size()));
	}
}