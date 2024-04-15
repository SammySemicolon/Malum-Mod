package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Item;

import java.util.function.Predicate;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritFocusingPage extends BookPage {
    private final SpiritFocusingRecipe recipe;

    public SpiritFocusingPage(Predicate<SpiritFocusingRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_focusing_page.png"));
        if (Minecraft.getInstance() == null) //this is null during datagen
        {
            this.recipe = null;
            return;
        }
        this.recipe = SpiritFocusingRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }

    public SpiritFocusingPage(SpiritFocusingRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_focusing_page.png"));
        this.recipe = recipe;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static SpiritFocusingPage fromInput(Item inputItem) {
        return new SpiritFocusingPage(s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }

    public static SpiritFocusingPage fromOutput(Item outputItem) {
        return new SpiritFocusingPage(s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        renderComponents(screen, guiGraphics, recipe.spirits, left + 63, top + 16, mouseX, mouseY, false);
        renderItem(screen, guiGraphics, recipe.input, left + 63, top + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, left + 63, top + 126, mouseX, mouseY);
    }
}