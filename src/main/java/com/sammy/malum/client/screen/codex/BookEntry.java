package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;

import java.util.function.*;

public class BookEntry<T extends AbstractMalumScreen<T>> {

    public final String identifier;
    public final boolean isVoid;
    public final ImmutableList<BookPage<T>> pages;
    public final ImmutableList<EntryReference<T>> references;
    public Predicate<T> isValid = t -> true;

    public BookEntry(String identifier, boolean isVoid, ImmutableList<BookPage<T>> pages, ImmutableList<EntryReference<T>> references) {
        this.identifier = identifier;
        this.isVoid = isVoid;
        this.pages = pages;
        this.references = references;
    }

    public String translationKey() {
        return "malum.gui.book.entry." + identifier;
    }

    public String descriptionTranslationKey() {
        return "malum.gui.book.entry." + identifier + ".description";
    }

    public boolean isValid(T screen) {
        return isValid.test(screen);
    }

    public static <T extends AbstractProgressionCodexScreen<T>> PlacedBookEntryBuilder<T> build(String identifier, int xOffset, int yOffset) {
        return new PlacedBookEntryBuilder<>(identifier, xOffset, yOffset);
    }

    public static <T extends AbstractMalumScreen<T>> BookEntryBuilder<T> build(String identifier) {
        return new BookEntryBuilder<>(identifier);
    }
}