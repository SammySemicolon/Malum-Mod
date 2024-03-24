package com.sammy.malum.client.screen.codex;

import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.screens.*;

public class EntryObjectHandler<T extends AbstractProgressionCodexScreen<T>> extends BookObjectHandler<T>{
    public EntryObjectHandler(T screen) {
        super(screen);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void setupEntryObjects() {
        bookObjects.clear();
        int left = screen.getGuiLeft() + screen.bookInsideWidth;
        int top = screen.getGuiTop() + screen.bookInsideHeight;
        for (BookEntry entry : screen.getEntries()) {
            final ProgressionEntryObject bookObject = entry.widgetSupplier.getBookObject(screen, entry, left + entry.xOffset, top - entry.yOffset);
            if (entry.widgetConfig != null) {
                entry.widgetConfig.accept(bookObject);
            }
            bookObjects.add(bookObject);
        }
        screen.faceObject(bookObjects.get(1));
    }
}
