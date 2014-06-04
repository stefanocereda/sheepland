package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.ClientMainClass;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ServerMainClass;

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
		ServerMainClass.main(null);// starts the server

		// wait for sever starting
		try {
			Thread.sleep(1 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// launch socket client
		for (int i = 0; i < GameConstants.MAX_PLAYERS_IN_A_GAME; i++) {
			Client c = new Client(new String[] { "fake", "socket" });
			(new Thread(c)).start();
		}

		// // launch rmi client
		// for (int i = GameConstants.MAX_PLAYERS_IN_A_GAME / 2; i <
		// GameConstants.MAX_PLAYERS_IN_A_GAME; i++) {
		// Client c = new Client(new String[] { "console", "rmi" });
		// (new Thread(c)).start();
		// }

		// let them play
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
