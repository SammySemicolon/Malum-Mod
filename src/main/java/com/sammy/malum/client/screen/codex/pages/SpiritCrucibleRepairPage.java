package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

@SuppressWarnings("all")
public class SpiritCrucibleRepairPage extends BookPage {
    private final SpiritRepairRecipe recipe;

    public SpiritCrucibleRepairPage(Predicate<SpiritRepairRecipe> predicate) {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_repair_page.png"));
        if (Minecraft.getInstance() == null) //this is null during datagen
        {
            this.recipe = null;
            return;
        }
        this.recipe = SpiritRepairRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }

    public SpiritCrucibleRepairPage(SpiritRepairRecipe recipe) {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_repair_page.png"));
        this.recipe = recipe;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static SpiritCrucibleRepairPage fromInput(Item inputItem) {
        return new SpiritCrucibleRepairPage(s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ItemStack inputStack = recipe.inputs.get(0).getDefaultInstance();
        ItemStack outputStack = inputStack.copy();
        ProgressionBookScreen.renderItem(poseStack, inputStack, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(poseStack, outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);
        ProgressionBookScreen.renderComponents(poseStack, guiLeft + 65, guiTop + 16, mouseX, mouseY, false, recipe.spirits);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ItemStack inputStack = recipe.inputs.get(0).getDefaultInstance();
        ItemStack outputStack = inputStack.copy();
        ProgressionBookScreen.renderItem(poseStack, inputStack, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(poseStack, outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
        ProgressionBookScreen.renderComponents(poseStack, guiLeft + 207, guiTop + 16, mouseX, mouseY, false, recipe.spirits);
    }
}