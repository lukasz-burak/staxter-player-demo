package com.staxter.player.network;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

class InitiatorMediator extends AbstractSocketMediator {

    InitiatorMediator(int port) {
        super(port);
    }

    @Override
    void initiateSocket() throws IOException {
        setClientSocket(new Socket(InetAddress.getLoopbackAddress(), getPort()));
    }
}
