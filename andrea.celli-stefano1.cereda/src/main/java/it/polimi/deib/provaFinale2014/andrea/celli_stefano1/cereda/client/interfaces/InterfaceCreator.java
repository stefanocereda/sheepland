/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.commandLineInterface.InterfaceConsole;

/**
 * This class is an interface creator
 * 
 * @author Stefano
 * 
 */
public class InterfaceCreator {

	public static Interface create(TypeOfInterface defaultInterface) {
		switch (defaultInterface) {
		case FAKE:
			return new InterfaceFake();
		case CONSOLE:
			return new InterfaceConsole();
		case GUI:
			return new InterfaceGui();
		}
		return null;
	}
}
