package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandlerRMI;

import java.util.ArrayList;

/** An ArrayList of ClientHandlerRMI */
public class ListOfClientHandlerRMI extends ArrayList<ClientHandlerRMI>
		implements ListOfClientHandler {
	public boolean add(ClientHandler acceptedHandler) {
		return super.add((ClientHandlerRMI) acceptedHandler);
	}

	public ClientHandler[] toArray(ClientHandler[] array) {
		return super.toArray(array);
	}

	public ClientHandler set(int i, ClientHandler newClienthandler) {
		return super.set(i, (ClientHandlerRMI) newClienthandler);
	}

}
