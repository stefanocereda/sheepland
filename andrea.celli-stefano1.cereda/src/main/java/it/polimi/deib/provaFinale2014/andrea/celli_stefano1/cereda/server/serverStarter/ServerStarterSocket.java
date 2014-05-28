package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandlerSocket;

/**
 * A socket server, it accepts new connections, create a ClienthandlerSocket and
 * give it back to the ServerStarter
 * 
 * @author Stefano
 * 
 */
public class ServerStarterSocket implements Runnable {
	/** A reference to the server starter that created this socket server */
	private ServerStarter creator;
	/** The ip port of the server */
	private int port;
	/** the server socket for income connections */
	private ServerSocket serverSocket = null;
	/** This variable is used to stop the server */
	private boolean running = false;
	/** A logger */
	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * The constructor of a server socket only saves the parameters passed,
	 * without actually starting the server
	 * 
	 * @param port
	 *            The IP port on wich start the socket server
	 * @param serverCreator
	 *            the object that is creating this server: will be used to
	 *            notify connecting clients
	 */
	public ServerStarterSocket(int port, ServerStarter serverCreator) {
		this.port = port;
		this.creator = serverCreator;
	}

	/**
	 * This method starts the server and begin listening to incoming
	 * connections, when detects a new one creates a proper client handler and
	 * notifies it back to the main server
	 */
	public void run() {
		try {
			// opens the socket for incoming connections
			serverSocket = new ServerSocket(port);

			// start waiting for connections
			running = true;

			while (running) {
				// accept a connection
				Socket acceptedSocket = serverSocket.accept();
				// create a client handler
				ClientHandlerSocket acceptedHandler = new ClientHandlerSocket(
						creator, acceptedSocket);
				// give it back to the server
				creator.addClient(acceptedHandler);
			}

			// If we are here the server has been closed
			serverSocket.close();
		} catch (IOException e) {
			String message = "Problems in the socket server";
			logger.log(Level.SEVERE, message, e);
		}
	}

	/** This method closes the socket server */
	public void stop() {
		running = false;
	}
}
