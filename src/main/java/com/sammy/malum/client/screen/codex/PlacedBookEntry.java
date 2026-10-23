package com.sammy.malum.client.screen.codex;

import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedGizmo;
import com.sammy.malum.client.screen.codex.objects.ProgressionEntryObject;

public class PlacedBookEntry {

    private final BookEntry entry;
    private final DisplayedGizmo icon;
    private final EntryWidgetDesign design;
    private final BookObjectSupplier objectBuilder;

    private final int x, y;

    public PlacedBookEntry(BookEntry entry, DisplayedGizmo icon, EntryWidgetDesign design, BookObjectSupplier objectBuilder, int x, int y) {
        this.entry = entry;
        this.icon = icon;
        this.design = design;
        this.objectBuilder = objectBuilder;
        this.x = x;
        this.y = y;
    }

    public BookEntry getEntry() {
        return entry;
    }

    public DisplayedGizmo getIcon() {
        return icon;
    }

    public EntryWidgetDesign getDesign() {
        return design;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static PlacedBookEntryBuilder create(String identifier, int x, int y) {
        return new PlacedBookEntryBuilder(identifier, x, y);
    }

    public ProgressionEntryObject createBookObject() {
        return objectBuilder.createBookObject(this);
    }

    public interface BookObjectSupplier {
        ProgressionEntryObject createBookObject(PlacedBookEntry entry);
    }
}
