package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.Optional;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderItem;

@SuppressWarnings("all")
public class SmeltingBookPage extends BookPage {
    private final ItemStack inputStack;
    private final ItemStack outputStack;

    public SmeltingBookPage(ItemStack inputStack, ItemStack outputStack) {
        super(MalumMod.malumPath("textures/gui/book/pages/smelting_page.png"));
        this.inputStack = inputStack;
        this.outputStack = outputStack;
    }

    public SmeltingBookPage(Item inputItem, Item outputItem) {
        this(inputItem.getDefaultInstance(), outputItem.getDefaultInstance());
    }

    public static SmeltingBookPage fromInput(Item input) {
        if (Minecraft.getInstance() == null) {
            return new SmeltingBookPage(ItemStack.EMPTY, ItemStack.EMPTY);
        }
        Optional<SmeltingRecipe> optional = Minecraft.getInstance().level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(new ItemStack(input, 1)), Minecraft.getInstance().level);
        if (optional.isPresent()) {
            SmeltingRecipe recipe = optional.get();
            return new SmeltingBookPage(new ItemStack(input), recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
        }
        return new SmeltingBookPage(ItemStack.EMPTY, ItemStack.EMPTY);
    }

    @Override
    public boolean isValid() {
        return !inputStack.isEmpty() && !outputStack.isEmpty();
    }

    @Override
    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderItem(screen, guiGraphics, inputStack, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);

    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderItem(screen, guiGraphics, inputStack, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
    }
}