package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.world.item.*;

public final class EntryReference<T extends EntryScreen<T, K>, K extends AbstractMalumScreen<K>> {
    public final ItemStack icon;
    public final BookEntry<T, K> entry;

    public EntryReference(ItemStack icon, BookEntry<T, K> entry) {
        this.icon = icon;
        this.entry = entry;
    }

    public EntryReference(ItemStack icon, BookEntryBuilder<T, K> builder) {
        this(icon, builder.build());
    }

    public EntryReference(Item icon, BookEntryBuilder<T, K> builder) {
        this(icon.getDefaultInstance(), builder);
    }
}