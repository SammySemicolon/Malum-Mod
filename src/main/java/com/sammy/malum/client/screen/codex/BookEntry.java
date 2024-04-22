package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;

import java.util.function.*;

public class BookEntry {

    public final String identifier;
    public final boolean isVoid;
    public final ImmutableList<BookPage> pages;
    public final ImmutableList<EntryReference> references;
    public Predicate<EntryScreen> isValid = t -> true;

    public BookEntry(String identifier, boolean isVoid, ImmutableList<BookPage> pages, ImmutableList<EntryReference> references) {
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

    public boolean isValid(EntryScreen screen) {
        return isValid.test(screen);
    }

    public static PlacedBookEntryBuilder build(String identifier, int xOffset, int yOffset) {
        return new PlacedBookEntryBuilder(identifier, xOffset, yOffset);
    }

    public static BookEntryBuilder build(String identifier) {
        return new BookEntryBuilder(identifier);
    }
}