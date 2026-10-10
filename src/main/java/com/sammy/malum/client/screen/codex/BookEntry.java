package com.sammy.malum.client.screen.codex;

import com.google.common.collect.ImmutableList;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import net.minecraft.network.chat.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.*;

public class BookEntry {

    public final String identifier;
    public final ImmutableList<BookPage> pages;
    public final ImmutableList<EntryBookmark> leftBookmarks;
    public final ImmutableList<EntryBookmark> rightBookmarks;
    private final List<String> predecessors;

    public BookEntry(String identifier, ImmutableList<BookPage> pages, ImmutableList<EntryBookmark> leftBookmarks, ImmutableList<EntryBookmark> rightBookmarks, List<String> predecessors) {
        this.identifier = identifier;
        this.pages = pages;
        this.leftBookmarks = leftBookmarks;
        this.rightBookmarks = rightBookmarks;
        this.predecessors = predecessors;
    }

    public String translationKey() {
        return "malum.gui.book.entry." + identifier;
    }

    public String descriptionTranslationKey() {
        return "malum.gui.book.entry." + identifier + ".subtext";
    }

    public boolean hasContents() {
        return !pages.isEmpty();
    }

    public boolean shouldShow() {
        return true;
    }

    public static BookEntryBuilder create(String identifier) {
        return new BookEntryBuilder(identifier);
    }

    public void addBookmarks(Consumer<MutableComponent> acceptor) {
        addBookmarks(leftBookmarks, acceptor);
        addBookmarks(rightBookmarks, acceptor);
    }

    private void addBookmarks(List<EntryBookmark> bookmarks, Consumer<MutableComponent> acceptor) {
        for (int i = bookmarks.size()-1; i >=0; i--) {
            var bookmark = bookmarks.get(i);
            if (bookmark.entry.shouldShow()) {
                acceptor.accept(bookmark.getComponent());
            }
        }
    }
}
