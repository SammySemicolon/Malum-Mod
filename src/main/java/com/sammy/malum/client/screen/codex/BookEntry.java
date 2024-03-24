package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;

import java.util.function.*;

public class BookEntry<T extends AbstractProgressionCodexScreen<T>> {
    public final String identifier;
    public final int xOffset;
    public final int yOffset;
    public final ImmutableList<BookPage<T>> pages;
    public final WidgetSupplier<T> widgetSupplier;
    public final Consumer<ProgressionEntryObject<T>> widgetConfig;

    public BookEntry(String identifier, int xOffset, int yOffset, ImmutableList<BookPage<T>> pages, WidgetSupplier<T> widgetSupplier, Consumer<ProgressionEntryObject<T>> widgetConfig) {
        this.identifier = identifier;
        this.xOffset = xOffset * 40;
        this.yOffset = yOffset * 40;
        this.pages = pages;
        this.widgetSupplier = widgetSupplier;
        this.widgetConfig = widgetConfig;
    }

    public String translationKey() {
        return "malum.gui.book.entry." + identifier;
    }

    public String descriptionTranslationKey() {
        return "malum.gui.book.entry." + identifier + ".description";
    }

    public static<T extends AbstractProgressionCodexScreen<T>> BookEntryBuilder<T> build(String identifier, int xOffset, int yOffset) {
        return new BookEntryBuilder<>(identifier, xOffset, yOffset);
    }

    public interface WidgetSupplier<T extends AbstractProgressionCodexScreen<T>> {
        ProgressionEntryObject<T> getBookObject(T screen, BookEntry<T> entry, int x, int y);
    }
}