package com.sammy.malum.client.screen.codex.pages.recipe.vanilla;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.display.*;
import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedGizmo;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.client.screen.codex.pages.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import static com.sammy.malum.client.screen.codex.helper.CodexItemHelper.renderItem;

public class SmeltingPage extends BookPage implements IGizmoHolder {

    private final DisplayedGizmo input;
    private final DisplayedGizmo output;

    public SmeltingPage(DisplayedGizmo input, DisplayedGizmo output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public ResourceLocation getBackground() {
        return MalumMod.malumPath("textures/gui/book/pages/smelting_page.png");
    }

    @Override
    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        input.render(screen, this, guiGraphics, left + 63, top + 70, mouseX, mouseY, partialTicks);
        output.render(screen, this, guiGraphics, left + 63, top + 162, mouseX, mouseY, partialTicks);
    }
}