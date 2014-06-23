package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * AdvancedPlayerAction represents advanced moves. They are different from
 * normal moves because in their execution is involved randomness, therefore
 * they are executed only by the server that sends the modified status to all
 * the clients. This class is used to and consequently distinguish advanced
 * moves via instancef
 */
public abstract class AdvancedPlayerAction extends PlayerAction {

    private static final long serialVersionUID = -6886358099734813561L;

    public AdvancedPlayerAction(Player player) {
        super(player);
    }

}
