package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.playersPanel;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;

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

	private GridLayout layout = new GridLayout(2, 2);
	// the label containing the name of the player
	private NamePlayer name;
	// the label containing the money of the player
	private MoneyPlayer money;
	// the label containing the cards owned by the player
	private CardsPlayer cards;

	/**
	 * The constructor creates the label containing the name, the money owned
	 * and the card owned.
	 * 
	 * @param playerName
	 * @param initialMoney
	 * @param initialCard
	 */
	public PlayerData(String playerName, int initialMoney, Card initialCard,
			Color playerColor) {

		this.setLayout(layout);

		setBackground(playerColor);

		// create and add the label for the name
		name = new NamePlayer(playerName, playerColor);
		this.add(name);

		// create the label for the money and adds it
		money = new MoneyPlayer(initialMoney, playerColor);
		this.add(money);

		// create the label for the cards and add it
		cards = new CardsPlayer(initialCard, playerColor);
		this.add(cards);

	}

	public NamePlayer getNamePlayer() {
		return name;
	}

	public MoneyPlayer getMoneyPlayer() {
		return money;
	}

	public CardsPlayer getCardsPlayer() {
		return cards;
	}

}
