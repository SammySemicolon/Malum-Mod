package com.sammy.malum.client.screen.codex.objects.button;

import com.sammy.malum.client.screen.codex.display.*;
import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedGizmo;
import com.sammy.malum.client.screen.codex.display.gizmo.GizmoTooltipBuilder;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.CodexEntryScreen;

import static com.sammy.malum.client.screen.codex.helper.CodexRenderHelper.renderTexture;

public class PageSelectionObject extends ButtonObject {

    protected final PageSelectionPage page;

    public PageSelectionObject(PageSelectionPage page, DisplayedGizmo gizmo, int index, int posX, int posY) {
        super(gizmo, index, posX, posY);
        this.page = page;
    }

    @Override
    public boolean release(CodexEntryScreen screen, double mouseX, double mouseY) {
        boolean release = super.release(screen, mouseX, mouseY);
        page.setIndex(buttonIndex);
        return release;
    }

    @Override
    public boolean isSelected() {
        return page.getIndex() == buttonIndex;
    }

    @Override
    public void addGizmoTooltip(GizmoTooltipBuilder builder) {
        var associatedPage = page.pages.get(buttonIndex);
        if (associatedPage instanceof IGizmoHolder holder) {
            holder.addGizmoTooltip(builder);
        }
        if (builder.isEmpty()) {
            var id = page.getId() + "." + buttonIndex;
            builder.addDefaultTitle(id);
            builder.addDefaultSubtext(id);
        }
    }
}