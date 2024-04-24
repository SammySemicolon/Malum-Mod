package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class BookEntry {

    protected static final Predicate<EntryScreen> IS_REEXAMINATION = t -> CurioHelper.hasCurioEquipped(Minecraft.getInstance().player, ItemRegistry.ORNATE_NECKLACE.get());

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