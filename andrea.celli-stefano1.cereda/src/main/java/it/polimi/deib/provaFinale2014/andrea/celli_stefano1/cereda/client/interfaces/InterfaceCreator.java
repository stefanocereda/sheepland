/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

/**
 * @author Stefano
 * 
 */
public class InterfaceCreator implements Interface {

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
