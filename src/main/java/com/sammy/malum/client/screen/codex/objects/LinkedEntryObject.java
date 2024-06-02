package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.pages.EntryReference;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderTexture;

public class LinkedEntryObject extends AbstractSelectableEntryObject {

    public static final ResourceLocation LINK = MalumMod.malumPath("textures/gui/book/entry_elements/entry_link.png");

    public final boolean flipped;

    public LinkedEntryObject(int posX, int posY, boolean flipped, EntryReference entryReference) {
        super(posX, posY, 36, 26, entryReference);
        this.flipped = flipped;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        final int entryX = getOffsetXPosition();
        final int entryY = getOffsetYPosition();
        final PoseStack poseStack = guiGraphics.pose();
        renderTexture(LINK, poseStack, entryX, entryY, 0, flipped ? 26 : 0, width, height, 36, 52);
        guiGraphics.renderItem(entryReference.icon, entryX + 5, entryY + 5);
    }
}
