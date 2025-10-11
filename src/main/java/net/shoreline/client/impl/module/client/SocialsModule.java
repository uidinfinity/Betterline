package net.shoreline.client.impl.module.client;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.ColorConfig;
import net.shoreline.client.api.module.ConcurrentModule;
import net.shoreline.client.api.module.ModuleCategory;

import java.awt.*;

public class SocialsModule extends ConcurrentModule {
    private static SocialsModule INSTANCE;

    Config<Boolean> friendsConfig = new BooleanConfig("Friends", "Allows friend system to function", true);
    Config<Boolean> addNotifyConfig = new BooleanConfig("AddNotify", "Notifies players when you add them as a friend", false, () -> friendsConfig.getValue());
    Config<Color> friendsColorConfig = new ColorConfig("FriendsColor", "The color for friends in the client", new Color(0xff66ff), false, false, () -> friendsConfig.getValue());

    public SocialsModule() {
        super("Socials", "The client socials system", ModuleCategory.CLIENT);
        INSTANCE = this;

        register(friendsConfig);
        register(addNotifyConfig);
        register(friendsColorConfig);
    }

    public static SocialsModule getInstance() {
        return INSTANCE;
    }

    public boolean isFriendsEnabled() {
        return friendsConfig.getValue();
    }

    public boolean shouldNotify() {
        return addNotifyConfig.getValue();
    }

    public Color getFriendColor() {
        return friendsColorConfig.getValue();
    }

    public int getFriendRGB() {
        return getFriendColor().getRGB();
    }
}