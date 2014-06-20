package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import java.awt.Color;

/**
 * This enum defines the relation between pawns,colors and name of players.
 * 
 * @author Andrea
 * 
 */
public enum Pawns {
	BLUEPAWN(Color.BLUE, "Player 1", "/pedinaBlu.png"), YELLOWPAWN(
			Color.YELLOW, "Player 2", "/pedinaGialla.png"), REDPAWN(Color.RED,
			"Player 3", "/pedinaRossa.png"), GREENPAWN(Color.GREEN, "Player 4",
			"/pedinaVerde.png");

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
