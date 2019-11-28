package com.staxter.player;

public class Player {

    private final String playerName;
    private final boolean isInitiator;
    private int sentMessageCounter = 0;
    private int receivedMessageCounter = 0;
    private Mediator mediator;

    public Player(String playerName) {
        this(playerName, false);
    }

    public Player(String playerName, boolean isInitiator) {
        this.playerName = playerName;
        this.isInitiator = isInitiator;
    }

    public void registerMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public void send(String message) {
        System.out.println(String.format("[%s] Sending message: %s", playerName, message));
        incrementSentCounter();
        mediator.send(playerName, message);
    }

    public void receive(String message) {
        System.out.println(String.format("[%s] Received message: %s", playerName, message));
        incrementReceivedCounter();
        if (shouldRespond()) {
            send(prepareMessage(message));
        } else {
            mediator.tearDown();
        }
    }

    private void incrementSentCounter() {
        this.sentMessageCounter++;
    }

    private void incrementReceivedCounter() {
        this.receivedMessageCounter++;
    }

    private boolean shouldRespond() {
        return !(isInitiator && sentMessageCounter == 10 && receivedMessageCounter == 10);
    }

    private String prepareMessage(String message) {
        return message + " " + sentMessageCounter;
    }

    public String getName() {
        return playerName;
    }
}
