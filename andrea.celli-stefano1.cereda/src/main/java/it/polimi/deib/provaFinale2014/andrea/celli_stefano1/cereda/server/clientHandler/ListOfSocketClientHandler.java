package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import java.util.ArrayList;

/**
 * An arrayList of SocketClientHandler
 * 
 * @author Stefano
 * @see ListOfClientHandler
 */
public class ListOfSocketClientHandler extends ArrayList<SocketClientHandler>
		implements ListOfClientHandler {

	public ClientHandler[] values() {
		return this.values();
	}

	public ClientHandler set(int index, ClientHandler newClientHandler) {
		return this.set(index, newClientHandler);
	}
}
