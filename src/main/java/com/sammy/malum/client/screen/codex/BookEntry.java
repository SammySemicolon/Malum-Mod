package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class BookEntry<T extends AbstractProgressionCodexScreen<T>> {

    public final String identifier;
    public final boolean isVoid;
    @Nullable
    private final BookEntryWidgetPlacementData<T> widgetData;
    public final ImmutableList<BookPage<T>> pages;

    public BookEntry(String identifier, boolean isVoid, @Nullable BookEntryWidgetPlacementData<T> widgetData, ImmutableList<BookPage<T>> pages) {
        this.identifier = identifier;
        this.isVoid = isVoid;
        this.widgetData = widgetData;
        this.pages = pages;
    }

    public Optional<BookEntryWidgetPlacementData<T>> getWidgetData() {
        return Optional.ofNullable(widgetData);
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
    public static<T extends AbstractProgressionCodexScreen<T>> BookEntryBuilder<T> build(String identifier) {
        return new BookEntryBuilder<>(identifier);
    }

    public interface WidgetSupplier<T extends AbstractProgressionCodexScreen<T>> {
        ProgressionEntryObject<T> getBookObject(BookEntry<T> entry, int x, int y);
    }

    public record BookEntryWidgetPlacementData<T extends AbstractProgressionCodexScreen<T>>(int xOffset, int yOffset, WidgetSupplier<T> widgetSupplier, Consumer<ProgressionEntryObject<T>> widgetConfig) {
    }
}