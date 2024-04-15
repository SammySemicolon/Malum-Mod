package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class LinkedEntryObject<T extends EntryScreen<T, K>, K extends AbstractMalumScreen<K>> extends AbstractSelectableEntryObject<T, K> {

    public static final ResourceLocation LINK = MalumMod.malumPath("textures/gui/book/entry_elements/arrows.png");

    public final boolean flipped;

    public LinkedEntryObject(int posX, int posY, boolean flipped, EntryReference<T, K> entryReference) {
        super(posX, posY, 36, 22, entryReference);
        this.flipped = flipped;
    }

    @Override
    public void render(T screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        final int arrowX = getOffsetXPosition();
        final int arrowY = getOffsetYPosition();
        final PoseStack poseStack = guiGraphics.pose();
        renderTexture(LINK, poseStack, arrowX, arrowY, 0, flipped ? 22 : 0, 36, 22, 36, 44);
    }
}
