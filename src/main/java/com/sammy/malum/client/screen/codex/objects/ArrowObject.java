package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderTexture;

public class ArrowObject<T extends AbstractProgressionCodexScreen<T>> extends BookObject<EntryScreen<T>> {

    public static final ResourceLocation ARROWS = MalumMod.malumPath("textures/gui/book/entry_elements/arrows.png");
    public static final ResourceLocation ARROWS_LIT_UP = MalumMod.malumPath("textures/gui/book/entry_elements/arrows_active.png");

    public final boolean flipped;

    public ArrowObject(EntryScreen<T> screen, int posX, int posY, boolean flipped) {
        super(screen, posX, posY, 36, 22);
        this.flipped = flipped;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        final int arrowX = getOffsetXPosition();
        final int arrowY = getOffsetYPosition();
        ResourceLocation texture = isHoveredOver ? ARROWS_LIT_UP : ARROWS;
        final PoseStack poseStack = guiGraphics.pose();
        renderTexture(texture, poseStack, arrowX, arrowY, 0, flipped ? 23 : 0, 36, 22, 64, 64);
    }

    @Override
    public void click(double mouseX, double mouseY) {
        if (flipped) {
            screen.nextPage();
        }
        else {
            screen.previousPage(true);
        }
    }

    @Override
    public boolean isValid() {
        return !flipped || (screen.hasNextPage());
    }
}
