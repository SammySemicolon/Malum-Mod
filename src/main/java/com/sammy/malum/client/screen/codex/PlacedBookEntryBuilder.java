package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.objects.progression.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;

import java.util.function.*;

public class PlacedBookEntryBuilder<T extends AbstractProgressionCodexScreen<T>> extends BookEntryBuilder<T> {

    protected PlacedBookEntry.WidgetSupplier<T> widgetSupplier;
    protected Consumer<ProgressionEntryObject<T>> widgetConfig;

    public PlacedBookEntryBuilder(String identifier) {
        super(identifier);
    }

    public PlacedBookEntryBuilder(String identifier, int xOffset, int yOffset) {
        super(identifier, xOffset, yOffset);
    }

    public PlacedBookEntryBuilder<T> setWidgetSupplier(PlacedBookEntry.WidgetSupplier<T> widgetSupplier) {
        this.widgetSupplier = widgetSupplier;
        return this;
    }

    public PlacedBookEntryBuilder<T> setWidgetConfig(Consumer<ProgressionEntryObject<T>> widgetConfig) {
        this.widgetConfig = widgetConfig;
        return this;
    }

    @Override
    public BookEntry<T> build() {
        ImmutableList<BookPage<T>> bookPages = ImmutableList.copyOf(pages);
        ImmutableList<EntryReference<T>> entryReferences = ImmutableList.copyOf(references);
        PlacedBookEntry.BookEntryWidgetPlacementData<T> data = widgetSupplier != null ? new PlacedBookEntry.BookEntryWidgetPlacementData<>(xOffset, yOffset, widgetSupplier, widgetConfig) : null;
        PlacedBookEntry<T> bookEntry = new PlacedBookEntry<>(identifier, isVoid, data, bookPages, entryReferences);
        bookPages.forEach(p -> p.setBookEntry(bookEntry));
        return bookEntry;
    }
}