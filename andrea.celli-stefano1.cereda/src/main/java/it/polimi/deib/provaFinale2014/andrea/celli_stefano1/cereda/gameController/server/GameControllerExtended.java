package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Wolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.AdvancedPlayerAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveWolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Dice;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;

/**
 * This is the controller for a game with advanced rules
 * 
 * @author stefano
 * 
 */
public class GameControllerExtended extends GameController {
	/** A logger */
	private static final Logger LOGGER = Logger
			.getLogger(GameControllerExtended.class.getName());

	public GameControllerExtended(List<ClientHandler> playerClients) {
		super(playerClients);
	}

	/**
	 * Initialize the ruleChecker, the moveCostCalculator, the moveExecutor and
	 * the board. Send this initial status to all the players. This method is
	 * overridden in order to create an extended boardStatus
	 */
	@Override
	protected void initializeAll() {
		boardStatus = new BoardStatusExtended(clients.size());

		// initialize the board
		initBoard();
	}

	/**
	 * Initialize the board following the rules on the second page
	 * (Preparazione). We also create a wolf
	 */
	@Override
	protected void initBoard() {
		addPlayersToGame();
		initSheeps();
		initBlackSheep();
		initWolf();
		initCards();
		deleteRemainingInitialCards();
		chooseFirstPlayer();
	}

	/**
	 * Create all the sheep and put them on the map, one per terrain excluding
	 * sheepsburg. It's overridden because we have to choose random types of
	 * sheep
	 */
	@Override
	protected void initSheeps() {
		for (Terrain terrain : Terrain.values()) {
			if (!terrain.equals(Terrain.SHEEPSBURG)) {
				TypeOfSheep type = TypeOfSheep.getRandomTypeOfSheep();
				int age = 0;
				if (!type.isNormal()) {
					age = 2;
				}
				Sheep sheep = new Sheep(age, type, terrain);
				sheep.setID();
				boardStatus.addSheep(sheep);
			}
		}
	}

	/** Create a wolf, set his id, put it in Sheepsburg and add it to the board */
	private void initWolf() {
		Wolf wolf = new Wolf(Terrain.SHEEPSBURG);
		wolf.setID();
		((BoardStatusExtended) boardStatus).addWolfToBoardStatus(wolf);
	}

	//
	//
	// HERE STARTS GAME MANAGING
	//
	//

	/**
	 * This method notifies the current player to all the clients, then goes on
	 * by moving the wolf or by asking a move to the current player. It is
	 * overridden because after a complete turn we move the wolf before going to
	 * the first player.
	 */
	@Override
	public String notifyNewCurrentPlayer() {
		sendNewCurrentPlayerToAllPlayers();
		if (boardStatus.getCurrentPlayer().equals(boardStatus.getFirstPlayer())) {
			return "moveTheWolf";
		}
		return "retrieveMoveFromCurrentPlayer";
	}

	/**
	 * This method rolls the dice and consequently creates a moveWolf move. If
	 * it is possible it validates, executes and sends to client the move.
	 * 
	 * @return manageMarket
	 */
	public String moveTheWolf() {
		String toReturn = "manageMarket";
		Dice dice = Dice.create();
		int roadNum = dice.roll(GameConstants.NUMBER_OF_DICE_SIDES);
		Wolf wolf = ((BoardStatusExtended) boardStatus).getWolf();

		// now search the road adjacent to the wolf that has the wanted number
		Road linkingRoad = null;
		Terrain wolfPosition = wolf.getPosition();
		for (Road r : boardStatus.getRoadMap().findRoadsAdjacentToATerrain(
				wolfPosition)) {
			if (r.getBoxValue() == roadNum) {
				linkingRoad = r;
				break;
			}
		}

		// if linkingRoad is null go on (it happens near the sea)
		if (linkingRoad == null) {
			return toReturn;
		}

		// if we have found a road search the new terrain
		Terrain newPosition = null;
		for (Terrain t : linkingRoad.getAdjacentTerrains()) {
			if (!t.equals(wolfPosition)) {
				newPosition = t;
				break;
			}
		}

		// if newPosition is null (should be impossible) log an error and go on
		if (newPosition == null) {
			LOGGER.log(Level.SEVERE,
					"There are errors in the method moveTheWolf");
			return toReturn;
		}

		// now randomly select a sheep in the new terrain
		List<Sheep> availableVictims = new ArrayList<Sheep>();
		for (Sheep s : boardStatus.getSheeps()) {
			if (s.getPosition().equals(newPosition)) {
				availableVictims.add(s);
			}
		}
		
		if (availableVictims.size() == 0){
			return toReturn;
		}
		
		int victimNumber = dice.roll(availableVictims.size()) - 1;
		Sheep sacrificialLamb = availableVictims.get(victimNumber);

		// create the move and try to execute
		MoveWolf mw = new MoveWolf(wolf, newPosition, sacrificialLamb);
		if (mw.isValid(boardStatus)) {
			executeMove(mw);
		}

		return toReturn;
	}

	/**
	 * This method has to be invoked after the moves session. It searches for
	 * the next player that has to play and state if the game is finished or
	 * not. If the game is over it communicates it to the caller using the
	 * string "gameOver", otherwise "notifyNewCurrentPlayer". It is overridden
	 * because after each player's turn we increase the age of all the sheeps
	 * and promotes the old lamb to male or female sheep
	 */
	@Override
	public String goOn() {
		increaseAgeOfSheep();
		promoteOldLambs();
		return super.goOn();
	}

	/** Increase by 1 the age of all the sheeps */
	private void increaseAgeOfSheep() {
		for (Sheep s : boardStatus.getSheeps()) {
			s.ageIcrement();
		}
	}

