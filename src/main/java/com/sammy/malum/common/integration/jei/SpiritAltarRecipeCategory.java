package com.sammy.malum.common.integration.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_content.MalumSpiritAltarRecipes.MalumSpiritAltarRecipe;
import com.sammy.malum.core.systems.recipe.ItemIngredient;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nonnull;
import java.util.ArrayList;

import static com.sammy.malum.MalumHelper.prefix;
import static com.sammy.malum.common.book.pages.SpiritInfusionPage.itemOffset;
import static net.minecraft.client.gui.AbstractGui.blit;

public class SpiritAltarRecipeCategory implements IRecipeCategory<MalumSpiritAltarRecipe>
{
    public static final ResourceLocation UID = prefix("spirit_altar");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public static final ResourceLocation BACKGROUND = MalumHelper.prefix("textures/gui/spirit_altar_recipe_overlay.png");

    public SpiritAltarRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(132, 163);
        localizedName = I18n.format("malum.jei.spirit_infusion");
        overlay = guiHelper.createDrawable(BACKGROUND, 0, 0, 130, 161);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.SPIRIT_ALTAR.get()));
    }

    @Nonnull
    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends MalumSpiritAltarRecipe> getRecipeClass()
    {
        return MalumSpiritAltarRecipe.class;
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
    public void setIngredients(MalumSpiritAltarRecipe recipe, IIngredients iIngredients)
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (SpiritIngredient spirit : recipe.spiritIngredients)
        {
            stacks.add(spirit.getItem());
        }
        for (ItemIngredient ingredient : recipe.extraItemIngredients)
        {
            stacks.addAll(ingredient.stacks());
        }
        stacks.addAll(recipe.inputIngredient.stacks());
        iIngredients.setInputs(VanillaTypes.ITEM, stacks);
        iIngredients.setOutputs(VanillaTypes.ITEM, recipe.outputIngredient.stacks());
    }

    @Override
    public void draw(MalumSpiritAltarRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        overlay.draw(matrixStack);
        Minecraft minecraft = Minecraft.getInstance();
        int inputX = 56;
        int inputY = 47;

        for (int i = 0; i < recipe.extraItemIngredients.size(); i++)
        {
            Vector3d itemOffset = itemOffset(false, i, recipe.extraItemIngredients.size());
            minecraft.getTextureManager().bindTexture(BACKGROUND);
            blit(matrixStack, inputX + (int) itemOffset.x, inputY + (int) itemOffset.z, 131, 0, 20, 21, 256, 256);
        }
        for (int i = 0; i < recipe.spiritIngredients.size(); i++)
        {
            Vector3d itemOffset = itemOffset(recipe.extraItemIngredients.size() > 1, i, recipe.spiritIngredients.size());
            minecraft.getTextureManager().bindTexture(BACKGROUND);
            blit(matrixStack, inputX + (int) itemOffset.x, inputY + (int) itemOffset.z, 131, 0, 20, 21, 256, 256);
        }
    }
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MalumSpiritAltarRecipe recipe, IIngredients iIngredients)
    {
        int itemCounter = 0;
        iRecipeLayout.getItemStacks().init(itemCounter, true, 56, 48);
        iRecipeLayout.getItemStacks().set(itemCounter, recipe.inputIngredient.stacks());
        itemCounter++;
        iRecipeLayout.getItemStacks().init(itemCounter, true, 56, 115);
        iRecipeLayout.getItemStacks().set(itemCounter, recipe.outputIngredient.stacks());
        itemCounter++;
        int inputX = 56;
        int inputY = 48;

        for (int i = 0; i < recipe.extraItemIngredients.size(); i++)
        {
            Vector3d itemOffset = itemOffset(false, i, recipe.extraItemIngredients.size());

            iRecipeLayout.getItemStacks().init(itemCounter, true, inputX + (int) itemOffset.x, inputY + (int) itemOffset.z);
            iRecipeLayout.getItemStacks().set(itemCounter, recipe.extraItemIngredients.get(i).stacks());
            itemCounter++;
        }
        for (int i = 0; i < recipe.spiritIngredients.size(); i++)
        {
            Vector3d itemOffset = itemOffset(recipe.extraItemIngredients.size() > 1, i, recipe.spiritIngredients.size());
            iRecipeLayout.getItemStacks().init(itemCounter, true, inputX + (int) itemOffset.x, inputY + (int) itemOffset.z);
            iRecipeLayout.getItemStacks().set(itemCounter, recipe.spiritIngredients.get(i).getItem());
            itemCounter++;
        }
    }
}