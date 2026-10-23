package com.sammy.malum.client.screen.codex.display.gizmo;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public record GizmoTooltipBuilder(ArrayList<Component> tooltip) {

    public void addDefaultTitle(String key) {
        addTitle(DisplayedGizmo.title(key));
    }

    public void addDefaultSubtext(String key) {
        addSubtext(DisplayedGizmo.subtext(key));
    }

    public void addTitle(String key) {
        tooltip.add(Component.translatable(key).withStyle(ChatFormatting.GOLD));
    }

    public void addSubtext(String key) {
        tooltip.add(Component.translatable(key).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    public void add(Component component) {
        tooltip.add(component);
    }

    public void addAll(List<Component> tooltip) {
        this.tooltip.addAll(tooltip);
    }

    public boolean isEmpty() {
        return tooltip.isEmpty();
    }
}
