package net.shoreline.client;

import net.shoreline.client.api.Identifiable;
import net.shoreline.client.api.event.handler.EventBus;
import net.shoreline.client.api.event.handler.EventHandler;
import net.shoreline.client.api.file.ClientConfiguration;
import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Shoreline {
    // Client logger.
    public static Logger LOGGER;
    // Client Event handler (aka Event bus) which handles event dispatching
    // and listening for client events.
    public static EventHandler EVENT_HANDLER;
    // Client configuration handler. This master saves/loads the client
    // configuration files which have been saved locally.
    public static ClientConfiguration CONFIG;
    // Client shutdown hooks which will run once when the MinecraftClient
    // game instance is shutdown.
    public static ShutdownHook SHUTDOWN;
    //
    public static Executor EXECUTOR;

    public static void init() {
        LOGGER = LogManager.getLogger("Shoreline");

        // Debug information - required when submitting a crash / bug report
        info("This build of Shoreline is on Git hash {} and was compiled on {}", BuildConfig.HASH, BuildConfig.BUILD_TIME);
        info("Starting preInit ...");

        EXECUTOR = Executors.newFixedThreadPool(1);
        // Create event handler instance
        EVENT_HANDLER = new EventBus();
        info("Starting init ...");
        Managers.init();
        Modules.init();
        // Commands.init();
        info("Starting postInit ...");
        CONFIG = new ClientConfiguration();
        Managers.postInit();
        SHUTDOWN = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(SHUTDOWN);
        // load configs AFTER everything has been initialized
        // this is to prevent configs loading before certain aspects of managers are available
        CONFIG.loadClient();
    }

    public static void info(String message) {
        LOGGER.info(String.format("[Shoreline] %s", message));
    }

    public static void info(String message, Object... params) {
        LOGGER.info(String.format("[Shoreline] %s", message), params);
    }

    public static void info(Identifiable feature, String message) {
        LOGGER.info(String.format("[%s] %s", feature.getId(), message));
    }

    public static void info(Identifiable feature, String message,
                            Object... params) {
        LOGGER.info(String.format("[%s] %s", feature.getId(), message), params);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void error(String message, Object... params) {
        LOGGER.error(message, params);
    }

    public static void error(Identifiable feature, String message) {
        LOGGER.error(String.format("[%s] %s", feature.getId(), message));
    }

    public static void error(Identifiable feature, String message,
                             Object... params) {
        LOGGER.error(String.format("[%s] %s", feature.getId(), message), params);
    }
}
