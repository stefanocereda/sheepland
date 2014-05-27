package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

public class InterfaceFake implements Interface {
	private BoardStatus status;
	Random rnd = new Random();

	public Move getNewMove() {
		return newRandomMove();
	}

	public void setReferencetoStatus(BoardStatus boardStatus) {
		status = boardStatus;
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
		Player[] players = status.getPlayers();
		Player player = players[rnd.nextInt(players.length)];

		// randomly choose a sheep
		List<Sheep> sheeps = status.getSheeps();
		Sheep sheep = sheeps.get(rnd.nextInt(sheeps.size()));

		// and randomly choose a terrain
		Terrain[] terrains = Terrain.values();
		Terrain terrain = terrains[rnd.nextInt(terrains.length)];

		// return the move
		return new MoveSheep(player, sheep, terrain);
	}

	private Move newMovePlayer() {
		// randomly choose a player
		Player[] players = status.getPlayers();
		Player player = players[rnd.nextInt(players.length)];

		// randomly choose a road
		Map<Integer, Road> roads = status.getRoadMap().getHashMapOfRoads();
		Road road = roads.get(rnd.nextInt(roads.size()));

		return new MovePlayer(player, road, 0);
	}

	private Move newMoveBlackSheep() {
		BlackSheep bs = status.getBlackSheep();

		// and randomly choose a terrain
		Terrain[] terrains = Terrain.values();
		Terrain terrain = terrains[rnd.nextInt(terrains.length)];

		return new MoveBlackSheep(terrain, bs);
	}

	private Move newBuyCardMove() {
		// randomly choose a player
		Player[] players = status.getPlayers();
		Player player = players[rnd.nextInt(players.length)];

		// and a card
		Deck deck = status.getDeck();
		Card card = deck.get(rnd.nextInt(deck.size()));

		return new BuyCardMove(player, card);
	}
}