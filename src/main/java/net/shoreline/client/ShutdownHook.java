package net.shoreline.client;

import net.shoreline.client.api.file.ClientConfiguration;

public class ShutdownHook extends Thread {

    public ShutdownHook() {
        setName("Shoreline-ShutdownHook");
    }

    @Override
    public void run() {
        Shoreline.info("Saving configurations and shutting down!");
        Shoreline.CONFIG.saveClient();
    }
}
