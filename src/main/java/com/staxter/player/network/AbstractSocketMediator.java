package com.staxter.player.network;

import com.staxter.player.Mediator;
import com.staxter.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

abstract class AbstractSocketMediator implements Mediator {

    private final int port;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Player player;

    AbstractSocketMediator(int port) {
        this.port = port;
    }

    abstract void initiateSocket() throws IOException;

    @Override
    public void send(String recipient, String message) {
        out.println(message);
        try {
            player.receive(readResponse());
        } catch (EmptyResponseException e) {
            tearDown();
        }
    }

    @Override
    public void registerPlayer(Player player) {
        this.player = player;
        startNetworkLayer();
    }

    private void startNetworkLayer() {
        try {
            initiateSocket();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            player.registerMediator(this);
            afterInitialization();
        } catch (IOException e) {
            System.out.println("Problem occurred while initiating sockets, exception: " + e);
            throw new RuntimeException("Problem occurred while initiating sockets", e);
        }
    }

    @Override
    public void tearDown() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Problem occurred while teardown, exception: " + e);
            throw new RuntimeException("Problem occurred while teardown", e);
        }
    }

    void afterInitialization() throws IOException {}

    int getPort() {
        return port;
    }

    void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    void waitForMessage() throws IOException {
        player.receive(in.readLine());
    }

    private String readResponse() throws EmptyResponseException {
        try {
            return Optional.ofNullable(in.readLine())
                    .orElseThrow(EmptyResponseException::new);
        } catch (IOException e) {
            System.out.println("Problem occurred while receiving the message, exception: " + e);
            throw new RuntimeException("Problem occurred while receiving the message", e);
        }
    }
}
