package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.common.recipe.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.item.*;

import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class RuneworkingPage<T extends AbstractProgressionCodexScreen<T>> extends BookPage<T> {
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
    public void render(EntryScreen<T> screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        renderComponent(screen, guiGraphics, recipe.primaryInput, left + 63, top + 59, mouseX, mouseY);
        renderComponent(screen, guiGraphics, recipe.secondaryInput, left + 63, top + 16, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, left + 63, top + 126, mouseX, mouseY);
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static <T extends AbstractProgressionCodexScreen<T>>RuneworkingPage<T> fromOutput(Item outputItem) {
        return new RuneworkingPage<>(s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }
}