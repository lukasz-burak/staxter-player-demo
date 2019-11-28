package com.staxter.player;

public interface Mediator {
    void send(String sender, String message);
    void registerPlayer(Player player);
    void tearDown();
}
