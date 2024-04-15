package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.objects.progression.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;

import java.util.function.*;

public class PlacedBookEntry<T extends AbstractProgressionCodexScreen<T>> extends BookEntry<T> {
    private final BookEntryWidgetPlacementData<T> widgetData;

    public PlacedBookEntry(String identifier, boolean isVoid, BookEntryWidgetPlacementData<T> widgetData, ImmutableList<BookPage<T>> bookPages, ImmutableList<EntryReference<T>> entryReferences) {
        super(identifier, isVoid, bookPages, entryReferences);
        this.widgetData = widgetData;
    }

    public BookEntryWidgetPlacementData<T> getWidgetData() {
        return widgetData;
    }

    public interface WidgetSupplier<T extends AbstractProgressionCodexScreen<T>> {
        ProgressionEntryObject<T> getBookObject(BookEntry<T> entry, int x, int y);
    }

    public record BookEntryWidgetPlacementData<T extends AbstractProgressionCodexScreen<T>>(int xOffset, int yOffset, WidgetSupplier<T> widgetSupplier, Consumer<ProgressionEntryObject<T>> widgetConfig) { }
}
