package com.sammy.malum.client.screen.codex.handklers;

import com.sammy.malum.client.screen.codex.PlacedBookEntry;
import com.sammy.malum.client.screen.codex.objects.progression.ProgressionEntryObject;
import com.sammy.malum.client.screen.codex.screens.AbstractProgressionCodexScreen;

public class EntryObjectHandler extends BookObjectHandler<AbstractProgressionCodexScreen> {
    public EntryObjectHandler() {
        super();
    }

    public void setupEntryObjects(AbstractProgressionCodexScreen screen) {
        clear();
        int left = screen.getGuiLeft() + screen.bookInsideWidth;
        int top = screen.getGuiTop() + screen.bookInsideHeight;
        for (PlacedBookEntry entry : screen.getEntries()) {
            final PlacedBookEntry.BookEntryWidgetPlacementData data = entry.getWidgetData();
            final ProgressionEntryObject bookObject = data.widgetSupplier().getBookObject(entry, left + data.xOffset(), top - data.yOffset());
            var config = data.widgetConfig();
            if (config != null) {
                config.accept(bookObject);
            }
            add(bookObject);
        }
        screen.faceObject(get(1));
    }
}