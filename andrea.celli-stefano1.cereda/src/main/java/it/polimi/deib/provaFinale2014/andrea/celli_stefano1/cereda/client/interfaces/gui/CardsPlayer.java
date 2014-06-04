package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.TerrainType;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

/**
 * This label displays the number cards owned by the player for each terrain
 * type
 * 
 * @author Andrea
 * 
 */

public class CardsPlayer extends JLabel {

	// An hashmap containing the number of card for each terrain type
	private Map<TerrainType, Integer> map = new HashMap<TerrainType, Integer>();

	/**
	 * The constructor takes as input the initial card of the player
	 * 
	 * @param Card
	 *            initialCard
	 */
	public CardsPlayer(Card initialCard) {
		super();
		mapInit();
		addCard(initialCard);
	}

	/**
	 * This method set the values for each terrain type in the hash map to 0
	 */
	private void mapInit() {
		map.put(TerrainType.COUNTRYSIDE, 0);
		map.put(TerrainType.DESERT, 0);
		map.put(TerrainType.LAKE, 0);
		map.put(TerrainType.MOUNTAIN, 0);
		map.put(TerrainType.PLAIN, 0);
		map.put(TerrainType.WOOD, 0);
	}

	/**
	 * This method adds a card to the map values and in the displayed string
	 * 
	 * @param the
	 *            card that has to be added
	 */
	public void addCard(Card card) {

	}

	/**
	 * This method creates the string that have to be displayed and set it as
	 * the text of the label
	 */
	private void setText() {
		String text = "Cards: c:" + map.get(TerrainType.COUNTRYSIDE) + " d:"
				+ map.get(TerrainType.DESERT) + " l:"
				+ map.get(TerrainType.LAKE) + " m:"
				+ map.get(TerrainType.MOUNTAIN) + " p:"
				+ map.get(TerrainType.PLAIN) + " w:"
				+ map.get(TerrainType.WOOD);
		this.setText(text);
	}

}
