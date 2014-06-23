package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants;

import java.net.InetSocketAddress;

/**
 * This class contains the network constants
 * 
 * @author Stefano
 * 
 */
public class NetworkConstants {
	/** the ip port for the socket server */
	public static final int SOCKET_IP_PORT = 5000;

	/** the ip port for the rmi registry */
	public static final int REGISTRY_IP_PORT = 6000;

	/** The address of the socket server as seen by the client */
	public static final InetSocketAddress SERVER_SOCKET_ADDRESS = new InetSocketAddress(
			"localhost", SOCKET_IP_PORT);

	/** The address of the rmi server registry as seen by the client */
	public static final String SERVER_RMI_ADDRESS = "localhost";

	/** Hide the default constructor */
	private NetworkConstants() {
	}
}
