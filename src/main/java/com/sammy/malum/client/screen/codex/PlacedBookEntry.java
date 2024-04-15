package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.objects.progression.*;
import com.sammy.malum.client.screen.codex.pages.*;

import java.util.function.*;

public class PlacedBookEntry extends BookEntry {
    private final BookEntryWidgetPlacementData widgetData;

    public PlacedBookEntry(String identifier, boolean isVoid, BookEntryWidgetPlacementData widgetData, ImmutableList<BookPage> bookPages, ImmutableList<EntryReference> entryReferences) {
        super(identifier, isVoid, bookPages, entryReferences);
        this.widgetData = widgetData;
    }

    public BookEntryWidgetPlacementData getWidgetData() {
        return widgetData;
    }

    public interface WidgetSupplier {
        ProgressionEntryObject getBookObject(BookEntry entry, int x, int y);
    }

    public record BookEntryWidgetPlacementData(int xOffset, int yOffset,
                                               WidgetSupplier widgetSupplier,
                                               Consumer<ProgressionEntryObject> widgetConfig) {
    }
}