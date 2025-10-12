package net.shoreline.client.impl.module.client;

import net.minecraft.util.math.MathHelper;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.ColorConfig;
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

    Config<Color> primaryColorConfig = new ColorConfig("PrimaryColor", "The primary GUI color", new Color(255, 0, 0), false, false);
    Config<Color> secondaryColorConfig = new ColorConfig("SecondaryColor", "The secondary GUI color", new Color(255, 100, 0), false, false);
    Config<Integer> alphaConfig = new NumberConfig<>("Alpha", "The alpha of GUI colors", 200, 0, 255);
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
        Color c = primaryColorConfig.getValue();
        int a = alphaConfig.getValue();
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), a).getRGB();
    }

    public int getColor1() {
        Color c = secondaryColorConfig.getValue();
        int a = alphaConfig.getValue();
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), a).getRGB();
    }

    public int getColor(float alphaMultiplier) {
        Color c = primaryColorConfig.getValue();
        int baseAlpha = alphaConfig.getValue();
        int finalAlpha = (int) (baseAlpha * MathHelper.clamp(alphaMultiplier, 0.0f, 1.0f));
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), finalAlpha).getRGB();
    }

    public int getColor1(float alphaMultiplier) {
        Color c = secondaryColorConfig.getValue();
        int baseAlpha = alphaConfig.getValue();
        int finalAlpha = (int) (baseAlpha * MathHelper.clamp(alphaMultiplier, 0.0f, 1.0f));
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), finalAlpha).getRGB();
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