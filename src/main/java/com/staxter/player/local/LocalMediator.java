package com.staxter.player.local;

import com.staxter.player.Mediator;
import com.staxter.player.Player;

import java.util.LinkedList;
import java.util.List;

class LocalMediator implements Mediator {

    private final List<Player> players = new LinkedList<>();

    public void send(String sender, String message) {
        players.stream()
                .filter(player -> isNotSender(player, sender))
                .findFirst()
                .map(player -> sendMessage(player, message))
                .orElseThrow(PlayerNotFoundException::new);
    }

    private boolean isNotSender(Player player, String sender) {
        return !sender.equals(player.getName());
    }

    public void registerPlayer(Player player) {
        System.out.println(String.format("Registering player: [%s]", player.getName()));
        player.registerMediator(this);
        players.add(player);
    }

    public void tearDown() {
        System.out.println("Message exchange completed, teardown");
    }

    private Player sendMessage(Player player, String message) {
        player.receive(message);
        return player;
    }
}
