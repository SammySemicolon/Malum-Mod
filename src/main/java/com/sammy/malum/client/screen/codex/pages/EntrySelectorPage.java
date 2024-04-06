package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.item.*;

import java.util.*;

public class EntrySelectorPage<T extends AbstractProgressionCodexScreen<T>> extends BookPage<T> {

    public final BookObjectHandler<EntryScreen<T>> bookObjectHandler = new BookObjectHandler<>();

    @SafeVarargs
    public EntrySelectorPage(EntryChoice<T>... entries) {
        this(List.of(entries));
    }

    public EntrySelectorPage(List<EntryChoice<T>> entries) {
        super(null);
        for (int i = 0; i < entries.size(); i++) {
            var entry = entries.get(i);
            int xOffset = i % 2 == 1 ? 88 : 24;
            int yOffset = 12 + (i / 2) * 40;
            bookObjectHandler.add(new SelectableEntryObject<>(xOffset, yOffset, entry));
        }
    }

    @Override
    public void render(EntryScreen<T> screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        bookObjectHandler.renderObjects(screen, guiGraphics, left, top, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderLate(EntryScreen<T> screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        bookObjectHandler.renderObjectsLate(screen, guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void click(EntryScreen<T> screen, int left, int top, double mouseX, double mouseY, double relativeMouseX, double relativeMouseY) {
        bookObjectHandler.click(screen, mouseX, mouseY);
    }

    public record EntryChoice<T extends AbstractProgressionCodexScreen<T>>(ItemStack icon, BookEntry<T> entry) {
        public EntryChoice(ItemStack icon, BookEntryBuilder<T> builder) {
            this(icon, builder.build());
        }

        public EntryChoice(Item icon, BookEntryBuilder<T> builder) {
            this(icon.getDefaultInstance(), builder.build());
        }
    }
}