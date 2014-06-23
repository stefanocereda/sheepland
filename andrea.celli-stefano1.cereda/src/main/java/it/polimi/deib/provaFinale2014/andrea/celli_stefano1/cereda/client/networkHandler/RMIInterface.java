package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This is the interface of a client's network handler. It is primarily used to
 * publish on the rmi registry the supported methods
 * 
 * @author Stefano
 */
public interface RMIInterface extends Remote {
    /**
     * This method is invoked by the server when it wants to receive a move
     * 
     * @return The move created by the client
     */
    public Move getMove() throws RemoteException;

    /**
     * This method is invoked by the server when there's a move to execute
     * 
     * @param moveToExecute
     *            The move that the server wants the client to execute
     */
    public void executeMove(Move moveToExecute) throws RemoteException;

    /**
     * This method is called by the server to say that the last move wasn't
     * valid and it's expecting a new one
     * 
     * @return A new move created by the client
     */
    public Move notifyNotValidMove() throws RemoteException;

    /**
     * This method is called by the server to send a board status to substitute
     * the old one
     * 
     * @param newStatus
     *            The new BoardStatus that the server wants the client to use in
     *            substitution of the old one
     */
    public void updateStatus(BoardStatus newStatus) throws RemoteException;

    /**
     * This method is used to choose the initial road
     * 
     * @return The initial road as chosed by the user
     */
    public Road askInitialPosition() throws RemoteException;

    /** This method is used to check connection */
    public void ping() throws RemoteException;

    /**
     * This method is called by the server to specify who is the current player
     * 
     * @param newCurrentPlayer
     *            The player that is playing this turn
     */
    public void setCurrentPlayer(Player newCurrentPlayer)
            throws RemoteException;

    /**
     * This method is used to notify the end of the game and receive the list of
     * winners
     * 
     * @param winners
     *            The list of player with the best score
     */
    public void sendWinners(List<Player> winners) throws RemoteException;

    /**
     * This method is used to let the client know which is the player that he's
     * moving
     * 
     * @param controlled
     *            the player that this client is using
     */
    public void notifyControlledPlayer(Player controlled)
            throws RemoteException;

    /**
     * This method is used to set the controlled shepherd at the beginning of a
     * turn in two players games
     * 
     * @return If the player wants to use his secon shepherd
     */
    public boolean chooseShepherd() throws RemoteException;

    /**
     * This method is used to ask the position of the second shepherd
     * 
     * @return The road that the client chosen as initial position for his
     *         second shepherd
     */
    public Road askSecondInitialPosition() throws RemoteException;

    /**
     * This method is used to let the client know which shepherd is using the
     * current player
     * 
     * @param usingSecond
     *            true if the current player is using his second shepherd
     */
    public void notifyShepherd(boolean usingSecond) throws RemoteException;

    /**
     * This method is used to ask the user to choose the cards that he wants to
     * sell
     * 
     * @return A list of MarketOffers as created by the user
     */
    public List<MarketOffer> askMarketOffers() throws RemoteException;

    /**
     * This method is used to asks the user to send a list of card that he wants
     * to buy
     * 
     * @param offers
     *            A list of available market offers
     * @return The list of market buy as created by the client
     */
    public List<MarketBuy> askMarketBuy(List<MarketOffer> offers)
            throws RemoteException;
}
