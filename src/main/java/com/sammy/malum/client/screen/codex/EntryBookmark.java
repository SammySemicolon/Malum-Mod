package com.sammy.malum.client.screen.codex;

import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedGizmo;
import net.minecraft.*;
import net.minecraft.network.chat.*;

public final class EntryBookmark {

    public final DisplayedGizmo icon;
    public final BookEntry entry;

    public EntryBookmark(DisplayedGizmo icon, BookEntry entry) {
        this.icon = icon;
        this.entry = entry;
    }

    public EntryBookmark(DisplayedGizmo icon, BookEntryBuilder builder) {
        this(icon, builder.build());
    }

    public MutableComponent getComponent() {
        var slash = Component.literal("┇ ");
        var text = Component.translatable(entry.translationKey());
        return slash.append(text).withStyle(ChatFormatting.DARK_GRAY);
    }
}