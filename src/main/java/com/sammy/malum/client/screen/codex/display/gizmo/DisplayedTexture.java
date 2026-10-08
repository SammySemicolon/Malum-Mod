package com.sammy.malum.client.screen.codex.display.gizmo;

import com.sammy.malum.client.screen.codex.display.CodexIconRenderer;
import com.sammy.malum.client.screen.codex.display.IGizmoHolder;
import com.sammy.malum.client.screen.codex.screens.AbstractMalumCodexScreen;
import net.minecraft.client.gui.GuiGraphics;

public class DisplayedTexture extends DisplayedGizmo {

    protected final CodexIconRenderer renderer;

    public DisplayedTexture(CodexIconRenderer renderer) {
        this.renderer = renderer;
    }

    public static DisplayedTexture texture(CodexIconRenderer renderer) {
        return new DisplayedTexture(renderer);
    }

    @Override
    public void renderDecals(AbstractMalumCodexScreen screen, IGizmoHolder holder, GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, float partialTicks) {
        renderer.renderIcon(guiGraphics.pose(), x, y);
    }
}