package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.ClientMainClass;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ServerMainClass;

import org.junit.Ignore;
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
	@Ignore
	public void test() {
		ServerMainClass.main(null);// starts the server

		for (int i = 0; i < GameConstants.MAX_PLAYERS_IN_A_GAME; i++) {
			(new Thread(new socketClient())).start();
		}

		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class socketClient implements Runnable {
		public void run() {
			try {
				ClientMainClass.launchSocket();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
