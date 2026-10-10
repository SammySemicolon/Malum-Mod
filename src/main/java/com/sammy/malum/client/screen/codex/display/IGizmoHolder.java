package com.sammy.malum.client.screen.codex.display;

import com.sammy.malum.client.screen.codex.display.gizmo.*;

public interface IGizmoHolder {

    enum HoverCondition {
        DEFAULT,
        DENY,
        ALLOW
    }
    default boolean shouldGizmoRenderTooltip() {
        return true;
    }

    default HoverCondition updateHoverCondition(DisplayedGizmo gizmo) {
        return HoverCondition.DEFAULT;
    }

    default void addGizmoTooltip(GizmoTooltipBuilder builder) {
    }
}
