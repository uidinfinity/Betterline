package net.shoreline.client.impl.module.movement;

import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;

/**
 * @author uidinfinity
 * @since 1.0
 */
public class SneakModule extends ToggleModule {

    public SneakModule() {
        super("Sneak", "Automatically sneaks for you.", ModuleCategory.MOVEMENT);
    }

    @EventListener
    public void onTick(TickEvent event) {
        if (mc.options.sneakKey.isPressed()) {
            return;
        }
        mc.options.sneakKey.setPressed(true);
    }
}