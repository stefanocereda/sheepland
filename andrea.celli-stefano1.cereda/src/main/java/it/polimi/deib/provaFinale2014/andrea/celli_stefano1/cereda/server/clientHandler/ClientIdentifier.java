package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import java.net.InetAddress;

/**
 * This class is used to identify a client by: his ip addres and a local
 * identifier, for the socket clients it will be the port, for rmi i still don't
 * know
 * 
 * @author Stefano
 * 
 */
public class ClientIdentifier {
	private InetAddress address;
	private int identifier;

	/**
	 * 
	 * @param addr
	 *            the client ip
	 * @param id
	 *            a client's local identifier
	 */
	public ClientIdentifier(InetAddress addr, int id) {
		address = addr;
		identifier = id;
	}

	/** get the ip address */
	public InetAddress getInetAddress() {
		return address;
	}

	/** get the identifier */
	public int getidentifier() {
		return identifier;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + identifier;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ClientIdentifier)) {
			return false;
		}
		ClientIdentifier other = (ClientIdentifier) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (identifier != other.identifier) {
			return false;
		}
		return true;
	}

}
