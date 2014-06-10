/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class GameControllerTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController#GameController(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler.ListOfClientHandler)}
	 * .
	 */
	@Test
	public void testGameController() {
		List<ClientHandler> list1 = new ArrayList<ClientHandler>();
		List<ClientHandler> list2 = new ArrayList<ClientHandler>();

		GameController gc1 = new GameController(list1);
		assertNotNull(gc1);

		GameController gc2 = new GameController(list2);
		assertNotNull(gc2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController#notifyDisconnection(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player)}
	 * .
	 */
	@Test
	public void testNotifyDisconnection() {
		Player p = new Player();
		GameController gc1 = new GameController(null);

		gc1.notifyDisconnection(p);
		assertFalse(p.isConnected());
		assertTrue(p.isSuspended());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController#notifyReconnection(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler)}
	 * .
	 */
	@Test
	public void testNotifyReconnection() {
		ClientHandler ch1 = new FakeClientHandler(null);
		List<ClientHandler> clients = new ArrayList<ClientHandler>();
		clients.add(ch1);

		Player p = new Player();
		p.setID();
		ch1.setPlayer(p);

		GameController gc1 = new GameController(clients);
		ch1.setGame(gc1);

		gc1.notifyDisconnection(p);

		ClientHandler ch2 = new FakeClientHandler(null);
		gc1.notifyReconnection(p, ch2);

		assertTrue(p.isConnected());
		assertFalse(p.isSuspended());

		assertTrue(clients.contains(ch2));
		assertFalse(clients.contains(ch1));

	}

	/** Fake class for the tests */
	class FakeClientHandler extends ClientHandler {

		public FakeClientHandler(ServerStarter creator) {
			super(creator);
		}

		public Move askMove() throws ClassNotFoundException,
				ClientDisconnectedException {
			// TODO Auto-generated method stub
			return null;
		}

		public void executeMove(Move moveToExecute)
				throws ClientDisconnectedException {
			// TODO Auto-generated method stub

		}

		public Move sayMoveIsNotValid() throws ClassNotFoundException,
				ClientDisconnectedException {
			// TODO Auto-generated method stub
			return null;
		}

		public void sendNewStatus(BoardStatus newStatus)
				throws ClientDisconnectedException {
			// TODO Auto-generated method stub

		}

		public void pingTheClient() throws ClientDisconnectedException {
			// TODO Auto-generated method stub

		}

		public void setCurrentPlayer(Player newCurrentPlayer)
				throws ClientDisconnectedException {
			// TODO Auto-generated method stub

		}

		public void sendWinners(List<Player> winners)
				throws ClientDisconnectedException {
			// TODO Auto-generated method stub

		}

		public Road askInitialPosition() throws ClientDisconnectedException,
				ClassNotFoundException {
			// TODO Auto-generated method stub
			return null;
		}

		public void notifyControlledPlayer(Player controlled)
				throws ClientDisconnectedException {
			// TODO Auto-generated method stub

		}

		public boolean chooseShepherd() throws ClientDisconnectedException {
			// TODO Auto-generated method stub
			return false;
		}

		public Road askSecondInitialPosition()
				throws ClientDisconnectedException, ClassNotFoundException {
			// TODO Auto-generated method stub
			return null;
		}

		public void sendShepherd(boolean usingSecond)
				throws ClientDisconnectedException {
			// TODO Auto-generated method stub

		}

		public List<MarketOffer> askMarketOffers()
				throws ClassNotFoundException, ClientDisconnectedException {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
