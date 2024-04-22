package com.sammy.malum.client.screen.codex.pages.recipe.vanilla;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.client.screen.codex.pages.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.Optional;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderItem;

public class SmeltingPage extends BookPage {
    private final ItemStack inputStack;
    private final ItemStack outputStack;

    public SmeltingPage(ItemStack inputStack, ItemStack outputStack) {
        super(MalumMod.malumPath("textures/gui/book/pages/smelting_page.png"));
        this.inputStack = inputStack;
        this.outputStack = outputStack;
    }

    public SmeltingPage(Item inputItem, Item outputItem) {
        this(inputItem.getDefaultInstance(), outputItem.getDefaultInstance());
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        renderItem(screen, guiGraphics, inputStack, left + 63, top + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, outputStack, left + 63, top + 126, mouseX, mouseY);
    }

    @Override
    public boolean isValid() {
        return !inputStack.isEmpty() && !outputStack.isEmpty();
    }

    public static SmeltingPage fromInput(Item input) {
        if (Minecraft.getInstance() == null) {
            return new SmeltingPage(ItemStack.EMPTY, ItemStack.EMPTY);
        }
        Optional<SmeltingRecipe> optional = Minecraft.getInstance().level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(new ItemStack(input, 1)), Minecraft.getInstance().level);
        if (optional.isPresent()) {
            SmeltingRecipe recipe = optional.get();
            return new SmeltingPage(new ItemStack(input), recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
        }
        return new SmeltingPage(ItemStack.EMPTY, ItemStack.EMPTY);
    }
}