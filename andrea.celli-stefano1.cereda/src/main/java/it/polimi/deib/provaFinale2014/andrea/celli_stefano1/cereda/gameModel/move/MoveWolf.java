/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Wolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

/**
 * This class represent the movement of a wolf. The wolf automatically moves
 * after each turn, it goes in an adjacent terrain passing on the road indicated
 * by a dice if the road is without gates. If all the roads are covered by gates
 * it can jump. If in the new Terrain there are some sheeps it randomly choose
 * one and kill it.
 * 
 * @author Stefano
 * 
 */
public class MoveWolf extends Move {
    private static final long serialVersionUID = -1432614147471630720L;

    /** The specific wolf moved */
    private Wolf wolf;
    /** The new terrain of the wolf */
    private Terrain newPosition;
    /** The killed sheep */
    private Sheep killedSheep;

    /**
     * @param wolf
     *            The moved wolf
     * @param newPosition
     *            The new terrain of the wolf
     * @param sheepToKill
     *            The sheep eaten by the wolf
     */
    public MoveWolf(Wolf wolf, Terrain newPosition, Sheep sheepToKill) {
        this.wolf = wolf;
        this.newPosition = newPosition;
        this.killedSheep = sheepToKill;
    }

    /**
     * @return the wolf
     */
    public Wolf getWolf() {
        return wolf;
    }

    /**
     * @return the newPosition of the wolf
     */
    public Terrain getNewPosition() {
        return newPosition;
    }

    /** @return the killed sheep */
    public Sheep getKilledSheep() {
        return killedSheep;
    }

    @Override
    public String toString() {
        String message = "The wolf goes in the terrain " + newPosition;

        if (killedSheep != null) {
            message += " and eats a sheep.";
        }

        return message;
    }

    @Override
    public boolean isValid(BoardStatus boardStatus) {
        return RuleChecker.isValidMoveWolf(this,
                (BoardStatusExtended) boardStatus);
    }

    @Override
    public void execute(BoardStatus boardStatus) {
        ExecuteAction.executeMoveWolf(this, (BoardStatusExtended) boardStatus);
    }
}
