package com.sammy.malum.common.integration.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_content.MalumRuneTableRecipes.MalumRuneTableRecipe;
import com.sammy.malum.core.systems.recipe.ItemIngredient;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;

import static com.sammy.malum.MalumHelper.prefix;

public class RuneTableRecipeCategory implements IRecipeCategory<MalumRuneTableRecipe>
{
    public static final ResourceLocation UID = prefix("rune_table");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public static final ResourceLocation BACKGROUND = MalumHelper.prefix("textures/gui/rune_table_recipe_overlay.png");

    public RuneTableRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(132, 163);
        localizedName = I18n.format("malum.jei.rune_table");
        overlay = guiHelper.createDrawable(BACKGROUND, 0, 0, 130, 161);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.RUNE_TABLE.get()));
    }

    @Nonnull
    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends MalumRuneTableRecipe> getRecipeClass()
    {
        return MalumRuneTableRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(MalumRuneTableRecipe recipe, IIngredients iIngredients)
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (ItemIngredient ingredient : recipe.itemIngredients)
        {
            stacks.addAll(ingredient.stacks());
        }
        iIngredients.setInputs(VanillaTypes.ITEM, stacks);
        iIngredients.setOutputs(VanillaTypes.ITEM, recipe.outputIngredient.stacks());
    }

    @Override
    public void draw(MalumRuneTableRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        overlay.draw(matrixStack);
    }
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MalumRuneTableRecipe recipe, IIngredients iIngredients)
    {
        for (int i = 0; i < recipe.itemIngredients.size(); i++)
        {
            ItemIngredient ingredient = recipe.itemIngredients.get(i);
            int itemPosX = 31 + 25*i;
            int itemPosY = 48;

            iRecipeLayout.getItemStacks().init(i, true, itemPosX, itemPosY);
            iRecipeLayout.getItemStacks().set(i, ingredient.stacks());
        }
        iRecipeLayout.getItemStacks().init(4, true, 56, 115);
        iRecipeLayout.getItemStacks().set(4, recipe.outputIngredient.stacks());
    }
}