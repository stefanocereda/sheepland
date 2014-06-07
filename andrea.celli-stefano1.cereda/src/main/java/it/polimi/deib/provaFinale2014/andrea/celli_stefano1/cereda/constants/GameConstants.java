/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants;

/**
 * This class contains the constants of a game
 * 
 * @author Stefano
 * 
 */
public class GameConstants {
	/** the max number of players in a games */
	public static final int MAX_PLAYERS_IN_A_GAME = 4;

	/** The number of non final gates that can be placed in a game */
	public static final int NUMBER_OF_NON_FINAL_GATES = 20;

	/** The faces of a dice */
	public static final int NUMBER_OF_DICE_SIDES = 6;

	/** The number of roads in a map */
	public static final int NUMBER_OF_ROADS = 42;

	/** The initial money of each player */
	public static final int INITIAL_MONEY = 20;

	/** The initial money when playing with only two clients */
	public static final int INITIAL_MONEY_TWO_PLAYERS = 30;

	/** The number of player's turn after which a lamb becomes an advanced sheep */
	public static final int AGE_OF_OLD_LAMBS = 2;

	/**
	 * This is the minimum score that a player has to achieve in order to be
	 * payed by a player who is doing a butchering
	 */
	public static final int MINIMUN_SCORE_IN_BUTCHERING = 5;
	
	/**
	 * The number of coins that a butcher has to give to every player that
	 * scores a value > 5
	 */
	public static final int COINS_GIVEN_IN_BUTCHERING = 2;

	/** Hide the default constructor */
	private GameConstants() {
	}
}