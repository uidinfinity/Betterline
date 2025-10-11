package net.shoreline.client.impl.module.render;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.Render2DEvent;
import net.shoreline.client.util.world.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import java.awt.Color;

/**
 * @author uidinfinity
 * @since 1.0
 */

public class CrosshairModule extends ToggleModule {
    public Config<Float> length = new NumberConfig<>("Length", "", 1f, 8f, 20f);
    public Config<Float> thickness = new NumberConfig<>("Thickness", "", 0.5f, 2f, 10f);
    public Config<Float> gap = new NumberConfig<>("Gap", "", 0f, 2f, 10f);
    public Config<Boolean> outline = new BooleanConfig("Outline", "", true);

    public CrosshairModule() {
        super("Crosshair", "Custom crosshair", ModuleCategory.RENDER);
    }

    @EventListener
    public void onRender2D(Render2DEvent event) {
        if (mc.player == null || mc.world == null) return;

        DrawContext ctx = event.getContext();
        MatrixStack ms = ctx.getMatrices();
        int w = mc.getWindow().getScaledWidth();
        int h = mc.getWindow().getScaledHeight();
        float cx = w / 2f;
        float cy = h / 2f;

        float t = thickness.getValue() / 2f;
        float l = length.getValue();
        float g = gap.getValue();

        // Top
        RenderUtil.renderQuad(ms, cx - t, cy - l - g, cx + t, cy - g, Color.WHITE);
        // Bottom
        RenderUtil.renderQuad(ms, cx - t, cy + g, cx + t, cy + g + l, Color.WHITE);
        // Left
        RenderUtil.renderQuad(ms, cx - g - l, cy - t, cx - g, cy + t, Color.WHITE);
        // Right
        RenderUtil.renderQuad(ms, cx + g, cy - t, cx + g + l, cy + t, Color.WHITE);

        if (outline.getValue()) {
            RenderUtil.renderOutline(ms, cx - t, cy - l - g, cx + t, cy - g, Color.BLACK);
            RenderUtil.renderOutline(ms, cx - t, cy + g, cx + t, cy + g + l, Color.BLACK);
            RenderUtil.renderOutline(ms, cx - g - l, cy - t, cx - g, cy + t, Color.BLACK);
            RenderUtil.renderOutline(ms, cx + g, cy - t, cx + g + l, cy + t, Color.BLACK);
        }
    }
}