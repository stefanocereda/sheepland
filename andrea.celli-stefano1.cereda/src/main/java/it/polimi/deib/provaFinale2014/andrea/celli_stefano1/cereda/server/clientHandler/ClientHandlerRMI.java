package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.RMIInterface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The rmi version of a client handler. This class will be deployed on the
 * server and as it starts it will search for a remote object representing the
 * client's network handler. As the game manager asks this object to perform an
 * action this object will invoke the clien't correspondent method
 * 
 * @author Stefano
 * 
 */
public class ClientHandlerRMI extends ClientHandler {
    /** The remote object */
    private RMIInterface clientObject;

    /**
     * The constructor takes as input the reference of the server starter that
     * will handle reconnection for the clientand the client created object.
     * Differently from the socket version we don't ask for the client's id as
     * that operation is already done by the so called RMIConnector. Differently
     * from the socket version the methods aren't synchronized because there
     * won't be problems to ping while asking a move
     * 
     * @param token
     * 
     * @param registry
     * @throws NotBoundException
     * @throws RemoteException
     * @throws AccessException
     */
    public ClientHandlerRMI(ServerStarter serverStarter, RMIInterface client,
            int token) throws RemoteException {
        super(serverStarter);
        clientObject = client;
        id = token;
    }

    /** {@inheritDoc} */
    public Move askMove() throws ClientDisconnectedException {
        try {
            return clientObject.getMove();
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public void executeMove(Move moveToExecute)
            throws ClientDisconnectedException {
        try {
            clientObject.executeMove(moveToExecute);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public Move sayMoveIsNotValid() throws ClientDisconnectedException {
        try {
            return clientObject.notifyNotValidMove();
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public void sendNewStatus(BoardStatus newStatus)
            throws ClientDisconnectedException {
        try {
            clientObject.updateStatus(newStatus);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public Road askInitialPosition() throws ClientDisconnectedException {
        try {
            return clientObject.askInitialPosition();
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public Road askSecondInitialPosition() throws ClientDisconnectedException {
        try {
            return clientObject.askSecondInitialPosition();
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public void pingTheClient() throws ClientDisconnectedException {
        try {
            clientObject.ping();
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public void setCurrentPlayer(Player newCurrentPlayer)
            throws ClientDisconnectedException {
        try {
            clientObject.setCurrentPlayer(newCurrentPlayer);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public void sendWinners(List<Player> winners)
            throws ClientDisconnectedException {
        try {
            clientObject.sendWinners(winners);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public void notifyControlledPlayer(Player controlled)
            throws ClientDisconnectedException {
        try {
            clientObject.notifyControlledPlayer(controlled);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public boolean chooseShepherd() throws ClientDisconnectedException {
        try {
            return clientObject.chooseShepherd();
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public void sendShepherd(boolean usingSecond)
            throws ClientDisconnectedException {
        try {
            clientObject.notifyShepherd(usingSecond);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public List<MarketOffer> askMarketOffers()
            throws ClientDisconnectedException {
        try {
            return clientObject.askMarketOffers();
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }

    /** {@inheritDoc} */
    public List<MarketBuy> askMarketBuy(List<MarketOffer> offers)
            throws ClientDisconnectedException {
        try {
            return clientObject.askMarketBuy(offers);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(gameController,
                    controlledPlayer, e);
        }
    }
}
