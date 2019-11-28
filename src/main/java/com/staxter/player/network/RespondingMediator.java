package com.staxter.player.network;


import java.io.IOException;
import java.net.ServerSocket;

class RespondingMediator extends AbstractSocketMediator {

    private ServerSocket serverSocket;

    RespondingMediator(int port) {
        super(port);
    }

    @Override
    void initiateSocket() throws IOException {
        System.out.println(String.format("Starting Responding mediator, waiting for Initiator to connect on port: %s", getPort()));
        serverSocket = new ServerSocket(getPort());
        setClientSocket(serverSocket.accept());
    }

    void afterInitialization() throws IOException {
        waitForMessage();
    }

    @Override
    public void tearDown() {
        super.tearDown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Problem occurred while teardown, exception: " + e);
            throw new RuntimeException("Problem occurred while teardown", e);
        }
    }
}
