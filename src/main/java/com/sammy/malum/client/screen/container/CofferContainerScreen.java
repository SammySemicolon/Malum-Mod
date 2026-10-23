package com.sammy.malum.client.screen.container;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.container.CofferContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class CofferContainerScreen extends AbstractMalumContainerScreen<CofferContainer>{
    private static final ResourceLocation TEXTURE = MalumMod.malumPath("textures/gui/coffer.png");

    private final CofferContainer container;

    public CofferContainerScreen(CofferContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.container = container;
        this.width = 176;
        this.height = 239;
        this.imageHeight = 256;
        this.imageWidth = 256;
    }

    @Override
    public ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        int group = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
                //blit the row slots
            }
            if (i < 5 && container.checkPin(i))
                group++;
        }
    }
}
