package com.sammy.malum.client.screen.container.coffer;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.container.AbstractMalumContainerScreen;
import com.sammy.malum.common.container.CofferContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CofferContainerScreen extends AbstractMalumContainerScreen<CofferContainer> {
    private static final ResourceLocation TEXTURE = MalumMod.malumPath("textures/gui/container/coffer.png");
    private static final ResourceLocation[] ROWS = {
            MalumMod.malumPath("textures/gui/container/coffer_row1.png"),
            MalumMod.malumPath("textures/gui/container/coffer_row2.png"),
            MalumMod.malumPath("textures/gui/container/coffer_row3.png"),
            MalumMod.malumPath("textures/gui/container/coffer_row4.png"),
            MalumMod.malumPath("textures/gui/container/coffer_row5.png"),
            MalumMod.malumPath("textures/gui/container/coffer_row6.png")};

    protected final CofferContainer container;

    public CofferContainerScreen(CofferContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.container = container;
        this.width = 176;
        this.height = 239;
        this.imageHeight = 256;
        this.imageWidth = 256;
        this.inventoryLabelY = 144;
    }

    @Override
    protected void init() {
        super.init();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(new CofferPinButton(this, leftPos, topPos + 33 + 18 * i, i, btn -> container.togglePin(finalI)));
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        int group = 0;
        for (int i = 0; i < 6; i++) {
            guiGraphics.blit(ROWS[group], leftPos + 7, topPos + 17 + (18 * i) + group * 2, 0, 0, 162, 18, 162, 18);
            //for (int j = 0; j < 9; j++) {
            //Rendering the items
            //}
            if (i < 5 && container.checkPin(i))
                group++;
        }
    }

    @Override
    public ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }

}
