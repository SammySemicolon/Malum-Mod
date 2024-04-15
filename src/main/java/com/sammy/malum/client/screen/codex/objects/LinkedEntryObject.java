package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class LinkedEntryObject extends AbstractSelectableEntryObject {

    public static final ResourceLocation LINK = MalumMod.malumPath("textures/gui/book/entry_elements/entry_link.png");

    public final boolean flipped;

    public LinkedEntryObject(int posX, int posY, boolean flipped, EntryReference entryReference) {
        super(posX, posY, 36, 22, entryReference);
        this.flipped = flipped;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        final int entryX = getOffsetXPosition();
        final int entryY = getOffsetYPosition();
        final PoseStack poseStack = guiGraphics.pose();
        renderTexture(LINK, poseStack, entryX, entryY, 0, flipped ? 22 : 0, 36, 22, 36, 44);
        guiGraphics.renderItem(entryReference.icon, entryX + 8, entryY + 3);
    }
}
