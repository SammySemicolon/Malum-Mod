package com.sammy.malum.client.screen.codex;

import com.google.common.collect.ImmutableList;
import com.sammy.malum.client.screen.codex.objects.progression.ProgressionEntryObject;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.pages.EntryReference;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PlacedBookEntry extends BookEntry {
    private final BookEntryWidgetPlacementData widgetData;

    public PlacedBookEntry(String identifier, boolean isVoid, BookEntryWidgetPlacementData widgetData, ImmutableList<BookPage> bookPages, ImmutableList<EntryReference> entryReferences, Predicate<EntryScreen> validityChecker) {
        super(identifier, isVoid, bookPages, entryReferences, validityChecker);
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