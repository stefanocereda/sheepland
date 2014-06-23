package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import java.awt.Color;

/**
 * This enum defines the relation between pawns,colors and name of players.
 * 
 * @author Andrea
 * 
 */
public enum Pawns {
    REDPAWN(Color.RED, "Player 1", "/pedinaRossa.png"), YELLOWPAWN(
            Color.YELLOW, "Player 2", "/pedinaGialla.png"), GREENPAWN(
            Color.GREEN, "Player 3", "/pedinaVerde.png"), BLUEPAWN(Color.BLUE,
            "Player 4", "/pedinaBlu.png");

    private Color pawnColor;
    private String playerName;
    private String imagePath;

    private Pawns(Color pawnColor, String playerName, String imagePath) {
        this.pawnColor = pawnColor;
        this.imagePath = imagePath;
        this.playerName = playerName;
    }

    public Color getPawnColor() {
        return pawnColor;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getImagePath() {
        return imagePath;
    }

}
