package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.commandLineInterface;

/**
 * This class contains a static method taht calls System.out.println() It's only
 * used to avoid a lot of sonar issues in the console interface
 * 
 * @author stefano
 * 
 */
public class Printer {
	/**
	 * Print on the console the given message
	 * 
	 * @param msg
	 *            The object to print (.toString will be called)
	 */
	public static void println(Object msg) {
		System.out.println(msg);
	}
	
	/**Print an empty line*/
	public static void println(){
		println("");
	}
}
