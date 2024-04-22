package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.pages.*;

import java.util.*;

public class BookEntryBuilder {

    protected final String identifier;
    protected final boolean isVoid;

    protected List<BookPage> pages = new ArrayList<>();
    protected List<EntryReference> references = new ArrayList<>();

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

    public BookEntry build() {
        ImmutableList<BookPage> bookPages = ImmutableList.copyOf(pages);
        ImmutableList<EntryReference> entryReferences = ImmutableList.copyOf(references);
        BookEntry bookEntry = new BookEntry(identifier, isVoid, bookPages, entryReferences);
        bookPages.forEach(p -> p.setBookEntry(bookEntry));
        return bookEntry;
    }
}