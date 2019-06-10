package com.xxc.dev.network;

import org.junit.Test;

public class NetworkManagerTest {


    @Test
    public void cancel() {
        NetworkManager.getInstance().cancel("");
    }

    @Test
    public void cancelAll() {
        NetworkManager.getInstance().cancelAll();
    }
}