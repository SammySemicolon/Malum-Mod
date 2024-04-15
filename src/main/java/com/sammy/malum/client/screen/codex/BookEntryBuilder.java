package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;

import java.util.*;

public class BookEntryBuilder<T extends EntryScreen<T, K>, K extends AbstractMalumScreen<K>> {

    protected final String identifier;
    protected final boolean isVoid;

    protected List<BookPage<T>> pages = new ArrayList<>();
    protected List<EntryReference<T, K>> references = new ArrayList<>();

    public BookEntryBuilder(String identifier) {
        this.identifier = identifier;
        this.isVoid = identifier.startsWith("void.");
    }

    public BookEntryBuilder<T, K> addPage(BookPage<T> page) {
        if (page.isValid()) {
            pages.add(page);
        }
        return this;
    }
    public BookEntryBuilder<T, K> addReference(EntryReference<T, K> reference) {
        references.add(reference);
        return this;
    }

    public BookEntry<T, K> build() {
        ImmutableList<BookPage<T>> bookPages = ImmutableList.copyOf(pages);
        ImmutableList<EntryReference<T, K>> entryReferences = ImmutableList.copyOf(references);
        BookEntry<T, K> bookEntry = new BookEntry<>(identifier, isVoid, bookPages, entryReferences);
        bookPages.forEach(p -> p.setBookEntry(bookEntry));
        return bookEntry;
    }
}