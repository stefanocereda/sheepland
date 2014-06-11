package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Butchering;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Mating;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Dice;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.TerrainType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InterfaceFake implements Interface {
	GameControllerClient gameController;
	Random rnd = new Random();

	public Move getNewMove() {
		Move move = newReallyRandomMove();
		return move;
	}

	private Move newCorrectMove() {
		BoardStatus status = gameController.getBoardStatus();
		Player player = status.getCurrentPlayer();

		// try to move on an adjacent road
		for (Road r : player.getPosition().getNextRoads()) {
			if (status.isFreeRoad(r)) {
				Move move = new MovePlayer(player, r);
				return move;
			}
		}

		// if it wasn't possible try to go on a random road
		for (int i = 1; i <= status.getRoadMap().getHashMapOfRoads().size(); i++) {
			if (status.isFreeRoad(status.getRoadMap().getHashMapOfRoads()
					.get(i))) {
				Move move = new MovePlayer(player, status.getRoadMap()
						.getHashMapOfRoads().get(i));
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

		// if it wasn't possible cazzi tuoi
		System.out
				.println("l'interfaccia fasulla non riesce a generare una mossa");
		return null;
	}

	private Move newReallyRandomMove() {
		int type = rnd.nextInt(5);

		if (type == 0) {
			return newReallyRandomBuyCard();
		}
		if (type == 1) {
			return newReallyRandomMoveSheep();
		}
		if (type == 2) {
			return newReallyRandomMovePlayer();
		}
		if (type == 3) {
			return newReallyRandomMating();
		}
		if (type == 4) {
			return newReallyRandomButchering();
		}
		return null;
	}

	private Move newReallyRandomButchering() {
		return new Butchering(sortPlayer(), sortSheep());
	}

	private Move newReallyRandomMating() {
		return new Mating(sortPlayer(), sortTerrain());
	}

	private Move newReallyRandomBuyCard() {
		return new BuyCardMove(sortPlayer(), sortCard());
	}

	private Move newReallyRandomMoveSheep() {
		return new MoveSheep(sortPlayer(), sortSheep(), sortTerrain());
	}

	private Move newReallyRandomMovePlayer() {
		return new MovePlayer(sortPlayer(), sortRoad());
	}

	private Move newRandomMove() {
		boolean ok = true;
		Move toReturn = null;
		do {
			ok = true;

			try {
				int type = rnd.nextInt(3);

				if (type == 0) {
					toReturn = newRandomBuyCard();
				}
				if (type == 1) {
					toReturn = newRandomMoveSheep();
				}
				if (type == 2) {
					toReturn = newRandomMovePlayer();
				}
			} catch (Exception e) {
				ok = false;
			}
		} while (!ok);
		return toReturn;
	}

	private Move newRandomBuyCard() {
		return new BuyCardMove(correctPlayer(), correctCard());
	}

	private Move newRandomMoveSheep() {
		Sheep sheep = correctSheep();
		Player player = correctPlayer();
		Terrain terrain = correctTerrain(player, sheep);
		return new MoveSheep(player, sheep, terrain);
	}

	private Move newRandomMovePlayer() {
		return new MovePlayer(correctPlayer(), freeRoad());
	}

	private Player correctPlayer() {
		return gameController.getControlledPlayer();
	}

	private Card correctCard() {
		// search the correct terrain types
		Terrain[] adjacent = gameController.getControlledPlayer().getPosition()
				.getAdjacentTerrains();
		List<TerrainType> ok = new ArrayList<TerrainType>();
		for (Terrain t : adjacent) {
			ok.add(t.getTerrainType());
		}

		// search the correct cards
		List<Card> okCards = new ArrayList<Card>();
		for (Card c : gameController.getBoardStatus().getDeck()) {
			if (ok.contains(c.getTerrainType())) {
				okCards.add(c);
			}
		}

		// keep only the cheaper
		for (int i = 0; i < okCards.size(); i++) {
			for (int j = i + 1; j < okCards.size(); j++) {
				if (okCards.get(i) != null && okCards.get(j) != null) {
					if ((okCards.get(i).getTerrainType() == okCards.get(j)
							.getTerrainType())
							&& (okCards.get(i).getNumber() < okCards.get(j)
									.getNumber())) {
						okCards.remove(j);
					}
				}
			}
		}

		// sort a random one
		return okCards.get(rnd.nextInt(okCards.size()));
	}

	private Sheep correctSheep() {
		Terrain[] adjacent = gameController.getControlledPlayer().getPosition()
				.getAdjacentTerrains();
		List<Sheep> okSheep = new ArrayList<Sheep>();

		for (Sheep s : gameController.getBoardStatus().getSheeps()) {
			if (adjacent[0].equals(s.getPosition())
					|| adjacent[1].equals(s.getPosition())) {
				okSheep.add(s);
			}
		}

		return okSheep.get(rnd.nextInt(okSheep.size()));
	}

	private Terrain correctTerrain(Player player, Sheep sheep) {
		Terrain[] terrains = player.getPosition().getAdjacentTerrains();
		if (terrains[0].equals(sheep.getPosition())) {
			return terrains[1];
		} else {
			return terrains[0];
		}
	}

	private Road freeRoad() {
		List<Road> okRoads = new ArrayList<Road>();

		for (Road r : gameController.getBoardStatus().getRoadMap()
				.getHashMapOfRoads().values()) {
			if (gameController.getBoardStatus().isFreeRoad(r)) {
				okRoads.add(r);
			}
		}

		return okRoads.get(rnd.nextInt(okRoads.size()));
	}

	private Player sortPlayer() {
		Player[] players = gameController.getBoardStatus().getPlayers();
		return players[rnd.nextInt(players.length)];
	}

	private Card sortCard() {
		Card[] cards = Card.values();
		return cards[rnd.nextInt(cards.length)];
	}

	private Sheep sortSheep() {
		List<Sheep> sheeps = gameController.getBoardStatus().getSheeps();
		return sheeps.get(rnd.nextInt(sheeps.size()));
	}

	private Terrain sortTerrain() {
		Terrain[] terrains = Terrain.values();
		return terrains[rnd.nextInt(terrains.length)];
	}

	private Road sortRoad() {
		Map<Integer, Road> roads = gameController.getBoardStatus().getRoadMap()
				.getHashMapOfRoads();
		return roads.get(rnd.nextInt(roads.size()));
	}

	public void setReferenceToGameController(GameControllerClient gameController) {
		this.gameController = gameController;
		System.out.println("Setted the reference to game controller");
	}

	public void notifyMove(Move move) {
	}

	public void notifyNotValidMove() {
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
			System.out.println("Player "
					+ gameController.getBoardStatus().getPlayerNumber(p)
					+ " is a winner");
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

	public void showInitialInformation() {
		System.out.println("the game is starting");
	}

	public void notifyNewStatus() {
		System.out.println("received a new status");
	}

	public boolean chooseShepherd() {
		return rnd.nextBoolean();
	}

	public Road chooseSecondInitialPosition() {
		System.out.println("this is a game with two players");
		return chooseInitialPosition();
	}

	public void notifyShepherd(boolean usingSecond) {
	}

	//
	//
	//
	// TODO ADVANCED MOVES
	//
	//
	//
	public List<MarketOffer> askMarketOffers() {
		List<MarketOffer> toReturn = new ArrayList<MarketOffer>();
		Player me = gameController.getBoardStatus().getEquivalentPlayer(
				gameController.getControlledPlayer());
		List<Card> myCards = me.getCards();
		Dice dice = Dice.create();

		for (Card c : myCards) {
			if (dice.roll(2) == 1) {
				int price = dice.roll(10);
				MarketOffer offer = new MarketOffer(me, c, price);
				toReturn.add(offer);
			}
		}

		return toReturn;
	}

	public List<MarketBuy> askMarketBuy(List<MarketOffer> offers) {
		List<MarketBuy> toReturn = new ArrayList<MarketBuy>();
		Player me = gameController.getBoardStatus().getEquivalentPlayer(
				gameController.getControlledPlayer());
		Dice dice = Dice.create();

		for (MarketOffer offer : offers) {
			if (dice.roll(2) == 1) {
				MarketBuy buy = new MarketBuy(me, offer.getCardOffered());
				toReturn.add(buy);
			}
		}

		return toReturn;
	}
}