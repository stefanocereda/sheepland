package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * A class for the move Mating 2. It indicates the player doing the mating and
 * the terrain wanted for the mating
 * 
 * @author stefano
 */

public class Mating extends AdvancedPlayerAction {
    private static final long serialVersionUID = -3416933122959236568L;
    /** The terrain where this mating takes place */
    private Terrain terrain;

    /**
     * 
     * @param player
     *            The player performing this action
     * @param terrain
     *            The terrain containing the mating sheeps
     */
    public Mating(Player player, Terrain terrain) {
        super(player);
        this.terrain = terrain;
    }

    /** @return the terrain where this action is taking place */
    public Terrain getTerrain() {
        return terrain;
    }

    @Override
    public boolean isValid(BoardStatus boardStatus) {
        return RuleChecker.isValidMating(this, boardStatus);
    }

    @Override
    public void execute(BoardStatus boardStatus) {
        BoardStatusExtended bs = (BoardStatusExtended) boardStatus;
        ExecuteAction.executeMating(this, bs);
    }
}
