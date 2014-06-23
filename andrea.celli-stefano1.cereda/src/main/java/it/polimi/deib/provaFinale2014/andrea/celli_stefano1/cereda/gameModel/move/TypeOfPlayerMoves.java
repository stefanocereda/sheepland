package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

/**
 * This class defines the types of move that can be executed in a game. It's
 * used in InterfaceConsole to show the options to a player.
 * 
 * @author Andrea
 * 
 */
public enum TypeOfPlayerMoves {
	MOVEPLAYER("move player"), MOVESHEEP("move sheep"), BUYCARD("buy card");

	private final String name;

	private TypeOfPlayerMoves(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
