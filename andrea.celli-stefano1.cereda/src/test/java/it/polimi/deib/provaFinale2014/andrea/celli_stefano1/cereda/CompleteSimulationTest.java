package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkHandlerRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkHandlerSocket;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.NetworkConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ServerMainClass;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

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
			(new Thread(new rmiClient())).start();
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
				Interface ui = new InterfaceFake();
				GameControllerClient controller = new GameControllerClient(ui);
				InetSocketAddress serverAddress = NetworkConstants.SERVER_SOCKET_ADDRESS;
				NetworkHandlerSocket socketClient;
				socketClient = new NetworkHandlerSocket(serverAddress,
						controller);
				socketClient.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class rmiClient implements Runnable {
		public void run() {
			try {
				Interface ui = new InterfaceFake();
				GameControllerClient controller = new GameControllerClient(ui);
				NetworkHandlerRMI rmiClient = new NetworkHandlerRMI(controller);
				rmiClient.connect();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}

		}
	}
}
