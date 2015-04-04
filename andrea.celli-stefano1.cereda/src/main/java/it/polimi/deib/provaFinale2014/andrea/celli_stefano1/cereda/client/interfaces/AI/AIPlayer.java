/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.AI;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.TerrainType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import java.util.List;

/**
 * This class implements an AI player. The main idea is to launch a separate
 * thread that builds up the move tree as soon as we receive the initial state.
 * By an heuristic function we give partial values and when we are asked for a
 * move we pick the current best subtree.
 *
 * @author stefano
 */
public class AIPlayer implements Interface {

    /**
     * the game controller acts as a mediator with the board status
     */
    GameControllerClient gameController;

    /**
     * {@inheritDoc}
     */
    public void setReferenceToGameController(GameControllerClient gameController) {
        this.gameController = gameController;
    }

    /**
     * Here we receive the initial status except for the initial position, so we
     * could start to decide what will be the best starting position. As this is
     * a rather simple task we don't anticipate it and it will be evaluated when
     * the positions are actually asked by the server.
     */
    public void showInitialInformation() {
    }

    //TODO: UPDATE THE TREE
    public void notifyNewStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * We pick the road that maximises our points (given the initial card)
     */
    public Road chooseInitialPosition() {
        Player me = gameController.getControlledPlayer();
        TerrainType wantedType = me.getCards().get(0).getTerrainType();

        int max = 0;
        Road best = null;

        for (Road r : gameController.getBoardStatus().getRoadMap().getHashMapOfRoads().values()) {
            int i = 0;
            for (Terrain adj : r.getAdjacentTerrains()) {
                if (adj.getTerrainType().equals(wantedType)) {
                    i++;
                }
            }

            if (i > max) {
                max = i;
                best = r;
            }
        }

        return best;
    }

    /**
     * Update the tree
     */
    public void notifyMove(Move move) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get the best move from the tree
     */
    public Move getNewMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * We should not be generating unvalid moves
     */
    public void notifyNotValidMove() {
        throw new Error("AIPlayer generated an invalid move");
    }

    /**
     * Nothing to do
     */
    public void notifyCurrentPlayer(Player newCurrentPlayer) {
    }

    /**
     * We assume that a computer takes no joy in winning a play.
     */
    public void notifyWinners(List<Player> winners) {
    }

    /**
     * Nothing to do
     */
    public void notifyDisconnection() {
    }

    /**
     * The shepherd to use should be the one that has the best subtree
     */
    public boolean chooseShepherd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * The second initial position is calculated as the first, except that it is
     * different
     */
    public Road chooseSecondInitialPosition() {
        Road first = chooseInitialPosition();

        Player me = gameController.getControlledPlayer();
        TerrainType wantedType = me.getCards().get(0).getTerrainType();

        int max = 0;
        Road best = null;

        for (Road r : gameController.getBoardStatus().getRoadMap().getHashMapOfRoads().values()) {
            if (r.equals(first)) {
                continue;
            }

            int i = 0;
            for (Terrain adj : r.getAdjacentTerrains()) {
                if (adj.getTerrainType().equals(wantedType)) {
                    i++;
                }
            }

            if (i > max) {
                max = i;
                best = r;
            }
        }

        return best;
    }

    /**
     * Nothing to do
     */
    public void notifyShepherd(boolean usingSecond) {
    }

    public List<MarketOffer> askMarketOffers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<MarketBuy> askMarketBuy(List<MarketOffer> offers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
