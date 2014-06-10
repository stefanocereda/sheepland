package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.ClientMainClass;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.NetworkConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.RMICostants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.TimeConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ServerMainClass;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMIConnector;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * This isn't really a test, we use it just to launch the debugging without
 * manually launching all the processes
 * 
 * @author Stefano
 * 
 */
public class CompleteSimulationTest {

	@Test
	public void test() {
		// starts the server
		ServerMainClass.main(null);
		waitServer();
		System.out.println("server started");

		List<Thread> players = new ArrayList<Thread>();
		// launch two socket client
		for (int i = 0; i < GameConstants.MAX_PLAYERS_IN_A_GAME; i++) {
			Client c = new Client(new String[] { "fake", "socket", "0" });
			Thread t = new Thread(c);
			players.add(t);
			t.start();
		}
		/**
		 * System.out.println("two fake socket created");
		 * 
		 * // launch two rmi client for (int i =
		 * GameConstants.MAX_PLAYERS_IN_A_GAME / 2; i <
		 * GameConstants.MAX_PLAYERS_IN_A_GAME; i++) { Client c = new Client(new
		 * String[] { "fake", "rmi" }); Thread t = new Thread(c);
		 * players.add(t); t.start(); }
		 * System.out.println("two fake rmi created");
		 * 
		 * // launch a socket client Client c1 = new Client(new String[] {
		 * "fake", "socket" }); Thread t1 = new Thread(c1); players.add(t1);
		 * t1.start(); // and an rmi Client c2 = new Client(new String[] {
		 * "fake", "rmi" }); Thread t2 = new Thread(c2); players.add(t2);
		 * t2.start(); System.out.println("created one socket and one rmi");
		 */
		
		// let them play for at most one minute
		int counter = 0;
		do {
			try {
				Thread.sleep(2 * TimeConstants.WAITING_FOR_MAX_PLAYERS);
				counter++;
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
		} while (!finished(players) && counter < 2);
	}

	/** Keep searching for the rmi server */
	private void waitServer() {
		try {
			// get the remote registry
			Registry registry = LocateRegistry.getRegistry(
					NetworkConstants.SERVER_RMI_ADDRESS,
					NetworkConstants.REGISTRY_IP_PORT);

			// Search the server acceptor
			RMIConnector connector = (RMIConnector) registry
					.lookup(RMICostants.CONNECTOR);
		} catch (Exception e) {
			waitServer();
		}
	}

	private boolean finished(List<Thread> threads) {
		for (Thread t : threads) {
			if (t.isAlive()) {
				return false;
			}
		}
		return true;
	}

	class Client implements Runnable {
		private String[] args;

		public Client(String[] arg) {
			args = arg;
		}

		public void run() {
			ClientMainClass.main(args);
		}
	}
}
