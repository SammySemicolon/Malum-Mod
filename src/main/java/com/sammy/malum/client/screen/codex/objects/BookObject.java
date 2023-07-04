package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.Minecraft;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.screen;

public class BookObject {
    public boolean isHovering;
    public float hover;
    public int posX;
    public int posY;
    public int width;
    public int height;

    public BookObject(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public int hoverCap() {
        return 20;
    }

    public void render(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {

    }

    public void lateRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {

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