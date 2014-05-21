/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;

import java.net.InetSocketAddress;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class ClientIdentifierTest {

	@Test
	public void testClientIdentifier() {
		InetSocketAddress addr = new InetSocketAddress("localhost",
				Costants.SOCKET_IP_PORT);

		ClientIdentifier id = new ClientIdentifier(addr.getAddress(),
				addr.getPort());

		assertNotNull(id);
		assertEquals(addr.getAddress(), id.getInetAddress());
		assertEquals(addr.getPort(), id.getidentifier());

		ClientIdentifier id2 = new ClientIdentifier(addr.getAddress(),
				addr.getPort());
		ClientIdentifier id3 = new ClientIdentifier(addr.getAddress(),
				addr.getPort() + 1);

		assertFalse(id == id2);
		assertTrue(id.equals(id2));
		assertFalse(id.equals(id3));

	}

}
