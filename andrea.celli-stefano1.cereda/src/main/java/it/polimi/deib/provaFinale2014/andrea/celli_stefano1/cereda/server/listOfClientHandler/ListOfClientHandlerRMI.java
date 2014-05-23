package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandlerRMI;

import java.util.ArrayList;

/** An ArrayList of ClientHandlerRMI */
public class ListOfClientHandlerRMI extends ArrayList<ClientHandlerRMI>
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
