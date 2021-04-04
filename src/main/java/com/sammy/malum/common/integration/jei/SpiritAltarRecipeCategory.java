package com.sammy.malum.common.integration.jei;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.modcontent.MalumSpiritAltarRecipes.MalumSpiritAltarRecipe;
import com.sammy.malum.core.systems.recipes.MalumSpiritIngredient;
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

public class SpiritAltarRecipeCategory implements IRecipeCategory<MalumSpiritAltarRecipe>
{
    public static final ResourceLocation UID = prefix("spirit_altar");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    
    public SpiritAltarRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(111, 145);
        localizedName = I18n.format("malum.jei.spirit_altar");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_altar_recipe_overlay.png"), 0, 0, 109, 143);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.SPIRIT_ALTAR.get()));
    }
    
    @Override
    public void draw(MalumSpiritAltarRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
        
        overlay.draw(matrixStack);
        
        GlStateManager.disableBlend();
        GlStateManager.disableAlphaTest();
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
        for (MalumSpiritIngredient spirit : recipe.spiritIngredients)
        {
            stacks.add(spirit.getItem());
        }
        stacks.addAll(recipe.inputIngredient.stacks());
        iIngredients.setInputs(VanillaTypes.ITEM, stacks);
        iIngredients.setOutputs(VanillaTypes.ITEM, recipe.outputIngredient.stacks());
    }
    
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MalumSpiritAltarRecipe recipe, IIngredients iIngredients)
    {
        ArrayList<Pair<Integer, Integer>> offsets = MalumHelper.toArrayList(Pair.of(46, 14), Pair.of(46, 66), Pair.of(20, 40), Pair.of(72, 40), Pair.of(24, 18), Pair.of(68, 62), Pair.of(68, 18), Pair.of(24, 62));
        int i = 0;
        for (MalumSpiritIngredient spirit : recipe.spiritIngredients)
        {
            Pair<Integer, Integer> offset = offsets.get(i);
            iRecipeLayout.getItemStacks().init(i, true, offset.first, offset.second);
            iRecipeLayout.getItemStacks().set(i, spirit.getItem());
            i++;
        }
        iRecipeLayout.getItemStacks().init(i+1, true, 46, 40);
        iRecipeLayout.getItemStacks().set(i+1, recipe.inputIngredient.stacks());
        iRecipeLayout.getItemStacks().init(i+2, true, 46, 107);
        iRecipeLayout.getItemStacks().set(i+2, recipe.outputIngredient.stacks());
    }
}