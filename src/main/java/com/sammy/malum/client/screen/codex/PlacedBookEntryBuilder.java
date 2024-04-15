package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.objects.progression.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;

import java.util.function.*;

public class PlacedBookEntryBuilder<T extends EntryScreen<T, K>, K extends AbstractProgressionCodexScreen<K>> extends BookEntryBuilder<T, K> {

    protected PlacedBookEntry.WidgetSupplier<K> widgetSupplier = ProgressionEntryObject::new;
    protected Consumer<ProgressionEntryObject<K>> widgetConfig;

    protected final int xOffset;
    protected final int yOffset;

    public PlacedBookEntryBuilder(String identifier, int xOffset, int yOffset) {
        super(identifier);
        this.xOffset = xOffset*40;
        this.yOffset = yOffset*40;
    }

    public PlacedBookEntryBuilder<T, K> setWidgetSupplier(PlacedBookEntry.WidgetSupplier<K> widgetSupplier) {
        this.widgetSupplier = widgetSupplier;
        return this;
    }

    public PlacedBookEntryBuilder<T, K> setWidgetConfig(Consumer<ProgressionEntryObject<K>> widgetConfig) {
        this.widgetConfig = widgetConfig;
        return this;
    }

    @Override
    public PlacedBookEntry<T, K> build() {
        ImmutableList<BookPage<T>> bookPages = ImmutableList.copyOf(pages);
        ImmutableList<EntryReference<T, K>> entryReferences = ImmutableList.copyOf(references);
        PlacedBookEntry.BookEntryWidgetPlacementData<K> data = new PlacedBookEntry.BookEntryWidgetPlacementData<>(xOffset, yOffset, widgetSupplier, widgetConfig);
        PlacedBookEntry<T, K> bookEntry = new PlacedBookEntry<>(identifier, isVoid, data, bookPages, entryReferences);
        bookPages.forEach(p -> p.setBookEntry(bookEntry));
        return bookEntry;
    }
}