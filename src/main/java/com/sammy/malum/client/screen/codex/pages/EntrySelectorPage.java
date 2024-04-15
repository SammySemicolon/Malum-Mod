package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.handklers.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class EntrySelectorPage<T extends AbstractProgressionCodexScreen<T>> extends BookPage<T> {

    public final BookObjectHandler<EntryScreen<T>> bookObjectHandler = new BookObjectHandler<>();

    @SafeVarargs
    public <K> EntrySelectorPage(Function<K, EntryReference<T>> mapper, K... objects) {
        this(mapper, List.of(objects));
    }

    public <K> EntrySelectorPage(Function<K, EntryReference<T>> mapper, Collection<K> objects) {
        this(objects.stream().map(mapper).collect(Collectors.toList()));
    }

    @SafeVarargs
    public EntrySelectorPage(EntryReference<T>... entries) {
        this(List.of(entries));
    }

    public EntrySelectorPage(List<EntryReference<T>> entries) {
        super(null);
        boolean isWide = entries.size() > 4;
        for (int i = 0; i < entries.size(); i++) {
            var entry = entries.get(i);
            int xOffset = isWide ? (i % 2 == 1 ? 88 : 24) : 66;
            int yOffset = 12 + (isWide ? i / 2 : i) * 40;
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

}