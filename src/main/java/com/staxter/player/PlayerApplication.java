package com.staxter.player;

import com.staxter.player.local.LocalApplication;
import com.staxter.player.network.RemoteApplication;

import java.util.Arrays;

public class PlayerApplication {

    private static final String REMOTE_APP_ARG = "remote";
    private static final String REMOTE_APP_INITIATOR_ARG = "-i";

    public static void main( String[] args ) {
        if (isRemoteVersion(args)) {
            RemoteApplication.start(isInitiator(args));
        } else {
            LocalApplication.start();
        }
    }

    private static boolean isRemoteVersion(String[] args) {
        return Arrays.asList(args).contains(REMOTE_APP_ARG);
    }

    private static boolean isInitiator(String[] args) {
        return Arrays.asList(args).contains(REMOTE_APP_INITIATOR_ARG);
    }
}
