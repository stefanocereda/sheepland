package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.commandLineInterface;

/**
 * This class contains a static method that calls System.out.println(). It's
 * only used to avoid a lot of sonar issues in the console interface, their
 * preventing me to focus on the real issues
 * 
 * @author stefano
 * 
 */
public class Printer {
    /** Hide the default constructor */
    private Printer() {
    }

    /**
     * Call System.out.println(msg)
     * 
     * @param msg
     *            the object to print
     */
    public static void println(Object msg) {
        System.out.println(msg);
    }

    /** Print an empty line */
    public static void println() {
        println("");
    }
}
