package com.sammy.malum.client.screen.codex;

import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class BookEntry {
    public final String identifier;
    public final int xOffset;
    public final int yOffset;
    public List<BookPage> pages = new ArrayList<>();
    public WidgetSupplier widgetSupplier = EntryObject::new;
    public Consumer<EntryObject> widgetConfig;

    public BookEntry(String identifier, int xOffset, int yOffset) {
        this.identifier = identifier;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public String translationKey() {
        return "malum.gui.book.entry." + identifier;
    }

    public String descriptionTranslationKey() {
        return "malum.gui.book.entry." + identifier + ".description";
    }

    public BookEntry addPage(BookPage page) {
        if (page.isValid()) {
            pages.add(page.setParentEntry(this));
        }
        return this;
    }

    public BookEntry addModCompatPage(BookPage page, String modId) {
        if (ModList.get().isLoaded(modId)) {
            addPage(page);
        }
        return this;
    }

    public BookEntry setWidgetInfo(WidgetSupplier widgetSupplier, Consumer<EntryObject> widgetConfig) {
        return setWidgetSupplier(widgetSupplier).setWidgetConfig(widgetConfig);
    }

    public BookEntry setWidgetSupplier(WidgetSupplier widgetSupplier) {
        this.widgetSupplier = widgetSupplier;
        return this;
    }

    public BookEntry setWidgetConfig(Consumer<EntryObject> widgetConfig) {
        this.widgetConfig = widgetConfig;
        return this;
    }

    public interface WidgetSupplier {
        EntryObject getBookObject(AbstractProgressionCodexScreen screen, BookEntry entry, int x, int y);
    }
}