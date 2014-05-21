package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import java.net.InetAddress;

/**
 * This class is used to identify a client by: his ip addres, his port, the
 * player controlled
 * 
 * @author Stefano
 * 
 */
public class ClientIdentifier {
	private InetAddress address;
	private int port;

	/**
	 * 
	 * @param addr
	 *            the client ip
	 * @param port
	 *            the client port
	 */
	public ClientIdentifier(InetAddress addr, int port) {
		address = addr;
		this.port = port;
	}

	/** get the ip address */
	public InetAddress getInetAddress() {
		return address;
	}

	/** get the port */
	public int getPort() {
		return port;
	}
}
