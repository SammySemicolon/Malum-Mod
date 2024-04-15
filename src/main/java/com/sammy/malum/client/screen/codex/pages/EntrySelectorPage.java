package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.handklers.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class EntrySelectorPage<T extends EntryScreen<T, K>, K extends AbstractMalumScreen<K>> extends BookPage<T> {

    public final BookObjectHandler<T> bookObjectHandler = new BookObjectHandler<>();

    @SafeVarargs
    public <J> EntrySelectorPage(Function<J, EntryReference<T, K>> mapper, J... objects) {
        this(mapper, List.of(objects));
    }

    public <J> EntrySelectorPage(Function<J, EntryReference<T, K>> mapper, Collection<J> objects) {
        this(objects.stream().map(mapper).collect(Collectors.toList()));
    }

    @SafeVarargs
    public EntrySelectorPage(EntryReference<T, K>... entries) {
        this(List.of(entries));
    }

    public EntrySelectorPage(List<EntryReference<T, K>> entries) {
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
    public void render(T screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        bookObjectHandler.renderObjects(screen, guiGraphics, left, top, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderLate(T screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        bookObjectHandler.renderObjectsLate(screen, guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void click(T screen, int left, int top, double mouseX, double mouseY, double relativeMouseX, double relativeMouseY) {
        bookObjectHandler.click(screen, mouseX, mouseY);
    }
}