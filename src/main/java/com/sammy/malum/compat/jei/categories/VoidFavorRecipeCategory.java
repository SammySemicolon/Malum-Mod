package com.sammy.malum.compat.jei.categories;

import com.sammy.malum.common.recipe.VoidFavorRecipe;
import com.sammy.malum.compat.jei.JEIHandler;
import com.sammy.malum.registry.common.MalumContent;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

import static com.sammy.malum.MalumMod.malumPath;

public class VoidFavorRecipeCategory implements IRecipeCategory<VoidFavorRecipe> {

    public static final ResourceLocation UID = malumPath("void_favor");
    private final IDrawable overlay;
    private final IDrawable icon;

    public VoidFavorRecipeCategory(IGuiHelper guiHelper) {
        overlay = guiHelper.createDrawable(malumPath("textures/gui/weeping_well_jei.png"), 0, 0, 142, 185);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(MalumContent.Enscription.WEEPING_WELL.get()));
    }

    @Override
    public void draw(VoidFavorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        overlay.draw(guiGraphics);
    }

    @Override
    public RecipeType<VoidFavorRecipe> getRecipeType() {
        return JEIHandler.VOID_FAVOR;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("malum.jei." + UID.getPath());
    }

    @Override
    public int getHeight() {
        return overlay.getHeight();
    }

    @Override
    public int getWidth() {
        return overlay.getWidth();
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, VoidFavorRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 63, 57)
            .addIngredients(recipe.input);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 124)
            .addItemStack(recipe.result);
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST)
            .addItemStack(new ItemStack(MalumContent.Enscription.WEEPING_WELL.get()));
    }
}
