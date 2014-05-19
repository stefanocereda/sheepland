package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

/**
 * BoardStatus with additional rules(ex. the wolf)
 * 
 * @author Andrea
 * 
 */
public class BoardStatusExtended extends BoardStatus {
	private Wolf wolf;

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
		if (this.wolf == null)
			this.wolf = wolf;
	}
}
