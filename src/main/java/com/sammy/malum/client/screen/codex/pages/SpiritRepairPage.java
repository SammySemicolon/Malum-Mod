package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class SpiritRepairPage extends BookPage {
    private final SpiritRepairRecipe recipe;

    public SpiritRepairPage(Predicate<SpiritRepairRecipe> predicate) {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_repair_page.png"));
        if (Minecraft.getInstance() == null) //this is null during datagen
        {
            this.recipe = null;
            return;
        }
        this.recipe = SpiritRepairRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }

    public SpiritRepairPage(SpiritRepairRecipe recipe) {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_repair_page.png"));
        this.recipe = recipe;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static SpiritRepairPage fromInput(Item inputItem) {
        return new SpiritRepairPage(s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderComponent(poseStack, recipe.repairMaterial, guiLeft + 48, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(poseStack, recipe.inputs.stream().map(s -> s.getDefaultInstance()).peek(s -> s.setDamageValue((int) (s.getMaxDamage() * recipe.durabilityPercentage))).collect(Collectors.toList()), guiLeft + 86, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(poseStack, recipe.inputs.stream().map(s -> SpiritRepairRecipe.getRepairRecipeOutput(s.getDefaultInstance())).collect(Collectors.toList()), guiLeft + 67, guiTop + 126, mouseX, mouseY);
        if (!recipe.spirits.isEmpty()) {
            ProgressionBookScreen.renderComponents(poseStack, recipe.spirits, guiLeft + 65, guiTop + 16, mouseX, mouseY, false);
        }
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderComponent(poseStack, recipe.repairMaterial, guiLeft + 190, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(poseStack, recipe.inputs.stream().map(s -> s.getDefaultInstance()).peek(s -> s.setDamageValue((int) (s.getMaxDamage() * recipe.durabilityPercentage))).collect(Collectors.toList()), guiLeft + 228, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(poseStack, recipe.inputs.stream().map(s -> SpiritRepairRecipe.getRepairRecipeOutput(s.getDefaultInstance())).collect(Collectors.toList()), guiLeft + 209, guiTop + 126, mouseX, mouseY);
        if (!recipe.spirits.isEmpty()) {
            ProgressionBookScreen.renderComponents(poseStack, recipe.spirits, guiLeft + 207, guiTop + 16, mouseX, mouseY, false);
        }
    }
}