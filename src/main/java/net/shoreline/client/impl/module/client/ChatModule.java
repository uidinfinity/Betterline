package net.shoreline.client.impl.module.client;

import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.module.ConcurrentModule;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.util.chat.ChatUtil;
import net.minecraft.util.Formatting;

public class ChatModule extends ConcurrentModule {
    public static boolean notificationsEnabled = true;

    public ChatModule() {
        super("Chat", "Client chat manager", ModuleCategory.CLIENT);
        BooleanConfig notifyConfig = new BooleanConfig("Notifications", "Toggle module notifications", true) {
            @Override
            public void setValue(Boolean value) {
                super.setValue(value);
                notificationsEnabled = value;
            }
        };
        register(notifyConfig);
        notificationsEnabled = notifyConfig.getValue();
    }

    public static void sendToggleNotification(String moduleName, boolean enabled) {
        if (!notificationsEnabled) return;
        String state = enabled ? Formatting.GREEN + "enabled" : Formatting.RED + "disabled";
        ChatUtil.clientSendMessage("%s §7has been %s§7.", moduleName, state);
    }
}