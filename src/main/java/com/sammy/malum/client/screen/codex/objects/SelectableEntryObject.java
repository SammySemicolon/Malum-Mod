package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;

import java.util.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SelectableEntryObject<T extends AbstractProgressionCodexScreen<T>> extends BookObject<EntryScreen<T>> {

    public static final ResourceLocation SOCKET = MalumMod.malumPath("textures/gui/book/entry_elements/entry_socket.png");
    public final EntrySelectorPage.EntryChoice<T> entryChoice;

    public SelectableEntryObject(int posX, int posY, EntrySelectorPage.EntryChoice<T> entryChoice) {
        super(posX, posY, 32, 32);
        this.entryChoice = entryChoice;
    }

    @Override
    public void render(EntryScreen<T> screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        final int entryX = getOffsetXPosition();
        final int entryY = getOffsetYPosition();
        final PoseStack poseStack = guiGraphics.pose();
        renderTexture(SOCKET, poseStack, entryX, entryY, 0, 0, 32, 32);
        guiGraphics.renderItem(entryChoice.icon(), entryX + 8, entryY + 8);
    }

    @Override
    public void renderLate(EntryScreen<T> screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (isHoveredOver) {
            final BookEntry<T> entry = entryChoice.entry();
            final List<Component> list = Arrays.asList(
                    Component.translatable(entry.translationKey()),
                    Component.translatable(entry.descriptionTranslationKey()).withStyle(ChatFormatting.GRAY));
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, list, mouseX, mouseY);
        }
    }

    @Override
    public void click(EntryScreen<T> screen, double mouseX, double mouseY) {
        EntryScreen.openScreen(screen, entryChoice.entry());
    }
}
