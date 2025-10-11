package net.shoreline.client.impl.module.client;

import net.minecraft.util.math.MathHelper;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.api.render.anim.Animation;
import net.shoreline.client.api.render.anim.Easing;
import net.shoreline.client.impl.gui.click.ClickGuiScreen;
import net.shoreline.client.util.render.ColorUtil;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

/**
 * @author linus
 * @see ClickGuiScreen
 * @since 1.0
 */
public class ClickGuiModule extends ToggleModule {

    Config<Integer> hueConfig = new NumberConfig<>("Hue", "The hue of the primary color", 0, 0, 360);
    Config<Integer> saturationConfig = new NumberConfig<>("Saturation", "The saturation of the primary color", 0, 50, 100);
    Config<Integer> brightnessConfig = new NumberConfig<>("Brightness", "The brightness of the primary color", 0, 50, 100);
    Config<Integer> hue1Config = new NumberConfig<>("Hue1", "The hue of the secondary color", 0, 0, 360);
    Config<Integer> saturation1Config = new NumberConfig<>("Saturation1", "The saturation of the secondary color", 0, 50, 100);
    Config<Integer> brightness1Config = new NumberConfig<>("Brightness1", "The brightness of the secondary color", 0, 50, 100);
    Config<Integer> alphaConfig = new NumberConfig<>("Alpha", "The alpha of colors", 0, 100, 100);
    Config<Integer> disabledHueConfig = new NumberConfig<>("DisabledHue", "Hue for disabled module text", 0, 0, 360);
    Config<Integer> disabledSaturationConfig = new NumberConfig<>("DisabledSaturation", "Saturation for disabled module text", 0, 0, 100);
    Config<Integer> disabledBrightnessConfig = new NumberConfig<>("DisabledBrightness", "Brightness for disabled module text", 0, 40, 100);

    public static ClickGuiScreen CLICK_GUI_SCREEN;
    private final Animation openCloseAnimation = new Animation(Easing.CUBIC_IN_OUT, 300);

    public float scaleConfig = 1.0f;

    public ClickGuiModule() {
        super("ClickGui", "Opens the clickgui screen", ModuleCategory.CLIENT, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @Override
    public void onEnable() {
        if (mc.player == null || mc.world == null) {
            toggle();
            return;
        }
        if (CLICK_GUI_SCREEN == null) {
            CLICK_GUI_SCREEN = new ClickGuiScreen(this);
        }
        mc.setScreen(CLICK_GUI_SCREEN);
        openCloseAnimation.setState(true);
    }

    @Override
    public void onDisable() {
        if (mc.player == null || mc.world == null) {
            toggle();
            return;
        }
        mc.player.closeScreen();
        openCloseAnimation.setStateHard(false);
    }

    public int getColor() {
        return ColorUtil.hslToColor(
                hueConfig.getValue(),
                saturationConfig.getValue(),
                brightnessConfig.getValue(),
                alphaConfig.getValue() / 100.0f
        ).getRGB();
    }

    public int getColor1() {
        return ColorUtil.hslToColor(
                hue1Config.getValue(),
                saturation1Config.getValue(),
                brightness1Config.getValue(),
                alphaConfig.getValue() / 100.0f
        ).getRGB();
    }

    public int getColor(float alpha) {
        float finalAlpha = MathHelper.clamp(alphaConfig.getValue() * alpha / 100.0f, 0.0f, 1.0f);
        return ColorUtil.hslToColor(
                hueConfig.getValue(),
                saturationConfig.getValue(),
                brightnessConfig.getValue(),
                finalAlpha
        ).getRGB();
    }

    public int getColor1(float alpha) {
        float finalAlpha = MathHelper.clamp(alphaConfig.getValue() * alpha / 100.0f, 0.0f, 1.0f);
        return ColorUtil.hslToColor(
                hue1Config.getValue(),
                saturation1Config.getValue(),
                brightness1Config.getValue(),
                finalAlpha
        ).getRGB();
    }

    public int getDisabledTextColor() {
        return ColorUtil.hslToColor(
                disabledHueConfig.getValue(),
                disabledSaturationConfig.getValue(),
                disabledBrightnessConfig.getValue(),
                1.0f
        ).getRGB();
    }

    public float getScale() {
        return scaleConfig;
    }
}