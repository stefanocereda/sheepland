package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.GenericGameObject;

import java.io.Serializable;

/**
 * A Move represents all the type of moves available in the game and the actors
 * involved in a move. This class will be used for client/server communications,
 * for rule checking and to actually execute them.
 * 
 * Specific types of moves are built through inheritance of the class Move
 * 
 * @author Andrea Celli
 */
public abstract class Move extends GenericGameObject implements Serializable {

	/** This method checks if the move is valid */
	public abstract boolean isValid(BoardStatus boardStatus);

	/** This method execute the move on the given boardstatus */
	public abstract void execute(BoardStatus boardStatus);
}