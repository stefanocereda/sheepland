package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController;

import java.util.ArrayList;

/**
 * A single object (to be shared between games) used to check rules
 * 
 * @author Stefano
 * 
 */
public class RuleChecker {
	/** the set of rules used by this checker */
	private GameType gameType;

	/** private constructor for singleton pattern */
	private RuleChecker(GameType type) {
		gameType = type;
	}

	/**
	 * A static array of ruleCheckers, ideally every one is a checker for a
	 * different set of rules, so that we can have multiple games with different
	 * sets of rules
	 */
	private static ArrayList<RuleChecker> ruleCheckers = new ArrayList<RuleChecker>();

	/**
	 * A singleton pattern constructor
	 * 
	 * @param gametype
	 *            The set of rules to use
	 * @return a rulechecker for the desired set of rules
	 */
	public static RuleChecker create(GameType type) {
		RuleChecker returnRC = null;

		// first of all check if we already have that type of checker
		for (int i = 0; i < ruleCheckers.size(); i++)
			if (ruleCheckers.get(i).gameType == type) {
				returnRC = ruleCheckers.get(i);
				break;
			}

		// if we don't have create a new one and add it to the list
		if (returnRC == null) {
			returnRC = new RuleChecker(type);
			ruleCheckers.add(returnRC);
		}

		// return it
		return returnRC;
	}
}
