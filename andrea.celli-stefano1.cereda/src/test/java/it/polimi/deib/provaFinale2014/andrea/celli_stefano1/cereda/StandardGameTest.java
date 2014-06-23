/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda;

import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkHandlerRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkHandlerSocket;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.NetworkConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.RMICostants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Dice;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ServerMainClass;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMIConnector;

import org.junit.Test;

/**
 * This test simulates a game with three players, using socket and rmi
 * 
 * @author stefano
 * 
 */
public class StandardGameTest {
	List<Client> clients = new ArrayList<StandardGameTest.Client>();

	@Test
	public void test() {
		ServerMainClass.main(null);
		waitServer();

		// launch two socket client
		for (int i = 0; i < GameConstants.MAX_PLAYERS_IN_A_GAME / 2; i++) {
			Client c = new Client(false);
			Thread t = new Thread(c);
			t.start();
			clients.add(c);
		}

		// launch two rmi client
		for (int i = GameConstants.MAX_PLAYERS_IN_A_GAME / 2; i < GameConstants.MAX_PLAYERS_IN_A_GAME; i++) {
			Client c = new Client(true);
			Thread t = new Thread(c);
			t.start();
			clients.add(c);
		}

		// launch a socket client
		Client c1 = new Client(false);
		Thread t1 = new Thread(c1);
		t1.start();
		clients.add(c1);
		// and an rmi Client
		Client c2 = new Client(true);
		Thread t2 = new Thread(c2);
		t2.start();
		clients.add(c2);

		// let them play
		int counter = 0;
		do {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			counter++;
		} while (counter < 5 && !completed());

	}

	private boolean completed() {
		for (Client c : clients) {
			if (!c.isFinished()) {
				return false;
			}
		}
		return true;
	}

	/** Keep searching for the rmi server */
	private void waitServer() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class TestInterface implements Interface {
		private GameControllerClient gc;
		private boolean isFinished = false;

		public void setReferenceToGameController(
				GameControllerClient gameControllerClient) {
			gc = gameControllerClient;
		}

		public void showInitialInformation() {
			assertNotNull(gc);
			assertNotNull(gc.getBoardStatus());
			assertNotNull(gc.getControlledPlayer());
		}

		public void notifyNewStatus() {
			assertNotNull(gc);
			assertNotNull(gc.getBoardStatus());
			assertNotNull(gc.getBoardStatus().getBlackSheep());
			assertNotNull(gc.getBoardStatus().getCurrentPlayer());
			assertNotNull(gc.getBoardStatus().getDeck());
			assertNotNull(gc.getBoardStatus().getFirstPlayer());
			assertNotNull(gc.getBoardStatus().getGates());
			assertNotNull(gc.getBoardStatus().getPlayers());
			assertNotNull(gc.getBoardStatus().getRoadMap());
			assertNotNull(gc.getBoardStatus().getSheeps());

		}

		public Road chooseInitialPosition() {
			Dice dice = Dice.create();
			Map<Integer, Road> roads = gc.getBoardStatus().getRoadMap()
					.getHashMapOfRoads();
			Road toRet = null;

			do {
				toRet = roads.get(dice.roll(roads.size()));
			} while (!gc.getBoardStatus().isFreeRoad(toRet));

			return toRet;
		}

		public void notifyMove(Move move) {
			assertNotNull(move);
		}

		public Move getNewMove() {
			return new MovePlayer(gc.getControlledPlayer(),
					chooseInitialPosition());
		}

		public void notifyNotValidMove() {
		}

		public void notifyCurrentPlayer(Player newCurrentPlayer) {
			assertNotNull(newCurrentPlayer);
		}

		public void notifyWinners(List<Player> winners) {
			assertNotNull(winners);
			assertNotNull(winners.get(0));
			System.out.println("game over");
			isFinished = true;
		}

		public void notifyDisconnection() {

		}

		public boolean chooseShepherd() {
			Random rnd = new Random();
			return rnd.nextBoolean();
		}

		public Road chooseSecondInitialPosition() {
			return chooseInitialPosition();
		}

		public void notifyShepherd(boolean usingSecond) {
			assertNotNull(usingSecond);
		}

		public List<MarketOffer> askMarketOffers() {
			Random rnd = new Random();
			List<MarketOffer> offers = new ArrayList<MarketOffer>();

			for (Card c : gc.getControlledPlayer().getCards()) {
				if (rnd.nextBoolean()) {
					offers.add(new MarketOffer(gc.getControlledPlayer(), c, rnd
							.nextInt(20)));
				}
			}

			return offers;
		}

		public List<MarketBuy> askMarketBuy(List<MarketOffer> offers) {
			Random rnd = new Random();
			List<MarketBuy> buy = new ArrayList<MarketBuy>();

			for (MarketOffer mo : offers) {
				if (rnd.nextBoolean()) {
					buy.add(new MarketBuy(gc.getControlledPlayer(), mo
							.getCardOffered()));
				}
			}

			return buy;
		}

		public boolean isFinished() {
			return isFinished;
		}
	}

	private class Client implements Runnable {
		private boolean useRmi = false;
		private TestInterface ux = new TestInterface();

		public Client(boolean rmi) {
			useRmi = rmi;
		}

		public void run() {
			GameControllerClient gc = new GameControllerClient(ux);

			try {
				if (useRmi) {
					NetworkHandlerRMI rmiClient = new NetworkHandlerRMI(gc, 0);
					rmiClient.connect();
				} else {
					InetSocketAddress serverAddress = NetworkConstants.SERVER_SOCKET_ADDRESS;

					NetworkHandlerSocket socketClient;

					socketClient = new NetworkHandlerSocket(serverAddress, gc,
							0);
					socketClient.start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public boolean isFinished() {
			return ux.isFinished();
		}
	}
}
