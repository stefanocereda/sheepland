package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.playersPanel;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * This panel contanins the data about all the players that take part in a game.
 * 
 * @author Andrea
 * 
 */
public class PlayersPanel extends JPanel {

	private GridLayout layout;
	// this arraylist contains the PlayersData of the player of a game (it's a
	// variable number and therefore it's used an arraylist)
	private ArrayList<PlayerData> players = new ArrayList<PlayerData>();

	/**
	 * The constructor takes as input the number of players that will
	 * partecipate in the match and creates the needed slots to host players'
	 * data.
	 * 
	 * @param numberOfPlayers
	 */
	public PlayersPanel(int numberOfPlayers) {
		super();

		layout = new GridLayout(numberOfPlayers, 1);

		this.setLayout(layout);

		setBackground(GuiConstants.COLORGAMECONSOLE);
	}

	/**
	 * This method adds a player to the PlayersPanel
	 */
	public void addPlayerToPlayersPanel(String namePlayer, int money,
			Card initialCard, Color playerColor) {

		players.add(new PlayerData(namePlayer, money, initialCard, playerColor));
		this.add(players.get(players.size() - 1));
	}

	/**
	 * This method mark a player as the current player adding a border to its
	 * slot and delete the border from the previous player's panel.
	 * 
	 * @param the
	 *            name of the CurrentPlayer
	 */
	public void markAsCurrentPlayer(String currentPlayer) {

		// looks for the right PlayerData JPanel
		for (PlayerData panel : players) {
			if (currentPlayer.equals(panel.getNamePlayer().getNamePlayer())) {
				panel.setBorder(BorderFactory.createLineBorder(Color.WHITE,
						GuiConstants.BORDERTHICKNESS));
			} else {
				// delete the border from the old currentPlayer
				if (panel.getBorder() != null) {
					//
					//
					// does it work??
					//
					//
					panel.setBorder(BorderFactory.createLineBorder(
							GuiConstants.COLORGAMECONSOLE, 0));
				}

			}
		}

	}

	public ArrayList<PlayerData> getPlayers() {
		return players;
	}
}
