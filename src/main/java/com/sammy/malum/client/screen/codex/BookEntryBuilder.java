package com.sammy.malum.client.screen.codex;

import com.google.common.collect.ImmutableList;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.pages.EntryReference;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BookEntryBuilder {

    protected final String identifier;
    protected final boolean isVoid;

    protected List<BookPage> pages = new ArrayList<>();
    protected List<EntryReference> references = new ArrayList<>();
    protected Predicate<EntryScreen> validityChecker = t -> true;

    public BookEntryBuilder(String identifier) {
        this.identifier = identifier;
        this.isVoid = identifier.startsWith("void.");
    }

    public BookEntryBuilder addPage(BookPage page) {
        if (page.isValid()) {
            pages.add(page);
        }
        return this;
    }

    public BookEntryBuilder addReference(EntryReference reference) {
        references.add(reference);
        return this;
    }

    public BookEntryBuilder setValidityChecker(Predicate<EntryScreen> validityChecker) {
        this.validityChecker = validityChecker;
        return this;
    }

    public BookEntryBuilder afterUmbralCrystal() {
        this.validityChecker = BookEntry.AFTER_UMBRAL_CRYSTAL;
        return this;
    }

    public BookEntry build() {
        ImmutableList<BookPage> bookPages = ImmutableList.copyOf(pages);
        ImmutableList<EntryReference> entryReferences = ImmutableList.copyOf(references);
        BookEntry bookEntry = new BookEntry(identifier, isVoid, bookPages, entryReferences, validityChecker);
        bookPages.forEach(p -> p.setBookEntry(bookEntry));
        return bookEntry;
    }
}
