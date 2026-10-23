package com.sammy.malum.client.screen.codex.display.gizmo;

import com.sammy.malum.client.screen.codex.display.IGizmoHolder;
import com.sammy.malum.client.screen.codex.screens.AbstractMalumCodexScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.*;

public abstract class DisplayedGizmo {

    public static String title(String key) {
        return "malum.gui.book.gizmo." + key + ".title";
    }

    public static String subtext(String key) {
        return "malum.gui.book.gizmo." + key + ".subtext";
    }

    protected boolean isHoveredOver;
    protected Color color = Color.WHITE;
    protected int width = 16, height = 16;

    public final void render(AbstractMalumCodexScreen screen, IGizmoHolder holder, GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, float partialTicks) {
        if (!isHoveredOver) {
            isHoveredOver = switch (holder.updateHoverCondition(this)) {
                case DEFAULT -> screen.isHovering(mouseX, mouseY, x, y, width, height);
                case ALLOW -> true;
                case DENY -> false;
            };
        }
        renderDecals(screen, holder, guiGraphics, x, y, mouseX, mouseY, partialTicks);
        if (holder.shouldGizmoRenderTooltip() && isHoveredOver) {
            var tooltip = new ArrayList<Component>();
            var builder = new GizmoTooltipBuilder(tooltip);
            holder.addGizmoTooltip(builder);
            if (tooltip.isEmpty()) {
                gatherTooltip(holder, builder);
            }
            screen.renderTooltip(tooltip);
        }
        resetValues();
    }

    public void gatherTooltip(IGizmoHolder holder, GizmoTooltipBuilder tooltip) {

    }

    public void resetValues() {
        isHoveredOver = false;
        color = Color.WHITE;
    }

    public DisplayedGizmo setColor(Color color) {
        this.color = color;
        return this;
    }

    public abstract void renderDecals(AbstractMalumCodexScreen screen, IGizmoHolder holder, GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, float partialTicks);

}
