package com.sammy.malum.compability.jei.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.compability.jei.JEIHandler;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;

import static com.sammy.malum.MalumMod.prefix;

public class SpiritInfusionRecipeCategory implements IRecipeCategory<SpiritInfusionRecipe> {

    public static final ResourceLocation UID = prefix("spirit_infusion");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritInfusionRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 185);
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MALUM, "textures/gui/spirit_infusion_jei.png"), 0, 0, 142, 183);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemRegistry.SPIRIT_ALTAR.get()));
    }

    @Override
    public void draw(SpiritInfusionRecipe recipe, PoseStack poseStack, double mouseX, double mouseY) {
        overlay.draw(poseStack);
        ProgressionBookScreen.renderItemFrames(poseStack, recipe.spirits.size(), 19, 48, true);
        if (!recipe.extraItems.isEmpty()) {
            ProgressionBookScreen.renderItemFrames(poseStack, recipe.extraItems.size(), 103, 48, true);
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends SpiritInfusionRecipe> getRecipeClass() {
        return SpiritInfusionRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("malum.jei." + UID.getPath());
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(SpiritInfusionRecipe recipe, IIngredients iIngredients) {
        ArrayList<ItemStack> items = new ArrayList<>(Arrays.asList(recipe.input.ingredient.getItems()));

        recipe.extraItems.forEach(ingredient -> items.addAll(ingredient.getStacks()));
        recipe.spirits.forEach(ingredient -> items.add(ingredient.getStack()));
        iIngredients.setInputs(VanillaTypes.ITEM, items);
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.output.getStack());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SpiritInfusionRecipe recipe, IIngredients iIngredients) {
        int index = 0;
        index = JEIHandler.addItemsToJei(iRecipeLayout, 19, 48, true, recipe.spirits, index);
        if (!recipe.extraItems.isEmpty()) {
            index = JEIHandler.addItemsToJei(iRecipeLayout, 103, 48, true, recipe.extraItems, index);
        }

        iRecipeLayout.getItemStacks().init(index + 1, true, 62, 56);
        iRecipeLayout.getItemStacks().set(index + 1, recipe.input.getStacks());

        iRecipeLayout.getItemStacks().init(index + 2, true, 62, 123);
        iRecipeLayout.getItemStacks().set(index + 2, recipe.output.getStack());
    }
}