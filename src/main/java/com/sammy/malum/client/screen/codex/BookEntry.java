package com.sammy.malum.client.screen.codex;

import com.google.common.collect.ImmutableList;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.pages.EntryReference;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import com.sammy.malum.registry.client.HiddenTagRegistry;

import java.util.function.Predicate;

public class BookEntry {

    protected static final Predicate<EntryScreen> AFTER_UMBRAL_CRYSTAL = t -> HiddenTagRegistry.hasBlackCrystal();

    public final String identifier;
    public final boolean isVoid;
    public final ImmutableList<BookPage> pages;
    public final ImmutableList<EntryReference> references;
    public final Predicate<EntryScreen> validityChecker;

    public BookEntry(String identifier, boolean isVoid, ImmutableList<BookPage> pages, ImmutableList<EntryReference> references, Predicate<EntryScreen> validityChecker) {
        this.identifier = identifier;
        this.isVoid = isVoid;
        this.pages = pages;
        this.references = references;
        this.validityChecker = validityChecker;
    }

    public String translationKey() {
        return "malum.gui.book.entry." + identifier;
    }

    public String descriptionTranslationKey() {
        return "malum.gui.book.entry." + identifier + ".description";
    }

    public boolean isValid(EntryScreen screen) {
        return validityChecker.test(screen);
    }

    public static PlacedBookEntryBuilder build(String identifier, int xOffset, int yOffset) {
        return new PlacedBookEntryBuilder(identifier, xOffset, yOffset);
    }

    public static BookEntryBuilder build(String identifier) {
        return new BookEntryBuilder(identifier);
    }
}
