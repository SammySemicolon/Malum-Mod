package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.BookEntry;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.*;

public class VanishingEntryObject extends EntryObject
{
    public VanishingEntryObject(BookEntry entry, int posX, int posY) {
        super(entry, posX, posY);
    }

    @Override
    public void exit() {
        objects.remove(this);
    }
}