	/** Search the old lambs and promotes them to advanced sheep */
	private void promoteOldLambs() {
		for (Sheep s : boardStatus.getSheeps()) {
			if (s.isBasicType() && s.getAge() >= GameConstants.AGE_OF_OLD_LAMBS) {
				s.setNewRandomAndvancedTypeOfSheep();
			}
		}
	}

	/**
	 * This method execute locally the move and notifies the clients. It is
	 * overridden to handle the advanced moves like mating and butchering, they
	 * involve randomness therefore they're executed locally by the server and
	 * the we sends back to the client the entire boardstatus
	 */
	@Override
	public void executeMove(Move moveToExecute) {
		if (!AdvancedPlayerAction.class.isInstance(moveToExecute)) {
			super.executeMove(moveToExecute);
		} else {
			moveToExecute.execute(boardStatus);
			sendStatusToAllPlayers();
		}
	}

	//
	//
	// HERE STARTS THE MARKET
	//
	//

	/**
	 * This is the method that handles all the market phase. It starts by asking
	 * all players (from the first) to send the cards that they wants to trade,
	 * then chooses a random player and starting from him asks all the players
	 * which cards they want to buy
	 */
	public String manageMarket() {
		List<MarketOffer> offers = askMarketOffersToAllClients();

		letThePlayersBuy(offers);

		sendStatusToAllPlayers();

		return "moveTheBlackSheep";
	}

	/**
	 * This method asks all the clients (from the first) to send a list of card
	 * that they wants to sell
	 */
	private List<MarketOffer> askMarketOffersToAllClients() {
		List<MarketOffer> offered = new ArrayList<MarketOffer>();

		Iterator<Player> it = boardStatus.getPlayersIterator();

		while (it.hasNext()) {
			Player current = it.next();
			ClientHandler ch = searchClientHandler(current);
			List<MarketOffer> clientoffers = askMarketOffersToClient(ch);
			if (clientoffers != null) {
				setIdToAllOffers(clientoffers);
				offered.addAll(clientoffers);
			}
		}
		return offered;
	}

	/** This method sets the id for all the market offers in the given list */
	private void setIdToAllOffers(List<MarketOffer> clientoffers) {
		for (MarketOffer mo : clientoffers) {
			mo.setID();
		}
	}

	/**
	 * This method keep asking some market offers to the given client handler
	 * until he gives valid proposal which are returned
	 */
	private List<MarketOffer> askMarketOffersToClient(ClientHandler ch) {
		List<MarketOffer> toReturn = null;

		if (!ch.getPlayer().isSuspended()) {
			try {
				do {
					toReturn = ch.askMarketOffers();
				} while (!areValidOffers(toReturn, ch.getPlayer()));
			} catch (ClientDisconnectedException e) {
				String message = "A client disconnected";
				LOGGER.log(Level.INFO, message, e);
				catchDisconnection(e.getPlayer());
			} catch (ClassNotFoundException e) {
				String message = "A client is not aligned with the communication protocol, suspending it";
				LOGGER.log(Level.INFO, message, e);
				ch.getPlayer().suspend();
				ch.getPlayer().setNotConnected();
			}
		}

		return toReturn;
	}

	/**
	 * This method scan all the passed market offers and check for their
	 * validity
	 */
	private boolean areValidOffers(List<MarketOffer> toCheck, Player offerer) {
		if (toCheck == null) {
			return true;
		}
		for (MarketOffer mo : toCheck) {
			if (!mo.isValidOffer(offerer)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method asks all the player (starting from a random one) if they
	 * wants to buy some cards from the available list and updates the status
	 */
	private void letThePlayersBuy(List<MarketOffer> offers) {
		Iterator<Player> it = boardStatus.getPlayersRandomIterator();

		while (it.hasNext()) {
			ClientHandler ch = searchClientHandler(it.next());
			if (!ch.getPlayer().isSuspended()) {
				try {
					List<MarketBuy> buy = null;

					do {
						buy = ch.askMarketBuy(offers);
					} while (!areValidBuy(buy, offers, ch.getPlayer()));

					setIdToAllBuy(buy);
					executeAllMarketBuy(buy, offers);
				} catch (ClientDisconnectedException e) {
					String message = "A client disconnected";
					LOGGER.log(Level.INFO, message, e);
					catchDisconnection(e.getPlayer());
				} catch (ClassNotFoundException e) {
					String message = "A client is not aligned with the communication protocol, suspending it";
					LOGGER.log(Level.INFO, message, e);
					ch.getPlayer().suspend();
					ch.getPlayer().setNotConnected();
				}
			}
		}
	}

	/** Set the id to all given market buy */
	private void setIdToAllBuy(List<MarketBuy> buy) {
		for (MarketBuy mb : buy) {
			mb.setID();
		}
	}

	/**
	 * Check if the give List of MarketBuy contains only buy that are legal in
	 * given offers
	 */
	private boolean areValidBuy(List<MarketBuy> buy, List<MarketOffer> offers,
			Player buyer) {
		if (buy == null) {
			return true;
		}
		for (MarketBuy mb : buy) {
			if (!mb.isValidBuy(offers, buyer)) {
				return false;
			}
		}
		return true;
	}

	/** For every buy we delete the correspondent offer and trades the card */
	private void executeAllMarketBuy(List<MarketBuy> buy,
			List<MarketOffer> offers) {
		if (buy != null) {
			for (MarketBuy mb : buy) {
				mb.execute(offers, (BoardStatusExtended) boardStatus);
			}
		}
	}
}