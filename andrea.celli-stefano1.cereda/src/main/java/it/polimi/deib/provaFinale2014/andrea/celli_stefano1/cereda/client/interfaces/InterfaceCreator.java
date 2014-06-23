package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.commandLineInterface.InterfaceConsole;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.InterfaceGui;

/**
 * This class only contains a method used to create user interfaces
 * 
 * @author Stefano
 * 
 */
public class InterfaceCreator {
    /** Hide the default constructor */
    private InterfaceCreator() {
    }

    /**
     * Creates an interface of the given type
     * 
     * @param type
     *            The type of interface desidered
     * @return an interface of the given type
     */
    public static Interface create(TypeOfInterface type) {
        switch (type) {
        case CONSOLE:
            return new InterfaceConsole();
        case GUI:
            return new InterfaceGui();
        default:
            return new InterfaceConsole();
        }
    }
}
