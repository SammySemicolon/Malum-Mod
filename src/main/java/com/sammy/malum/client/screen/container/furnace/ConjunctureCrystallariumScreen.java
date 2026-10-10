package com.sammy.malum.client.screen.container.furnace;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.container.AbstractMalumContainerScreen;
import com.sammy.malum.common.block.curiosities.artifice.furnace.crystallarium.ConjunctureCrystallariumContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

//TODO abstract this as well
public class ConjunctureCrystallariumScreen extends AbstractMalumContainerScreen<ConjunctureCrystallariumContainer> {

    private static final ResourceLocation TEXTURE = MalumMod.malumPath("textures/gui/container/conjuncture_crystallarium.png");
    private static final ResourceLocation ARROW_LEFT = MalumMod.malumPath("textures/gui/container/conjuncture_arrow_left.png");
    private static final ResourceLocation ARROW_RIGHT = MalumMod.malumPath("textures/gui/container/conjuncture_arrow_right.png");
    private static final ResourceLocation BURN = MalumMod.malumPath("textures/gui/container/burn_overlay.png");

    public ConjunctureCrystallariumScreen(ConjunctureCrystallariumContainer menu, Inventory pPlayerInventory, Component pTitle) {
        super(menu, pPlayerInventory, pTitle);
        imageWidth = 176;
        imageHeight = 233;
        titleLabelX = 88;
        titleLabelY = -10;
        inventoryLabelX = 8;
        inventoryLabelY = 133;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        if (menu.isLit()) {
            int textureSize = 14;
            int progress = Mth.ceil(this.menu.getLitProgress() * 13.0F) + 1;
            guiGraphics.blit(BURN, leftPos + 81, topPos + 93 + 14 - progress , 0, (float)0, (float)14 - progress, 14, progress, textureSize, textureSize);
        }

        int burnProgress = Mth.ceil(this.menu.getBurnProgress() * 27.0F);
        guiGraphics.blit(ARROW_LEFT, leftPos + 55, topPos + 56 + 27 - burnProgress, 0, (float)0, (float)27 - burnProgress, 18, burnProgress, 18, 27);
        guiGraphics.blit(ARROW_RIGHT, leftPos + 103, topPos + 56 + 27 - burnProgress, 0, (float)0, (float)27 - burnProgress, 19, burnProgress, 19, 27);
    }

    @Override
    public ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }
}