package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.playersPanel;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * This panel contanins the data about all the players that take part in a game.
 * 
 * @author Andrea
 * 
 */
public class PlayersPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -200102214997913078L;
	private GridLayout layout;
	// this arraylist contains the PlayersData of the player of a game (it's a
	// variable number and therefore it's used an arraylist)
	private List<PlayerData> players = new ArrayList<PlayerData>();

	/**
	 * The constructor takes as input the number of players that will
	 * partecipate in the match and creates the needed slots to host players'
	 * data.
	 * 
	 * The grid is set for the maximum number of player.
	 */
	public PlayersPanel() {
		super();

		layout = new GridLayout(GameConstants.MAX_PLAYERS_IN_A_GAME, 1);

		this.setLayout(layout);

		setBackground(GuiConstants.COLORGAMECONSOLE);
	}

	/**
	 * This method adds a player to the PlayersPanel
	 */
	public void addPlayerToPlayersPanel(String namePlayer, Player player,
			Color playerColor) {

		players.add(new PlayerData(namePlayer, player, playerColor));
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

					panel.setBorder(BorderFactory.createLineBorder(
							GuiConstants.COLORGAMECONSOLE, 0));
					this.validate();
					this.repaint();
				}

			}
		}

	}

	/** Get the arrayList of playerDatas */
	public List<PlayerData> getPlayers() {
		return players;
	}

	/** Get the player data panel coresponding to a specific player */
	public PlayerData getPlayerDisplayedData(Player player) {
		for (PlayerData playerData : players) {
			if (player.equals(playerData.getPlayer())) {
				return playerData;
			}
		}
		return null;
	}

	/**
	 * Set the money for a player
	 * 
	 * @param player
	 *            The player to modify
	 * @param money
	 *            The new money
	 */
	public void setMoneyToPlayer(Player player, int money) {
		getPlayerDisplayedData(player).getMoneyPlayer().setMoneyPlayer(money);
	}
}
