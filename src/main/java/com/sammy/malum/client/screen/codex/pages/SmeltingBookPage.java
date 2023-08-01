package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

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
            return new SmeltingBookPage(new ItemStack(input), recipe.getResultItem());
        }
        return new SmeltingBookPage(ItemStack.EMPTY, ItemStack.EMPTY);
    }

    @Override
    public boolean isValid() {
        return !inputStack.isEmpty() && !outputStack.isEmpty();
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderItem(screen, poseStack, inputStack, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        renderItem(screen, poseStack, outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);

    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderItem(screen, poseStack, inputStack, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        renderItem(screen, poseStack, outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
    }
}