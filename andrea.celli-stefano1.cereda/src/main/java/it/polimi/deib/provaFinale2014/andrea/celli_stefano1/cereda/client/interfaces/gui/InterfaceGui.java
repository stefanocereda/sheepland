package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.List;

public class InterfaceGui implements Interface {

	public void setReferenceToGameController(
			GameControllerClient gameControllerClient) {
		// TODO Auto-generated method stub

	}

	public void showInitialInformation() {
		// TODO Auto-generated method stub

	}

	public void notifyNewStatus() {
		// TODO Auto-generated method stub

	}

	public Road chooseInitialPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	public void notifyMove(Move move) {
		// TODO Auto-generated method stub

	}

	public Move getNewMove() {
		// TODO Auto-generated method stub
		return null;
	}

	public void notifyNotValidMove() {
		// TODO Auto-generated method stub

	}

	public void notifyCurrentPlayer(Player newCurrentPlayer) {
		// TODO Auto-generated method stub

	}

	public void notifyWinners(List<Player> winners) {
		// TODO Auto-generated method stub

	}

	public void notifyDisconnection() {
		// TODO Auto-generated method stub

	}

	public boolean chooseShepherd() {
		// TODO Auto-generated method stub
		return false;
	}
}