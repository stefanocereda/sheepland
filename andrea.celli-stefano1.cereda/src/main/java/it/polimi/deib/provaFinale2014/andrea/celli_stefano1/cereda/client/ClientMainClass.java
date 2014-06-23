/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.InterfaceCreator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.TypeOfInterface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.commandLineInterface.Printer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkHandlerRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkHandlerSocket;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.NetworkConstants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main class of the client, asks for RMI/socket and creates a network
 * manager linked to a client game controller
 * 
 * @author Stefano
 * 
 */
public class ClientMainClass {
    /** A logger */
    private static final Logger LOGGER = Logger
            .getLogger("client.ClientMainClass");
    /** The console scanner */
    static Scanner in = new Scanner(System.in);

    /** Hide the default constructor */
    private ClientMainClass() {
    }

    /**
     * The main method of a client
     * 
     * @param args
     *            You can use this parameter to describe the kind of client
     *            wanted. You can insert "socket" or "rmi" for the connections
     *            and "console", "gui" or "fake" for the interfaces
     */
    public static void main(String[] args) {
        // parse param
        TypeOfInterface userInterface = null;
        int network = 0;
        int token = -1;

        for (String arg : args) {
            if ("socket".equals(arg)) {
                network = 1;
            } else if ("rmi".equals(arg)) {
                network = 2;
            } else if ("console".equals(arg)) {
                userInterface = TypeOfInterface.CONSOLE;
            } else if ("gui".equals(arg)) {
                userInterface = TypeOfInterface.GUI;
            } else {
                try {
                    token = Integer.parseInt(arg);
                } catch (NumberFormatException e) {
                    LOGGER.log(Level.INFO, "The input given is not valid", e);
                }
            }
        }

        if (userInterface == null) {
            userInterface = askUserInterface();
        }
        if (network == 0) {
            network = askNetwork();
        }
        if (token == -1) {
            Printer.println("Insert a previous token if you have one, 0 otherwise: ");
            token = Integer.parseInt(in.nextLine());
        }
        if (token == -1) {
            token = 0;
        }

        // create the interface
        Interface ux = InterfaceCreator.create(userInterface);

        // create the game controller
        GameControllerClient gameController = new GameControllerClient(ux);

        // launch the network handler
        if (network == 1) {
            try {
                launchSocket(gameController, token);
            } catch (IOException e) {
                String message = "Unable to start Socket connection";
                LOGGER.log(Level.SEVERE, message, e);
            }
        } else {
            try {
                launchRMI(gameController, token);
            } catch (RemoteException e) {
                String message = "Unable to start rmi connection";
                LOGGER.log(Level.SEVERE, message, e);
            } catch (NotBoundException e) {
                String message = "Unable to start rmi connection";
                LOGGER.log(Level.SEVERE, message, e);
            }
        }
    }

    /**
     * Ask the user to choose the kind of interface to launch
     * 
     * @return The TypeOfInterface selected
     */
    private static TypeOfInterface askUserInterface() {
        String answer;

        do {
            Printer.println("Choose the interface type:");
            Printer.println("1 - Console");
            Printer.println("2 - Gui");
            Printer.println("Insert answer:");
            answer = in.nextLine();
        } while (!"1".equals(answer) && !"2".equals(answer));

        if ("1".equals(answer)) {
            return TypeOfInterface.CONSOLE;
        } else {
            return TypeOfInterface.GUI;
        }
    }

    /**
     * Ask the user to choose between socket and rmi
     * 
     * @return 1 for socket; 2 for RMI
     */
    private static int askNetwork() {
        String answer;

        do {
            Printer.println("Choose the network type:");
            Printer.println("1 - Socket");
            Printer.println("2 - RMI");
            Printer.println("Insert answer:");
            answer = in.nextLine();
        } while (!"1".equals(answer) && !"2".equals(answer));

        if ("1".equals(answer)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * This method launches the rmi version of a client.
     */
    private static void launchRMI(GameControllerClient gcc, int token)
            throws RemoteException, NotBoundException {
        NetworkHandlerRMI rmiClient = new NetworkHandlerRMI(gcc, token);
        rmiClient.connect();
    }

    /**
     * This method launches the socket version of a client, it connects to the
     * server and creates a network handler.
     */
    private static void launchSocket(GameControllerClient gcc, int token)
            throws IOException {
        /** The server address */
        InetSocketAddress serverAddress = NetworkConstants.SERVER_SOCKET_ADDRESS;

        NetworkHandlerSocket socketClient;

        socketClient = new NetworkHandlerSocket(serverAddress, gcc, token);
        socketClient.start();
    }
}
