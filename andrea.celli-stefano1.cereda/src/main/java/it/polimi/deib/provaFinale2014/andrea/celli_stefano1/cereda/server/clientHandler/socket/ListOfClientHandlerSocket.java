package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.socket;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ListOfClientHandler;

import java.util.ArrayList;

/**
 * An arrayList of SocketClientHandler
 * 
 * @author Stefano
 * @see ListOfClientHandler
 */
public class ListOfClientHandlerSocket extends ArrayList<ClientHandlerSocket>
		implements ListOfClientHandler {

	public ClientHandler[] values() {
		return this.values();
	}

	public ClientHandler set(int index, ClientHandler newClientHandler) {
		return this.set(index, newClientHandler);
	}
}
