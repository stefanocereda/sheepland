package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

/**
 * This class in the analogous of TypeOfPlayerMoves for the advanced moves
 * 
 * @author stefano
 * 
 */
public enum TypeOfAdvancedPlayerMoves {
    MATING("Mating"), BUTCHERING("Butchering");

    private final String name;

    private TypeOfAdvancedPlayerMoves(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}