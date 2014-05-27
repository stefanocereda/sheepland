/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandlerRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandlerSocket;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler.ListOfClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler.ListOfClientHandlerRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler.ListOfClientHandlerSocket;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;

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
		ListOfClientHandler list1 = new ListOfClientHandlerRMI();
		ListOfClientHandler list2 = new ListOfClientHandlerSocket();

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
		ClientHandler ch = null;
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
		ClientHandler ch = new FakeClientHandler(null);
		ListOfClientHandler players = new ListOfFakeClientHandler();
		players.add(ch);

		Player p = new Player();
		ch.setPlayer(p);

		GameController gc1 = new GameController(players);
		ch.setGame(gc1);

		gc1.notifyDisconnection(p);

		ClientHandler chNew = new FakeClientHandler(null);
		gc1.notifyReconnection(p, chNew);

		assertTrue(p.isConnected());
		assertFalse(p.isSuspended());

		boolean containsOld = false;
		boolean containsNew = false;
		for (int i = 0; i < players.size(); i++)
			if (players.get(i).equals(chNew))
				containsNew = true;
			else if (players.get(i).equals(ch))
				containsOld = true;

		assertTrue(containsNew);
		assertFalse(containsOld);
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

		public void sendWinners(ArrayList<Player> winners)
				throws ClientDisconnectedException {
			// TODO Auto-generated method stub

		}

	}

	/** Fake class for the tests */
	class ListOfFakeClientHandler extends ArrayList<FakeClientHandler>
			implements ListOfClientHandler {

		public boolean add(ClientHandler acceptedHandler) {
			return super.add((FakeClientHandler) acceptedHandler);
		}

		public ClientHandler[] toArray(ClientHandler[] array) {
			return super.toArray(array);
		}

		public ClientHandler set(int i, ClientHandler newClienthandler) {
			return super.set(i, (FakeClientHandler) newClienthandler);
		}

	}
}
