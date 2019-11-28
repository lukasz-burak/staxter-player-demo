package com.staxter.player.local;

import com.staxter.player.Mediator;
import com.staxter.player.Player;

public class LocalApplication {
    public static void start() {
        Mediator mediator = new LocalMediator();
        Player playerOne = new Player("Initiator", true);
        Player playerTwo = new Player("Responder");
        mediator.registerPlayer(playerOne);
        mediator.registerPlayer(playerTwo);

        playerOne.send("hello");
    }
}
