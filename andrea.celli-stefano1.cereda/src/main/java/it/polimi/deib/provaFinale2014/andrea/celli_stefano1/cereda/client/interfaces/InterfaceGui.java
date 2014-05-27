package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;

public class InterfaceGui implements Interface {

	public Move getNewMove() {
		// TODO Auto-generated method stub
		return null;
	}

	public void notifyMove(Move move) {
		// TODO Auto-generated method stub

	}

	public void notifyNotValidMove() {
		// TODO Auto-generated method stub

	}

	public void notifyDisconnection() {
		// TODO Auto-generated method stub

	}

	public void notifyCurrentPlayer(Player newCurrentPlayer) {
		// TODO Auto-generated method stub

	}

	public void notifyWinners(ArrayList<Player> winners) {
		// TODO Auto-generated method stub

	}

	public void setReferenceToGameController(GameControllerClient gameController) {
		// TODO Auto-generated method stub

	}

}
