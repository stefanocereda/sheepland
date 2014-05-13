package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

public interface ClientHandler {
	public Move getNextMove();

	public void sendErrorMessage();

	public void sendNewStatus(BoardStatus newStatus);
}
