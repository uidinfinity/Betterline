package net.shoreline.client.impl.module.client;

import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.gui.hud.RenderOverlayEvent;
import net.shoreline.client.init.Modules;

public class UserInfoModule extends ToggleModule {

    private static final String TARGET_USER = "root";
    private boolean shouldDisplay = false;

    public UserInfoModule() {
        super("UserInfo", "Displays your uid", ModuleCategory.CLIENT);
    }

    @Override
    public void onEnable() {
        checkSystemUser();
    }

    private void checkSystemUser() {
        String systemUser = System.getProperty("user.name");
        this.shouldDisplay = TARGET_USER.equals(systemUser);
    }

    @EventListener
    public void onRenderOverlay(RenderOverlayEvent.Post event) {
        if (!shouldDisplay || mc.player == null || mc.world == null) {
            return;
        }

        int color = Modules.COLORS.getRGB();

        String text = "Uid: 1";
        event.getContext().drawTextWithShadow(mc.textRenderer, text, 2, 11, color);
    }
}