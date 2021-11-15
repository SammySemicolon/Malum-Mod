package com.sammy.malum.client.screen.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.container.SpiritPouchContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.awt.*;

import static net.minecraft.util.ColorHelper.PackedColor.packColor;

public class SpiritPouchContainerScreen extends ContainerScreen<SpiritPouchContainer> {
    public static final ResourceLocation texture = MalumHelper.prefix("textures/gui/spirit_pouch.png");
    public static final Color textColor = new Color(49,35,41);

    public SpiritPouchContainerScreen(SpiritPouchContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderHoveredTooltip(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY) {
        Minecraft mc = this.minecraft;
        if (mc != null) {
            ClientPlayerEntity clientPlayer = mc.player;
            if (clientPlayer != null && clientPlayer.inventory.getItemStack().isEmpty()) {
                if (this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
                    this.renderTooltip(matrixStack, this.hoveredSlot.getStack(), mouseX, mouseY);
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY) {
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
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.minecraft.getTextureManager().bindTexture(texture);
        blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}