package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

//The GameController wants to to have a list of ClientHandler,
//but we call GameController by SocketServerStarter or RMIServerStarter,
//which only have SocketClientHandler or RMIClientHandler
//we can't do this by ArrayList<ClientHandler> = new ArrayList<SocketClientHandler>

public interface ListOfClientHandler {
}