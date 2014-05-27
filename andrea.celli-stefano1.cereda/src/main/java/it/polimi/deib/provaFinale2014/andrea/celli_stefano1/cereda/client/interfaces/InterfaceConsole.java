package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

import java.util.ArrayList;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

public class InterfaceConsole implements Interface {

	public Move getNewMove() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setReferencetoStatus(BoardStatus boardStatus) {
		// TODO Auto-generated method stub
		
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

}
