package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.playersPanel;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * This panel contains the data about a SINGLE player.
 * 
 * @author Andrea
 * 
 */

public class PlayerData extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5876071555465736915L;
	private GridLayout layout = new GridLayout(1, 2);
	// the label containing the name of the player
	private NamePlayer name;
	// the label containing the money of the player
	private MoneyPlayer money;
	// the player
	private Player player;

	/**
	 * The constructor creates the label containing the name and the money owned
	 * 
	 * @param playerName
	 * @param initialMoney
	 */
	public PlayerData(String playerName, Player player, Color playerColor) {

		this.setLayout(layout);

		setBackground(playerColor);

		// create and add the label for the name
		name = new NamePlayer(playerName, playerColor);
		this.add(name);

		// create the label for the money and adds it
		money = new MoneyPlayer(player.getMoney(), playerColor);
		this.add(money);

		this.player = player;

	}

	public NamePlayer getNamePlayer() {
		return name;
	}

	public MoneyPlayer getMoneyPlayer() {
		return money;
	}

	public Player getPlayer() {
		return player;
	}

}
