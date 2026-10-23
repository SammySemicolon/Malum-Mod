package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.display.*;
import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedGizmo;
import com.sammy.malum.client.screen.codex.screens.CodexEntryScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;

public class InteractionPage extends BookPage implements IGizmoHolder {

    public enum InteractionType {
        STRIPPING("stripping"),
        BOTTLING("bottling");

        public final String name;

        InteractionType(String name) {
            this.name = name;
        }
    }

    private final InteractionType interactionType;
    private final DisplayedGizmo upperDisplay;
    private final DisplayedGizmo lowerDisplay;

    public static InteractionPage stripping(DisplayedGizmo upperDisplay, DisplayedGizmo lowerDisplay) {
        return new InteractionPage(InteractionType.STRIPPING, upperDisplay, lowerDisplay);
    }

    public static InteractionPage bottling(DisplayedGizmo upperDisplay, DisplayedGizmo lowerDisplay) {
        return new InteractionPage(InteractionType.BOTTLING, upperDisplay, lowerDisplay);
    }

    public InteractionPage(InteractionType interactionType, DisplayedGizmo upperDisplay, DisplayedGizmo lowerDisplay) {
        this.interactionType = interactionType;
        this.upperDisplay = upperDisplay;
        this.lowerDisplay = lowerDisplay;
    }

    @Override
    public ResourceLocation getBackground() {
        return MalumMod.malumPath("textures/gui/book/pages/interaction_page.png");
    }

    @Override
    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        upperDisplay.render(screen, this, guiGraphics, left + 63, top + 70, mouseX, mouseY, partialTicks);
        lowerDisplay.render(screen, this, guiGraphics, left + 63, top + 162, mouseX, mouseY, partialTicks);

        var interactionIconTexture = MalumMod.malumPath("textures/gui/book/entry_elements/interaction_" + interactionType.name + ".png");

        var segments = VFXBuilders.createScreen()
                .setShader(GameRenderer::getPositionTexColorShader)
                .setTexture(interactionIconTexture);

        segments.setPositionWithWidth(left + 21, top + 161, 16, 16).blit(guiGraphics.pose());
        segments.setPositionWithWidth(left + 105, top + 161, 16, 16).blit(guiGraphics.pose());

    }
}