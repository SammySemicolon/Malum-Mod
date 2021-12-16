package com.sammy.malum.client.screen.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.container.SpiritPouchContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.awt.*;

import static net.minecraft.util.ColorHelper.PackedColor.packColor;

public class SpiritPouchContainerScreen extends ContainerScreen<SpiritPouchContainer> {
    public static final ResourceLocation texture = MalumHelper.prefix("textures/gui/spirit_pouch.png");
    public static final Color textColor = new Color(49,35,41);

    public SpiritPouchContainerScreen(SpiritPouchContainer screenContainer, PlayerInventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY) {
        Minecraft mc = this.minecraft;
        if (mc != null) {
            ClientPlayer clientPlayer = mc.player;
            if (clientPlayer != null && clientPlayer.inventory.getCarried().isEmpty()) {
                if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
                    this.renderTooltip(matrixStack, this.hoveredSlot.getItem(), mouseX, mouseY);
                }
            }
        }
    }

    @Override
    protected void renderLabels(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null) {
//            Color color = textColor;
//            Color insideColor = MalumHelper.darker(color, 3);
//            Color outlineColor = MalumHelper.darker(color, 2);
//            String text = title.getString();
//            float x = 89 - font.getStringWidth(text) / 2f;
//            float y = 6;

//            font.drawString(matrixStack, text, x, y - 1, packColor(96, outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));
//            font.drawString(matrixStack, text, x - 1, y, packColor(96, outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));
//            font.drawString(matrixStack, text, x + 1, y, packColor(96, outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));
//            font.drawString(matrixStack, text, x, y + 1, packColor(96, outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));
//
//            font.drawString(matrixStack, text, x, y, packColor(255, insideColor.getRed(), insideColor.getGreen(), insideColor.getBlue()));
        }
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.minecraft.getTextureManager().bind(texture);
        blit(matrixStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }
}