package net.shoreline.client.util.world;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import java.awt.Color;

/**
 * @author uidinfinity
 * @since 1.0
 */

public class RenderUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void renderQuad(MatrixStack matrices, float left, float top, float right, float bottom, Color color) {
        if (mc.getFramebuffer() == null) return;

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        Matrix4f mat = matrices.peek().getPositionMatrix();
        float a = color.getAlpha() / 255.0f;
        float r = color.getRed() / 255.0f;
        float g = color.getGreen() / 255.0f;
        float b = color.getBlue() / 255.0f;

        buffer.vertex(mat, left, top, 0).color(r, g, b, a).next();
        buffer.vertex(mat, left, bottom, 0).color(r, g, b, a).next();
        buffer.vertex(mat, right, bottom, 0).color(r, g, b, a).next();
        buffer.vertex(mat, right, top, 0).color(r, g, b, a).next();

        BufferRenderer.drawWithGlobalProgram(buffer.end());

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    public static void renderOutline(MatrixStack matrices, float l, float t, float r, float b, Color color) {
        renderQuad(matrices, l, t, l + 1, b, color);
        renderQuad(matrices, r - 1, t, r, b, color);
        renderQuad(matrices, l, t, r, t + 1, color);
        renderQuad(matrices, l, b - 1, r, b, color);
    }
}