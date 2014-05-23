package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandlerSocket;

import java.util.ArrayList;

/** An ArrayList of ClientHandlerSocket */
public class ListOfClientHandlerSocket extends ArrayList<ClientHandlerSocket>
		implements ListOfClientHandler {

	public void add(ClientHandler acceptedHandler) {
		this.add(acceptedHandler);
	}

	public ClientHandler[] values() {
		return this.values();
	}

	public void set(int i, ClientHandler newClienthandler) {
		this.set(i, newClienthandler);
	}
}
