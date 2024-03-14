package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.common.recipe.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.item.*;

import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

@SuppressWarnings("all")
public class RunicWorkbenchPage extends BookPage {
    private final RunicWorkbenchRecipe recipe;

    public RunicWorkbenchPage(Predicate<RunicWorkbenchRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/runic_workbench_page.png"));
        if (Minecraft.getInstance() == null) //this is null during datagen
        {
            this.recipe = null;
            return;
        }
        this.recipe = RunicWorkbenchRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }

    public RunicWorkbenchPage(RunicWorkbenchRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/runic_workbench_page.png"));
        this.recipe = recipe;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static RunicWorkbenchPage fromOutput(Item outputItem) {
        return new RunicWorkbenchPage(s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }

    @Override
    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderComponent(screen, guiGraphics, recipe.primaryInput, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, guiLeft + 67, guiTop + 126, mouseX, mouseY);
        renderComponent(screen, guiGraphics, recipe.secondaryInput, guiLeft + 67, guiTop + 16, mouseX, mouseY);
    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderComponent(screen, guiGraphics, recipe.primaryInput, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, guiLeft + 209, guiTop + 126, mouseX, mouseY);
        renderComponent(screen, guiGraphics, recipe.secondaryInput, guiLeft + 209, guiTop + 16, mouseX, mouseY);
    }
}