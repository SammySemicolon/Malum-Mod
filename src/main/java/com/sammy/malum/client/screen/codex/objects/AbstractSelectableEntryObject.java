package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.pages.EntryReference;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractSelectableEntryObject extends BookObject<EntryScreen> {

    public final EntryReference entryReference;

    public AbstractSelectableEntryObject(int posX, int posY, int width, int height, EntryReference entryReference) {
        super(posX, posY, width, height);
        this.entryReference = entryReference;
    }

    @Override
    public void renderLate(EntryScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (isHoveredOver && entryReference.entry.hasTooltip()) {
            final BookEntry entry = entryReference.entry;
            final List<Component> list = Arrays.asList(
                    ArcanaCodexHelper.convertToComponent(entry.translationKey(), entry.titleStyle),
                    ArcanaCodexHelper.convertToComponent(entry.descriptionTranslationKey(), entry.subtitleStyle));
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, list, mouseX, mouseY);
        }
    }

    @Override
    public void click(EntryScreen screen, double mouseX, double mouseY) {
        if (entryReference.entry.hasContents()) {
            EntryScreen.openScreen(screen, entryReference.entry);
        }
    }
}
