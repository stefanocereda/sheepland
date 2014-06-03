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
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// launch client
		for (int i = 0; i < GameConstants.MAX_PLAYERS_IN_A_GAME; i++) {
			(new Thread(new client())).start();
		}

		// let them play
		try {
			Thread.sleep(150000 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class client implements Runnable {
		public void run() {
			String[] args = { "socket", "fake" };
			ClientMainClass.main(args);
		}
	}
}
