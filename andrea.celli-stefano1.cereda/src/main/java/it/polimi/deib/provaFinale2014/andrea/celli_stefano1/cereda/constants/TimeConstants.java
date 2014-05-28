package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants;

/** This class contains time-related constants */
public class TimeConstants {
	/** the milliseconds between each ping to a client */
	public static final long PING_TIME = 10 * 1000;

	/** The milliseconds waiting for the client pong */
	public static final long PONG_WAITING_TIME = 10 * 1000;

	/**
	 * The milliseconds that the rmi system waits before the server tries to
	 * lookup the client's object
	 */
	public static final long RMI_WAITING_LOOKUP = 1 * 1000;

	/** The time in milliseconds waiting for the reconnection of a client */
	// public static final long WAITING_FOR_CLIENT_RECONNECT = 10 * 1000;//
	public static final long WAITING_FOR_CLIENT_RECONNECT = 1;
	// è BASSO PER VELOCIZZARE I TEST, BISOGNERà USARE QUELLO SOPRA

	/** The time in milliseconds between each reconnection test */
	public static final long WAIT_FOR_RECONNECTION = 1 * 1000;

	/** the minutes waiting for the max number of players */
	public static final int MINUTES_WAITING_FOR_MAX_PLAYERS = 5;

	/** Hide the default constructor */
	private TimeConstants() {
	}
}