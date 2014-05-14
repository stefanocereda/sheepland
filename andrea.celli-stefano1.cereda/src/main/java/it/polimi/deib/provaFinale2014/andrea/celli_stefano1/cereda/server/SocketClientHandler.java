package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;

import java.net.Socket;

/**
 * A socket version of a ClientHandler
 * 
 * TODO
 * 
 * @author Stefano
 * @see ClientHandler
 */
public class SocketClientHandler implements ClientHandler {

	public SocketClientHandler(Socket socket) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sendErrorMessage() {
		// TODO Auto-generated method stub

	}

	@Override
	public Move getNextMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendNewStatus(BoardStatus newStatus) {
		// TODO Auto-generated method stub

	}

}
