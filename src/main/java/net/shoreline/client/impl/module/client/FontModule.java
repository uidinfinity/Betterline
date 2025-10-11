package net.shoreline.client.impl.module.client;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;

/**
 * @author linus
 * @since 1.0
 */
public class FontModule extends ToggleModule {

    public Config<Boolean> shadowConfig = new BooleanConfig("Shadow", "Renders text with a shadow", true);
    public Config<Integer> fontSizeConfig = new NumberConfig<>("FontSize", "The size of the custom font", 8, 18, 32);
    public Config<Float> fontScaleConfig = new NumberConfig<>("FontScale", "The scale multiplier for font rendering", 0.5f, 1.0f, 2.0f);

    public FontModule() {
        super("Font", "Changes the client text to custom font rendering", ModuleCategory.CLIENT);
    }

    public boolean getShadow() {
        return shadowConfig.getValue();
    }

    public int getFontSize() {
        return fontSizeConfig.getValue();
    }

    public float getFontScale() {
        return fontScaleConfig.getValue();
    }
}