package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Wolf;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<Terrain, Integer> calculateNumberOfSheepForEachTerrain(
            TypeOfSheep type) {
        Map<Terrain, Integer> map = new HashMap<Terrain, Integer>();

        for (Terrain t : Terrain.values()) {
            map.put(t, 0);
        }

        for (Sheep s : sheeps) {
            if (s.getTypeOfSheep().equals(type)) {
                int value = map.get(s.getPosition());
                value++;
                map.put(s.getPosition(), value);
            }
        }

        return map;
    }

    /**
     * This method seach in the given terrain the given type of sheep and
     * returns a sheep of that type in that terrain
     * 
     * @param terrain
     *            the terrain where search
     * @param type
     *            the type of sheep searched
     * @return a sheep in that terrain of that type, or null
     */
    public Sheep findASheep(Terrain terrain, TypeOfSheep type) {
        for (Sheep s : sheeps) {
            if (s.getPosition().equals(terrain)
                    && s.getTypeOfSheep().equals(type)) {
                return s;
            }
        }
        return null;
    }

    /**
     * This method returns to the player the list of cards he can sell during
     * the market. They are all his cards without the initial one.
     * 
     * @param player
     * @return arrayList of sellable cards
     * 
     */
    public List<Card> getSellableCards(Player player) {

        List<Card> toReturn = new ArrayList<Card>();

        for (Card card : player.getCards()) {
            if (!card.isInitial()) {
                toReturn.add(card);
            }
        }

        return toReturn;
    }
}
