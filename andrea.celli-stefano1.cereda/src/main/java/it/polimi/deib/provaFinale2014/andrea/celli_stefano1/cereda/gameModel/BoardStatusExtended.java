package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import java.util.HashMap;
import java.util.Map;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Wolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

/**
 * BoardStatus with additional rules(ex. the wolf)
 * 
 * @author Andrea
 * 
 */
public class BoardStatusExtended extends BoardStatus {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3691292789992923045L;
	private Wolf wolf;

	public BoardStatusExtended(int numberOfPlayers) {
		super(numberOfPlayers);
	}

	/**
	 * Returns the wolf
	 */
	public Wolf getWolf() {
		return wolf;
	}

	/**
	 * Add a wolf to the BoardStatus if it doesn't already exists
	 * 
	 * @param wolf
	 *            the wolf
	 */
	public void addWolfToBoardStatus(Wolf wolf) {
		if (this.wolf == null) {
			this.wolf = wolf;
		}
	}

	/**
	 * @param type
	 *            The type of sheep to consider (lambs/male/female)
	 * @return A map that associates to each terrain the number of sheep of the
	 *         given type contained
	 */
	public Map<Terrain, Integer> calculateNumberOfSheepForEachTerrain(TypeOfSheep type) {
		Map<Terrain, Integer> map = new HashMap<Terrain, Integer>();

		for (Terrain t : Terrain.values()) {
			map.put(t, 0);
		}
		
		for (Sheep s: sheeps){
			if (s.getTypeOfSheep().equals(type)){
				int value = map.get(s.getPosition());
				value++;
				map.put(s.getPosition(), value);
			}
		}
		
		return map;
	}
}
