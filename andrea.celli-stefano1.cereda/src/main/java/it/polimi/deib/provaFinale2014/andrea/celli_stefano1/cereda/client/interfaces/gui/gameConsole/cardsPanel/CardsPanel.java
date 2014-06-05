package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.gameConsole.cardsPanel;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.TerrainType;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

/**
 * This panel contains the buyable cards.
 * 
 * @author Andrea
 * 
 */
public class CardsPanel extends JPanel {

	private Map<TerrainType, SingleCardPanel> panels = new HashMap<TerrainType, SingleCardPanel>();

	private GridLayout layout = new GridLayout(2, 3, 30, 30);

	public CardsPanel() {
		setBackground(GuiConstants.COLORGAMECONSOLE);

		this.setLayout(layout);
	}

	/**
	 * This method displays a new card. It looks for the JPanel of the specific
	 * terrain type and set the new image.
	 * 
	 * @param newcard
	 */
	public void addCard(Card newCard) {

		// if the hashmap doesn't contain any panel for that type it creates and
		// add a new one
		if (!panels.containsKey(newCard.getTerrainType())) {
			// add the panel to the hashmap
			panels.put(newCard.getTerrainType(), new SingleCardPanel(newCard));
			// add the panel to the container panel
			this.add(panels.get(newCard.getTerrainType()));
		} else {
			// get the panel for that type of terrain and ask to update the
			// image
			panels.get(newCard.getTerrainType()).upDate(newCard);
		}
	}

	/**
	 * This method removes the JPanel when cards of that terrain type are
	 * finished.
	 * 
	 * @param terrainType
	 */
	public void removeSingleCardPanel(TerrainType type) {
		this.remove(panels.get(type));
	}

}
