package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SelectableEntryObject<T extends EntryScreen<T, K>, K extends AbstractMalumScreen<K>> extends AbstractSelectableEntryObject<T, K> {

    public static final ResourceLocation SOCKET = MalumMod.malumPath("textures/gui/book/entry_elements/entry_socket.png");

    public SelectableEntryObject(int posX, int posY, EntryReference<T, K> entryReference) {
        super(posX, posY, 32, 32, entryReference);
    }

    @Override
    public void render(T screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        final int entryX = getOffsetXPosition();
        final int entryY = getOffsetYPosition();
        final PoseStack poseStack = guiGraphics.pose();
        renderTexture(SOCKET, poseStack, entryX, entryY, 0, 0, 32, 32);
        guiGraphics.renderItem(entryReference.icon, entryX + 8, entryY + 8);
    }
}
