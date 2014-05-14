/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class RuleCheckerTest {

	@Test
	public void testCreate() {
		GameType gc1 = GameType.ORIGINAL;
		GameType gc2 = GameType.EXTENDED;

		RuleChecker rc1 = RuleChecker.create(gc1);
		RuleChecker rc2 = RuleChecker.create(gc1);
		RuleChecker rc3 = RuleChecker.create(gc2);
		RuleChecker rc4 = RuleChecker.create(gc2);

		assertNotNull(rc1);
		assertNotNull(rc2);
		assertNotNull(rc3);
		assertNotNull(rc4);

		assertEquals(rc1, rc2); // it's a singleton pattern
		assertEquals(rc3, rc4);

		assertNotEquals(rc1, rc3);// transitive property...
	}

}
