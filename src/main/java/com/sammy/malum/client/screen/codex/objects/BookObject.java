package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.AbstractProgressionCodexScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;

import static com.sammy.malum.MalumMod.malumPath;

public class BookObject {

    public static final ResourceLocation WIDGET_FADE_TEXTURE = malumPath("textures/gui/book/widget_fade.png");

    public final AbstractProgressionCodexScreen screen;
    public final int posX;
    public final int posY;
    public final int width;
    public final int height;
    public boolean isHovering;
    public float hover;

    public BookObject(AbstractProgressionCodexScreen screen, int posX, int posY, int width, int height) {
        this.screen = screen;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public int hoverCap() {
        return 20;
    }

    public void render(Minecraft minecraft, GuiGraphics guiGraphics, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {

    }

    public void lateRender(Minecraft minecraft, GuiGraphics guiGraphics, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {

    }

    public void click(float xOffset, float yOffset, double mouseX, double mouseY) {

    }

    public void exit() {

    }

    public boolean isHovering(AbstractProgressionCodexScreen screen, float xOffset, float yOffset, double mouseX, double mouseY) {
        return screen.isHovering(mouseX, mouseY, offsetPosX(xOffset), offsetPosY(yOffset), width, height);
    }

    public int offsetPosX(float xOffset) {
        int guiLeft = (width - screen.bookWidth) / 2;
        return (int) (guiLeft + this.posX + xOffset);
    }

    public int offsetPosY(float yOffset) {
        int guiTop = (height - screen.bookHeight) / 2;
        return (int) (guiTop + this.posY + yOffset);
    }
}