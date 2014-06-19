package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.rmi.RemoteException;
import java.util.List;

/**
 * This is the implementation of RMIInterface, for every method calls the
 * equivalent on the client game controller. For the methods that doesn't need
 * to wait for user input we create separate threads, in this way we can go back
 * to the server. Note that this workaround is not needed in the socket, as the
 * server does an out.flush() and then goes on with its job
 * 
 * @author Stefano
 * 
 */
public class RMIImpl implements RMIInterface {
	private GameControllerClient controller;

	public RMIImpl(GameControllerClient controller) {
		this.controller = controller;
	}

	public Move getMove() throws RemoteException {
		return controller.getNewMove();
	}

	public void executeMove(Move moveToExecute) throws RemoteException {
		SendMove sm = new SendMove(moveToExecute);
		(new Thread(sm)).start();
	}

	/** This class is used to send a move to the interface on a separate thread */
	private class SendMove implements Runnable {
		private Move move;

		public SendMove(Move move) {
			this.move = move;
		}

		public void run() {
			controller.executeMove(move);
		}
	}

	public Move notifyNotValidMove() throws RemoteException {
		controller.notifyNotValidMove();
		return controller.getNewMove();
	}

	public void updateStatus(BoardStatus newStatus) throws RemoteException {
		SendStatus ss = new SendStatus(newStatus);
		(new Thread(ss)).start();
	}

	/**
	 * This class is used to send a new status to the interface on a separate
	 * thread
	 */
	private class SendStatus implements Runnable {
		private BoardStatus status;

		public SendStatus(BoardStatus status) {
			this.status = status;
		}

		public void run() {
			controller.upDateStatus(status);
		}
	}

	public void setCurrentPlayer(Player newCurrentPlayer)
			throws RemoteException {
		SetCurrentPlayer scp = new SetCurrentPlayer(newCurrentPlayer);
		(new Thread(scp)).start();
	}

	/**
	 * This class is used to notify the current player to the interface on a
	 * separate thread
	 */
	private class SetCurrentPlayer implements Runnable {
		private Player p;

		public SetCurrentPlayer(Player p) {
			this.p = p;
		}

		public void run() {
			controller.setCurrentPlayer(p);
		}
	}

	public void sendWinners(List<Player> winners) throws RemoteException {
		SendWinners sw = new SendWinners(winners);
		(new Thread(sw)).start();
		controller.notifyWinners(winners);

		// TODO handle closing connection
	}

	/**
	 * This class is used to notify to the interface the list of winners on a
	 * separate thread
	 */
	private class SendWinners implements Runnable {
		private List<Player> winners;

		public SendWinners(List<Player> w) {
			winners = w;
		}

		public void run() {
			controller.notifyWinners(winners);
		}
	}

	public void ping() throws RemoteException {
		// empty method
	}

	public Road askInitialPosition() throws RemoteException {
		return controller.chooseInitialPosition();
	}

	public void notifyControlledPlayer(Player controlled)
			throws RemoteException {
		controller.setControlledPlayer(controlled);
	}

	public boolean chooseShepherd() throws RemoteException {
		return controller.getShepherd();
	}

	public Road askSecondInitialPosition() throws RemoteException {
		return controller.chooseSecondInitialPosition();
	}

	public void notifyShepherd(boolean usingSecond) throws RemoteException {
		controller.notifyShepherd(usingSecond);
	}

	public List<MarketOffer> askMarketOffers() throws RemoteException {
		return controller.askMarketOffers();
	}

	public List<MarketBuy> askMarketBuy(List<MarketOffer> offers)
			throws RemoteException {
		return controller.askMarketBuy(offers);
	}
}
