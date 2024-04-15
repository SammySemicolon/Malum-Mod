package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.*;
import net.minecraft.world.item.*;

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

    public EntryReference(Item icon, BookEntryBuilder builder) {
        this(icon, builder.build());
    }
}