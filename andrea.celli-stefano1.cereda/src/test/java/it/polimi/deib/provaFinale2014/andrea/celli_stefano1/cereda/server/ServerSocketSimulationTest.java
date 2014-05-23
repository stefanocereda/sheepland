package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarterSocket;

import org.junit.Ignore;
import org.junit.Test;

public class ServerSocketSimulationTest {

	@Test
	@Ignore
	public void test() throws UnknownHostException, IOException {
		// create a socket server
		ServerStarter server = new ServerStarterSocket(Costants.SOCKET_IP_PORT,
				Costants.DEFAULT_GAME_TYPE, Costants.MAX_PLAYERS_IN_A_GAME,
				Costants.MAX_PLAYERS_IN_A_GAME);

		// launch it
		server.start();

		// connect some clients
		Socket[] clients = new Socket[Costants.MAX_PLAYERS_IN_A_GAME];
		for (int i = 0; i < Costants.MAX_PLAYERS_IN_A_GAME; i++) {
			clients[i] = new Socket("localhost", Costants.SOCKET_IP_PORT);
		}

	}
}
