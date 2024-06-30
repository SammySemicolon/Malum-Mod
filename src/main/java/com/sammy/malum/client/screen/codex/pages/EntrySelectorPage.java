package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.handlers.BookObjectHandler;
import com.sammy.malum.client.screen.codex.objects.SelectableEntryObject;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import net.minecraft.client.gui.GuiGraphics;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntrySelectorPage extends BookPage {

    public final BookObjectHandler<EntryScreen> bookObjectHandler = new BookObjectHandler<>();

    @SafeVarargs
    public <J> EntrySelectorPage(Function<J, EntryReference> mapper, J... objects) {
        this(mapper, List.of(objects));
    }

    public <J> EntrySelectorPage(Function<J, EntryReference> mapper, Collection<J> objects) {
        this(objects.stream().map(mapper).collect(Collectors.toList()));
    }

    public EntrySelectorPage(EntryReference... entries) {
        this(List.of(entries));
    }

    public EntrySelectorPage(List<EntryReference> entries) {
        super(null);
        boolean isWide = entries.size() > 4;
        for (int i = 0; i < entries.size(); i++) {
            var entry = entries.get(i);
            int xOffset = isWide ? (i % 2 == 1 ? 88 : 24) : 66;
            int yOffset = 12 + (isWide ? i / 2 : i) * 40;
            bookObjectHandler.add(new SelectableEntryObject(xOffset, yOffset, entry));
        }
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        bookObjectHandler.renderObjects(screen, guiGraphics, left, top, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderLate(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        bookObjectHandler.renderObjectsLate(screen, guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void click(EntryScreen screen, int left, int top, double mouseX, double mouseY, double relativeMouseX, double relativeMouseY) {
        bookObjectHandler.click(screen, mouseX, mouseY);
    }
}