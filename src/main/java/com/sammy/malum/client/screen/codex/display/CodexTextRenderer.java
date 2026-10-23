package com.sammy.malum.client.screen.codex.display;

import com.mojang.blaze3d.systems.*;
import com.sammy.malum.client.screen.codex.helper.CodexTextHelper;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.util.*;
import org.joml.*;
import team.lodestar.lodestone.modules.core.easing.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.wrapper.LodestoneBufferWrapper;

import java.awt.*;
import java.lang.Math;
import java.util.function.*;

import static com.sammy.malum.client.screen.codex.helper.CodexTextHelper.*;
import static net.minecraft.client.gui.Font.DisplayMode.*;
import static net.minecraft.util.FastColor.ARGB32.color;

public class CodexTextRenderer {

    public static final Function<GuiGraphics, LodestoneBufferWrapper> WRAPPER_FUNCTION = Util.memoize(guiGraphics -> new LodestoneBufferWrapper(LodestoneRenderTypes.ADDITIVE_TEXT, guiGraphics.bufferSource));
    public static final TextColorData DEFAULT_TEXT_COLOR = new TextColorData(new Color(138, 79, 58), new Color(65, 41, 8), new Color(20, 44, 60), new Color(227, 39, 228));
    public static final TextColorData GEAS_POSITIVE_COLOR = new TextColorData(new Color(18, 52, 141), new Color(118, 52, 141), new Color(20, 44, 120), new Color(100, 100, 240));
    public static final TextColorData GEAS_NEGATIVE_COLOR = new TextColorData(new Color(141, 18, 52), new Color(118, 52, 141), new Color(120, 44, 20), new Color(240, 100, 100));

    protected Minecraft minecraft = Minecraft.getInstance();
    protected Font font = minecraft.font;

    protected boolean isCentered = false;
    protected float scale = 1f;
    protected float glowStrength = 0.4f;
    protected TextColorData textColor = DEFAULT_TEXT_COLOR;

    public static CodexTextRenderer create() {
        return new CodexTextRenderer();
    }

    public CodexTextRenderer setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public CodexTextRenderer setGlowStrength(float glowStrength) {
        this.glowStrength = glowStrength;
        return this;
    }

    public CodexTextRenderer setTextColor(TextColorData textColor) {
        this.textColor = textColor;
        return this;
    }

    public CodexTextRenderer setCentered(boolean centered) {
        isCentered = centered;
        return this;
    }

    public CodexTextRenderer renderWrappingText(GuiGraphics guiGraphics, Component text, float x, float y) {
        return renderWrappingText(guiGraphics, text, x, y, 140);
    }

    public CodexTextRenderer renderWrappingText(GuiGraphics guiGraphics, Component text, float x, float y, int width) {
        text = applyScaleAndUpdate(text);
        var font = Minecraft.getInstance().font;
        var wrapped = wrapComponent(text, (int) (width /scale));
        for (int i = 0; i < wrapped.size(); i++) {
            var currentLine = wrapped.get(i);
            float offset = i * (font.lineHeight + 1) * scale;
            renderText(guiGraphics, currentLine, x, y + offset);
        }
        return this;
    }

    public CodexTextRenderer renderHeadline(GuiGraphics guiGraphics, Component headline, float x, float y) {
        int width = Minecraft.getInstance().font.width(headline.getString());
        float scaling = 1f;
        if (width > 100) {
            scaling -= (width - 100) / 200f;
        }

        float oldScale = scale;
        boolean oldCentered = isCentered;
        return setScale(scale * scaling)
                .setCentered(true)
                .renderText(guiGraphics, headline.getVisualOrderText(), x+72, y+11)
                .setCentered(oldCentered)
                .setScale(oldScale);
    }

    public CodexTextRenderer renderText(GuiGraphics guiGraphics, Component text, float x, float y) {
        return renderText(guiGraphics, text.getVisualOrderText(), x, y);
    }

