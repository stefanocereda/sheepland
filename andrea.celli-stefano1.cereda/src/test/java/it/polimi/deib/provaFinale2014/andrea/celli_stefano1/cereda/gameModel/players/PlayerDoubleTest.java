package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;

import org.junit.Test;

/**
 * Test for PlayerDouble class in the game model
 * 
 * @author Andrea
 */
public class PlayerDoubleTest {

	@Test
	public void playerDoubleTest() {
		BoardStatus bs = new BoardStatus(1);

		PlayerDouble p = new PlayerDouble();
		p.setID();
		bs.addPlayerToBoardStatus(p);

		p.setShepherd(true);

		p.move(bs.getRoadMap().getHashMapOfRoads().get(2));

		assertEquals(bs.getRoadMap().getHashMapOfRoads().get(2),
				p.getPosition());
		assertEquals(bs.getRoadMap().getHashMapOfRoads().get(2),
				p.getSecondposition());
		assertTrue(p.getShepherd());

		p.setShepherd(false);
		p.move(bs.getRoadMap().getHashMapOfRoads().get(3));
		assertEquals(bs.getRoadMap().getHashMapOfRoads().get(3),
				p.getFirstPosition());

		assertFalse(p.getShepherd());
	}

}
