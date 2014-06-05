package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.cardsPanel.CardsPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.playersPanel.PlayersPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;

import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * This JPanel contains the information about players and roads and the move
 * buttons.
 * 
 * @author Andrea
 * 
 */
public class GameConsole extends JPanel {

	private PlayersPanel playersPanel;
	private CardsPanel cardsPanel;
	private ButtonsPanel buttonsPanel;

	private GridLayout layout = new GridLayout(3, 1, 10, 10);

	/**
	 * @param numbersOfPlayer
	 */
	public GameConsole(int numbersOfPlayer) {
		setBackground(GuiConstants.COLORGAMECONSOLE);

		this.setLayout(layout);

		playersPanel = new PlayersPanel(numbersOfPlayer);
		this.add(playersPanel);

		cardsPanel = new CardsPanel();
		this.add(cardsPanel);

		buttonsPanel = new ButtonsPanel();
		this.add(buttonsPanel);

		setVisible(true);
	}

	public PlayersPanel getPlayersPanel() {
		return playersPanel;
	}

	public CardsPanel getCardsPanel() {
		return cardsPanel;
	}

}
