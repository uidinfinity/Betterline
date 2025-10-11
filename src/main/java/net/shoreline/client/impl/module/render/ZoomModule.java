package net.shoreline.client.impl.module.render;

import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;

public class ZoomModule extends ToggleModule {
    public ZoomModule() {
        super("Zoom", "Simple zoom with C key (disabled)", ModuleCategory.RENDER);
    }
}