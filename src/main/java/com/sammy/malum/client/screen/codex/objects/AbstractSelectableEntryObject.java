package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;

import java.util.*;

public abstract class AbstractSelectableEntryObject<T extends EntryScreen<T, K>, K extends AbstractMalumScreen<K>> extends BookObject<T> {

    public final EntryReference<T, K> entryReference;

    public AbstractSelectableEntryObject(int posX, int posY, int width, int height, EntryReference<T, K> entryReference) {
        super(posX, posY, width, height);
        this.entryReference = entryReference;
    }

    @Override
    public void renderLate(T screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (isHoveredOver) {
            final BookEntry<T, K> entry = entryReference.entry;
            final List<Component> list = Arrays.asList(
                    Component.translatable(entry.translationKey()),
                    Component.translatable(entry.descriptionTranslationKey()).withStyle(ChatFormatting.GRAY));
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, list, mouseX, mouseY);
        }
    }

    @Override
    public void click(T screen, double mouseX, double mouseY) {
        EntryScreen.openScreen(screen, entryReference.entry);
    }
}
