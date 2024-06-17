package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;

import java.util.*;

public abstract class AbstractSelectableEntryObject extends BookObject<EntryScreen> {

    public final EntryReference entryReference;

    public AbstractSelectableEntryObject(int posX, int posY, int width, int height, EntryReference entryReference) {
        super(posX, posY, width, height);
        this.entryReference = entryReference;
    }

    @Override
    public void renderLate(EntryScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (isHoveredOver) {
            final BookEntry entry = entryReference.entry;
            final List<Component> list = Arrays.asList(
                ArcanaCodexHelper.convertToComponent(entry.translationKey()),
                ArcanaCodexHelper.convertToComponent(entry.descriptionTranslationKey(), (style) -> style.withColor(ChatFormatting.GRAY)));
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, list, mouseX, mouseY);
        }
    }

    @Override
    public void click(EntryScreen screen, double mouseX, double mouseY) {
        EntryScreen.openScreen(screen, entryReference.entry);
    }
}
