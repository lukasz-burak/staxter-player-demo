package com.staxter.player.network;

import com.staxter.player.Mediator;
import com.staxter.player.Player;

public class RemoteApplication {

    private static final int PORT = 6666;

    public static void start(boolean initiator) {
        Mediator mediator = initiator ? new InitiatorMediator(PORT) : new RespondingMediator(PORT);
        Player player = new Player(getPlayerName(initiator), initiator);
        mediator.registerPlayer(player);
        if (initiator) {
            player.send("hello");
        }
    }

    private static String getPlayerName(boolean initiator) {
        return initiator ? "Initiator" : "Responder";
    }
}
