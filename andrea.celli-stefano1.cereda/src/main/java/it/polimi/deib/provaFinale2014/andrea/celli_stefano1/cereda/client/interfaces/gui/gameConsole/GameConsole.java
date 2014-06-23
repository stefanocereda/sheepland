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

	/**
	 * 
	 */
	private static final long serialVersionUID = -7971581227330764418L;
	private PlayersPanel playersPanel;
	private CardsPanel cardsPanel;
	private ButtonsPanel buttonsPanel;

	private GridLayout layout = new GridLayout(3, 1, 10, 10);

	/**
	 * Initially the game console is empty. It's filled only when the first
	 * board status is received by the player.
	 * 
	 * @param numbersOfPlayer
	 */
	public GameConsole() {
		setBackground(GuiConstants.COLORGAMECONSOLE);

		this.setLayout(layout);

		playersPanel = new PlayersPanel();
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

	public ButtonsPanel getButtonPanel() {
		return buttonsPanel;
	}

}
