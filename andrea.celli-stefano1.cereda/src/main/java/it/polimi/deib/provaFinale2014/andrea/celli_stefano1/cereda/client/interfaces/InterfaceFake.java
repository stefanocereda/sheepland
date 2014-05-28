package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
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
		return newCorrectMove();
	}

	private Move newCorrectMove() {
		System.out.println("checking for a move");
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
		System.err
				.println("l'interfaccia fasulla non riesce a generare una mossa");
		return null;
	}

	public void setReferenceToGameController(GameControllerClient gameController) {
		this.gameController = gameController;
	}

	private Move newRandomMove() {
		// first of all choose the type of move
		int type = rnd.nextInt(4);
		switch (type) {
		case 0:
			return newBuyCardMove();
		case 1:
			return newMoveBlackSheep();
		case 2:
			return newMovePlayer();
		case 3:
			return newMoveSheep();
		}
		return null;
	}

	private Move newMoveSheep() {
		// randomly choose a player
		Player[] players = gameController.getBoardStatus().getPlayers();
		Player player = players[rnd.nextInt(players.length)];

		// randomly choose a sheep
		List<Sheep> sheeps = gameController.getBoardStatus().getSheeps();
		Sheep sheep = sheeps.get(rnd.nextInt(sheeps.size()));

		// and randomly choose a terrain
		Terrain[] terrains = Terrain.values();
		Terrain terrain = terrains[rnd.nextInt(terrains.length)];

		// return the move
		return new MoveSheep(player, sheep, terrain);
	}

	private Move newMovePlayer() {
		// randomly choose a player
		Player[] players = gameController.getBoardStatus().getPlayers();
		Player player = players[rnd.nextInt(players.length)];

		// randomly choose a road
		Map<Integer, Road> roads = gameController.getBoardStatus().getRoadMap()
				.getHashMapOfRoads();
		Road road = roads.get(rnd.nextInt(roads.size()));

		return new MovePlayer(player, road, 0);
	}

	private Move newMoveBlackSheep() {
		BlackSheep bs = gameController.getBoardStatus().getBlackSheep();

		// and randomly choose a terrain
		Terrain[] terrains = Terrain.values();
		Terrain terrain = terrains[rnd.nextInt(terrains.length)];

		return new MoveBlackSheep(terrain, bs);
	}

	private Move newBuyCardMove() {
		// randomly choose a player
		Player[] players = gameController.getBoardStatus().getPlayers();
		Player player = players[rnd.nextInt(players.length)];

		// and a card
		Deck deck = gameController.getBoardStatus().getDeck();
		Card card = deck.get(rnd.nextInt(deck.size()));

		return new BuyCardMove(player, card);
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

	public void notifyWinners(List<Player> winners) {
		System.out.println("game ended");
	}

	public Road chooseInitialPosition() {
		Dice dice = Dice.create();
		Map<Integer, Road> roads = gameController.getBoardStatus().getRoadMap()
				.getHashMapOfRoads();
		return roads.get(dice.roll(roads.size()));
	}
}