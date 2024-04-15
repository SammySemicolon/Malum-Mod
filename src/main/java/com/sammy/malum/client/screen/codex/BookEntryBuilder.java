package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;

import java.util.*;

public class BookEntryBuilder<T extends AbstractMalumScreen<T>> {

    protected final String identifier;
    protected final boolean isVoid;

    protected int xOffset;
    protected int yOffset;

    protected List<BookPage<T>> pages = new ArrayList<>();
    protected List<EntryReference<T>> references = new ArrayList<>();

    public BookEntryBuilder(String identifier) {
        this.identifier = identifier;
        this.isVoid = identifier.startsWith("void.");
    }

    public BookEntryBuilder(String identifier, int xOffset, int yOffset) {
        this(identifier);
        this.xOffset = xOffset*40;
        this.yOffset = yOffset*40;
    }

    public BookEntryBuilder<T> addPage(BookPage<T> page) {
        if (page.isValid()) {
            pages.add(page);
        }
        return this;
    }
    public BookEntryBuilder<T> addReference(EntryReference<T> reference) {
        references.add(reference);
        return this;
    }

    public BookEntry<T> build() {
        ImmutableList<BookPage<T>> bookPages = ImmutableList.copyOf(pages);
        ImmutableList<EntryReference<T>> entryReferences = ImmutableList.copyOf(references);
        BookEntry<T> bookEntry = new BookEntry<>(identifier, isVoid, bookPages, entryReferences);
        bookPages.forEach(p -> p.setBookEntry(bookEntry));
        return bookEntry;
    }
}