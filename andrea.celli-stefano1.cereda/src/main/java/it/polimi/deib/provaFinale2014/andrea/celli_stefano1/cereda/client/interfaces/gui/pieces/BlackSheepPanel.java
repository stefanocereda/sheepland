package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.Map;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import java.awt.Dimension;

/**
 * This JPanel displays the black sheep
 * 
 * @author Andrea
 * 
 */
public class BlackSheepPanel extends PiecesOnTheMap {

	public BlackSheepPanel(String path, Dimension dimension) {
		super(path, dimension);
	}

	/**
	 * add a blackSheep panel to the map on a specific terrain
	 * 
	 * @param map
	 * @param terrain
	 */
	public void addToMap(Map map, Terrain terrain) {

		map.add(this);
		setLocation(map.getLinker().getBlackSheepOrigins().get(terrain));
		setVisible(true);

		// add this JPanel in the array list of panels displayed in the terrain
		map.getComponentsInTerrains().get(terrain).add(this);

	}
}