    public CodexTextRenderer renderText(GuiGraphics guiGraphics, FormattedCharSequence text, float x, float y) {
        var poseStack = guiGraphics.pose();
        float guiScale = (float) minecraft.getWindow().getGuiScale();
        float screenWidth = minecraft.getWindow().getScreenWidth();
        float screenHeight = minecraft.getWindow().getScreenHeight();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        if (scale != 1) {
            poseStack.scale(scale, scale, 1);
        }

        var projection = new Vector4f(1, 1, 0, 1);
        projection.mul(poseStack.last().pose());

        int width = font.width(text);
        int height = font.lineHeight;
        if (isCentered) {
            poseStack.translate(-width / 2f, -height / 2f, 0);
        }
        float relativeX = projection.x * guiScale / screenWidth;
        float relativeY = projection.y * guiScale / screenHeight;
        float relativeXMax = (projection.x+width) * guiScale / screenWidth;
        float relativeYMax = (projection.y+height) * guiScale / screenHeight;
        float mouseX = (float) (minecraft.mouseHandler.xpos() / screenWidth);
        float mouseY = (float) (minecraft.mouseHandler.ypos() / screenHeight);

        float differenceX = Math.min(Math.abs(relativeX - mouseX), Mth.abs(relativeXMax-mouseX)) * 5;
        float differenceY = Math.min(Math.abs(relativeY - mouseY), Mth.abs(relativeYMax-mouseY)) * 10;
        float horizontalDelta = Math.clamp(1 - differenceX, 0, 1);
        float verticalDelta = Math.clamp(1 - differenceY, 0, 1);
        float delta = Easing.QUINTIC_OUT.ease(horizontalDelta) * Easing.QUINTIC_OUT.ease(verticalDelta);
        if (CodexEntryScreen.textJump > 0) {
            double jumpDelta = delta * Easing.SINE_IN_OUT.ease(CodexEntryScreen.textJump);
            glowStrength *= (float) (1 + jumpDelta);
        }

        var gray = textColor.primaryColor().getRGB();
        var dark = textColor.secondaryColor().getRGB();

        MultiBufferSource buffer = guiGraphics.bufferSource;
        var pose = poseStack.last().pose();
        drawInBatch(buffer, pose, text, -1f, 0, color(64, gray));
        drawInBatch(buffer, pose, text, +1f, 0, color(32, gray));
        drawInBatch(buffer, pose, text, 0, 0 - 1f, color(32, gray));
        drawInBatch(buffer, pose, text, 0, 0 + 1f, color(92, gray));

        drawInBatch(buffer, pose, text, 0, 0, color(255, dark));

        if (delta > 0.025f) {
            int alpha = Mth.floor(255 * Easing.QUARTIC_IN.lerp(delta, 0.4f, 1) * glowStrength);
            alpha = Mth.clamp(alpha, 0, 255);
            if (alpha > 15) {
                float color = Easing.CUBIC_IN.ease(delta);
                Color start = textColor.glowStart();
                Color end = textColor.glowEnd();
                int r = (int) Mth.lerp(color, start.getRed(), end.getRed());
                int g = (int) Mth.lerp(color, start.getGreen(), end.getGreen());
                int b = (int) Mth.lerp(color, start.getBlue(), end.getBlue());
                buffer = WRAPPER_FUNCTION.apply(guiGraphics);
                drawInBatch(buffer, pose, text, 0f, 0, color(alpha, r, g, b));

                drawInBatch(buffer, pose, text, 1f, 0, color(alpha / 2, r, g, b));
                drawInBatch(buffer, pose, text, -1f, 0, color(alpha / 3, r, g, b));
                drawInBatch(buffer, pose, text, 0, 1f, color(alpha / 2, r, g, b));
                drawInBatch(buffer, pose, text, 0, -1f, color(alpha / 3, r, g, b));


                RenderSystem.disableBlend();
            }
        }
        poseStack.popPose();
        return this;
    }

    protected void drawInBatch(MultiBufferSource buffer, Matrix4f pose, FormattedCharSequence text, float x, float y, int color) {
        font.drawInBatch(text, x, y, color, false, pose, buffer, NORMAL, 0, 15728880);
    }

    public Component applyScaleAndUpdate(Component text) {
        var translated = text.getString();
        if (translated.startsWith("$m")) {
            int i = translated.indexOf("/$");
            float value = Float.parseFloat(translated.substring(3, i));
            text = Component.literal(translated.substring(i + 2));
            setScale(value);
        }
        return text;
    }

    public record TextColorData(Color primaryColor, Color secondaryColor, Color glowStart, Color glowEnd) {
    }
}