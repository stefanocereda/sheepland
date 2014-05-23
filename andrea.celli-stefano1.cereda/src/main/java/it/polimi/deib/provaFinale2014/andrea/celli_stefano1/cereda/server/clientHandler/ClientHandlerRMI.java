package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;

/**
 * This is a class that will be deployed on the client and published to the
 * server that will use this remote object to communicate with the client TODO
 */
public class ClientHandlerRMI extends ClientHandler {

	@Override
	public Move askMove() throws ClassNotFoundException,
			ClientDisconnectedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeMove(Move moveToExecute)
			throws ClientDisconnectedException {
		// TODO Auto-generated method stub

	}

	@Override
	public Move sayMoveIsNotValid() throws ClassNotFoundException,
			ClientDisconnectedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendNewStatus(BoardStatus newStatus)
			throws ClientDisconnectedException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void pingTheClient() throws ClientDisconnectedException {
		// TODO Auto-generated method stub

	}

}
