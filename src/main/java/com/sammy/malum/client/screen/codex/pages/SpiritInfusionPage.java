package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.systems.recipe.IRecipeComponent;
import com.sammy.malum.core.systems.recipe.IngredientWithCount;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.renderTexture;

@SuppressWarnings("all")
public class SpiritInfusionPage extends BookPage {
    private final SpiritInfusionRecipe recipe;

    public SpiritInfusionPage(Predicate<SpiritInfusionRecipe> predicate) {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_infusion_page.png"));
        if (Minecraft.getInstance() == null) //this is null during datagen
        {
            this.recipe = null;
            return;
        }
        this.recipe = SpiritInfusionRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }

    public SpiritInfusionPage(SpiritInfusionRecipe recipe) {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_infusion_page.png"));
        this.recipe = recipe;
    }

    public static SpiritInfusionPage fromInput(Item inputItem) {
        return new SpiritInfusionPage(s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }

    public static SpiritInfusionPage fromOutput(Item outputItem) {
        return new SpiritInfusionPage(s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        if (!recipe.extraItems.isEmpty()) {
            ProgressionBookScreen.renderComponents(poseStack, recipe.extraItems, guiLeft + 107, guiTop + 59, mouseX, mouseY, true);
        }
        ProgressionBookScreen.renderComponent(poseStack, recipe.input, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderComponent(poseStack, recipe.output, guiLeft + 67, guiTop + 126, mouseX, mouseY);
        ProgressionBookScreen.renderComponents(poseStack, recipe.spirits, guiLeft + 23, guiTop + 59, mouseX, mouseY, true);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        if (!recipe.extraItems.isEmpty()) {
            ProgressionBookScreen.renderComponents(poseStack, recipe.extraItems, guiLeft + 249, guiTop + 59, mouseX, mouseY, true);
        }
        ProgressionBookScreen.renderComponent(poseStack, recipe.input, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderComponent(poseStack, recipe.output, guiLeft + 209, guiTop + 126, mouseX, mouseY);
        ProgressionBookScreen.renderComponents(poseStack, recipe.spirits, guiLeft + 165, guiTop + 59, mouseX, mouseY, true);
    }
}