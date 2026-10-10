package com.sammy.malum.client.screen.codex;

import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedGizmo;
import com.sammy.malum.client.screen.codex.objects.ProgressionEntryObject;

public class PlacedBookEntryBuilder extends BookEntryBuilder {

    protected final int x, y;
    protected DisplayedGizmo icon;
    protected EntryWidgetDesign design = EntryWidgetDesign.MEDIUM;

    protected PlacedBookEntry.BookObjectSupplier objectBuilder = ProgressionEntryObject::new;

    protected PlacedBookEntryBuilder(String identifier, int x, int y) {
        super(identifier);
        this.x = x;
        this.y = y;
    }

    public PlacedBookEntryBuilder setIcon(DisplayedGizmo icon) {
        this.icon = icon;
        return this;
    }

    public PlacedBookEntryBuilder setDesign(EntryWidgetDesign design) {
        this.design = design;
        return this;
    }

    public PlacedBookEntryBuilder setObjectBuilder(PlacedBookEntry.BookObjectSupplier objectBuilder) {
        this.objectBuilder = objectBuilder;
        return this;
    }

    public PlacedBookEntry place() {
        return new PlacedBookEntry(build(), icon, design, objectBuilder, x, y);
    }
}