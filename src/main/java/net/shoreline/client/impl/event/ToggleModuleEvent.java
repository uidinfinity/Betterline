package net.shoreline.client.impl.event;

import net.shoreline.client.api.event.Event;
import net.shoreline.client.api.module.ToggleModule;

public class ToggleModuleEvent extends Event {
    private final ToggleModule module;

    public ToggleModuleEvent(ToggleModule module) {
        this.module = module;
    }

    public ToggleModule getModule() {
        return module;
    }
}