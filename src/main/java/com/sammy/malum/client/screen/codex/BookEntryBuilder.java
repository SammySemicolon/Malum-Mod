package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;

import java.util.*;
import java.util.function.*;

public class BookEntryBuilder<T extends AbstractProgressionCodexScreen<T>> {

    protected final String identifier;
    protected final int xOffset;
    protected final int yOffset;
    protected List<BookPage<T>> pages = new ArrayList<>();
    protected BookEntry.WidgetSupplier<T> widgetSupplier = ProgressionEntryObject::new;;
    protected Consumer<ProgressionEntryObject<T>> widgetConfig;

    public BookEntryBuilder(String identifier, int xOffset, int yOffset) {
        this.identifier = identifier;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public BookEntryBuilder<T> addPage(BookPage<T> page) {
        if (page.isValid()) {
            pages.add(page);
        }
        return this;
    }

    public BookEntryBuilder<T> setWidgetSupplier(BookEntry.WidgetSupplier<T> widgetSupplier) {
        this.widgetSupplier = widgetSupplier;
        return this;
    }

    public BookEntryBuilder<T> setWidgetConfig(Consumer<ProgressionEntryObject<T>> widgetConfig) {
        this.widgetConfig = widgetConfig;
        return this;
    }

    public BookEntry<T> build() {
        final ImmutableList<BookPage<T>> bookPages = ImmutableList.copyOf(pages);
        final BookEntry<T> bookEntry = new BookEntry<>(identifier, xOffset, yOffset, bookPages, widgetSupplier, widgetConfig);
        bookPages.forEach(p -> p.setBookEntry(bookEntry));
        return bookEntry;
    }
}
