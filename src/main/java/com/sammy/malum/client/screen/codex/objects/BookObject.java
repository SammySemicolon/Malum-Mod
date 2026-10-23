package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;

import static com.sammy.malum.MalumMod.*;

public class BookObject<T extends AbstractMalumCodexScreen> {

    public static final ResourceLocation WIDGET_FADE_TEXTURE = malumPath("textures/gui/book/widget_fade.png");

    public final int x;
    public final int y;
    public final int width;
    public final int height;

    public boolean wasHoveredOver;
    public boolean isHoveredOver;
    public boolean wasPressed;
    public boolean isPressed;

    public boolean isInSubspace;
    public float xOffset;
    public float yOffset;

    public BookObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isValid(T screen) {
        return true;
    }

    public boolean hasPriority(T screen) {
        return false;
    }


    public final void updateValues(T screen, double mouseX, double mouseY) {
        wasHoveredOver = isHoveredOver;
        wasPressed = isPressed;
    }

    public void tick(T screen, double mouseX, double mouseY) {

    }

    public void applyTransforms(T screen, PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {

    }

    public void render(T screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

    }

    public void renderLate(T screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

    }

    public boolean tryClick(T screen, double mouseX, double mouseY) {
        if (isHoveredOver) {
            isPressed = true;
            return click(screen, mouseX, mouseY);
        }
        return false;
    }

    public boolean tryRelease(T screen, double mouseX, double mouseY) {
        if (isHoveredOver || isPressed) {
            isPressed = false;
            return release(screen, mouseX, mouseY);
        }
        return false;
    }

    public boolean click(T screen, double mouseX, double mouseY) {
        return false;
    }

    public boolean release(T screen, double mouseX, double mouseY) {
        return false;
    }

    public boolean isHovering(T screen, double mouseX, double mouseY) {
        return screen.isHovering(mouseX, mouseY, getOffsetX(), getOffsetY(), width, height);
    }

    public boolean isInView(T screen) {
        return true;
    }

    public int getOffsetX() {
        return (int) (x + xOffset);
    }

    public int getOffsetY() {
        return (int) (y + yOffset);
    }
}