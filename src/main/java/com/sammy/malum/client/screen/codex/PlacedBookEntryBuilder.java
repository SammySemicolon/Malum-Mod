package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.objects.progression.*;
import com.sammy.malum.client.screen.codex.pages.*;

import java.util.function.*;

public class PlacedBookEntryBuilder extends BookEntryBuilder {

    protected PlacedBookEntry.WidgetSupplier widgetSupplier = ProgressionEntryObject::new;
    protected Consumer<ProgressionEntryObject> widgetConfig;

    protected final int xOffset;
    protected final int yOffset;

    public PlacedBookEntryBuilder(String identifier, int xOffset, int yOffset) {
        super(identifier);
        this.xOffset = xOffset*40;
        this.yOffset = yOffset*40;
    }

    public PlacedBookEntryBuilder setWidgetSupplier(PlacedBookEntry.WidgetSupplier widgetSupplier) {
        this.widgetSupplier = widgetSupplier;
        return this;
    }

    public PlacedBookEntryBuilder setWidgetConfig(Consumer<ProgressionEntryObject> widgetConfig) {
        this.widgetConfig = widgetConfig;
        return this;
    }

    @Override
    public PlacedBookEntry build() {
        ImmutableList<BookPage> bookPages = ImmutableList.copyOf(pages);
        ImmutableList<EntryReference> entryReferences = ImmutableList.copyOf(references);
        PlacedBookEntry.BookEntryWidgetPlacementData data = new PlacedBookEntry.BookEntryWidgetPlacementData(xOffset, yOffset, widgetSupplier, widgetConfig);
        PlacedBookEntry bookEntry = new PlacedBookEntry(identifier, isVoid, data, bookPages, entryReferences);
        bookPages.forEach(p -> p.setBookEntry(bookEntry));
        return bookEntry;
    }
}