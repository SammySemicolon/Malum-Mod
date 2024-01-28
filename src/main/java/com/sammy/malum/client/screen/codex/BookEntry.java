package com.sammy.malum.client.screen.codex;

import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class BookEntry<T extends AbstractProgressionCodexScreen> {
    public final String identifier;
    public final int xOffset;
    public final int yOffset;
    public List<BookPage> pages = new ArrayList<>();
    public WidgetSupplier<T> widgetSupplier = EntryObject::new;
    public Consumer<EntryObject<T>> widgetConfig;

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

    public BookEntry<T> addPage(BookPage page) {
        if (page.isValid()) {
            pages.add(page.setParentEntry(this));
        }
        return this;
    }

    public BookEntry<T> setWidgetInfo(WidgetSupplier<T> widgetSupplier, Consumer<EntryObject<T>> widgetConfig) {
        return setWidgetSupplier(widgetSupplier).setWidgetConfig(widgetConfig);
    }

    public BookEntry<T> setWidgetSupplier(WidgetSupplier<T> widgetSupplier) {
        this.widgetSupplier = widgetSupplier;
        return this;
    }

    public BookEntry<T> setWidgetConfig(Consumer<EntryObject<T>> widgetConfig) {
        this.widgetConfig = widgetConfig;
        return this;
    }

    public interface WidgetSupplier<T extends AbstractProgressionCodexScreen> {
        EntryObject<T> getBookObject(T screen, BookEntry<T> entry, int x, int y);
    }
}