package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.world.item.*;

public final class EntryReference<T extends AbstractMalumScreen<T>> {
    public final ItemStack icon;
    public final BookEntry<T> entry;

    public EntryReference(ItemStack icon, BookEntry<T> entry) {
        this.icon = icon;
        this.entry = entry;
    }

    public EntryReference(ItemStack icon, BookEntryBuilder<T> builder) {
        this(icon, builder.build());
    }

    public EntryReference(Item icon, BookEntryBuilder<T> builder) {
        this(icon.getDefaultInstance(), builder);
    }
}