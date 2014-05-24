package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;

public class NetworkHandlerRMI extends NetworkHandler {

	public NetworkHandlerRMI(GameControllerClient gameController) {
		super(gameController);
	}

	public void ping() {
		// empty method
	}

}
