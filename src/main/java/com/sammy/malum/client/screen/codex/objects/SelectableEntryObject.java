package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.display.*;
import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedGizmo;
import com.sammy.malum.client.screen.codex.display.gizmo.GizmoTooltipBuilder;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.client.screen.codex.screens.progression.AbstractProgressionCodexScreen;

public abstract class SelectableEntryObject<T extends AbstractMalumCodexScreen> extends BookObject<T> implements IGizmoHolder {

    public final BookEntry entry;
    public final DisplayedGizmo icon;

    public SelectableEntryObject(PlacedBookEntry entry, int width, int height) {
        this(entry.getEntry(), entry.getIcon(), entry.getX(), entry.getY(), width, height);
    }

    public SelectableEntryObject(BookEntry entry, DisplayedGizmo icon, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.entry = entry;
        this.icon = icon;
    }

    public void additionalSetup(AbstractProgressionCodexScreen screen) {

    }

    @Override
    public void addGizmoTooltip(GizmoTooltipBuilder builder) {
        builder.addTitle(entry.translationKey());
        builder.addSubtext(entry.descriptionTranslationKey());
    }

    @Override
    public boolean click(T screen, double mouseX, double mouseY) {
        if (entry.hasContents()) {
            CodexEntryScreen.openScreen(entry);
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldGizmoBeConsideredHoveredOver() {
        return isHoveredOver;
    }
}