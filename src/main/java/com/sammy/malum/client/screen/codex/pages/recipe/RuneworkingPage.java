package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import com.sammy.malum.common.recipe.RunicWorkbenchRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Item;

import java.util.function.Predicate;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderComponent;
import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderItem;

public class RuneworkingPage extends BookPage {
    private final RunicWorkbenchRecipe recipe;

    public RuneworkingPage(Predicate<RunicWorkbenchRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/runeworking_page.png"));
        if (Minecraft.getInstance() == null) {
            this.recipe = null;
            return;
        }
        this.recipe = RunicWorkbenchRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }

    public RuneworkingPage(RunicWorkbenchRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/runic_workbench_page.png"));
        this.recipe = recipe;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        renderComponent(screen, guiGraphics, recipe.primaryInput, left + 63, top + 59, mouseX, mouseY);
        renderComponent(screen, guiGraphics, recipe.secondaryInput, left + 63, top + 16, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, left + 63, top + 126, mouseX, mouseY);
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static RuneworkingPage fromOutput(Item outputItem) {
        return new RuneworkingPage(s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }
}