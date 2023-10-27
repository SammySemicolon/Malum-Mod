package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.common.recipe.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.*;

import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

@SuppressWarnings("all")
public class SpiritCruciblePage extends BookPage {
    private final SpiritFocusingRecipe recipe;

    public SpiritCruciblePage(Predicate<SpiritFocusingRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_crucible_page.png"));
        if (Minecraft.getInstance() == null) //this is null during datagen
        {
            this.recipe = null;
            return;
        }
        this.recipe = SpiritFocusingRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }

    public SpiritCruciblePage(SpiritFocusingRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_crucible_page.png"));
        this.recipe = recipe;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static SpiritCruciblePage fromInput(Item inputItem) {
        return new SpiritCruciblePage(s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }

    public static SpiritCruciblePage fromOutput(Item outputItem) {
        return new SpiritCruciblePage(s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }

    @Override
    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderItem(screen, guiGraphics, recipe.input, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, guiLeft + 67, guiTop + 126, mouseX, mouseY);
        renderComponents(screen, guiGraphics, recipe.spirits, guiLeft + 65, guiTop + 16, mouseX, mouseY, false);
    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderItem(screen, guiGraphics, recipe.input, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, guiLeft + 209, guiTop + 126, mouseX, mouseY);
        renderComponents(screen, guiGraphics, recipe.spirits, guiLeft + 207, guiTop + 16, mouseX, mouseY, false);
    }
}