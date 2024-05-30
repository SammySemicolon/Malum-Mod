package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.BookEntryBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public final class EntryReference {
    public final ItemStack icon;
    public final BookEntry entry;

    public EntryReference(ItemStack icon, BookEntry entry) {
        this.icon = icon;
        this.entry = entry;
    }

    public EntryReference(Item icon, BookEntry entry) {
        this(icon.getDefaultInstance(), entry);
    }

    public EntryReference(ItemStack icon, BookEntryBuilder builder) {
        this(icon, builder.build());
    }

    public EntryReference(Supplier<? extends Item> icon, BookEntry entry) {
        this(icon.get(), entry);
    }

    public EntryReference(Item icon, BookEntryBuilder builder) {
        this(icon, builder.build());
    }

    public EntryReference(Supplier<? extends Item> icon, BookEntryBuilder builder) {
        this(icon.get(), builder);
    }
}
