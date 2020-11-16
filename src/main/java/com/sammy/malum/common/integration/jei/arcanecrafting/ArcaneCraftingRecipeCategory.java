package com.sammy.malum.common.integration.jei.arcanecrafting;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.recipes.ArcaneCraftingRecipe;
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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.MalumHelper.prefix;

public class ArcaneCraftingRecipeCategory implements IRecipeCategory<ArcaneCraftingRecipe>
{
    public static final ResourceLocation UID = prefix("arcane_crafting");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    
    public ArcaneCraftingRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(135, 71);
        localizedName = I18n.format("malum.jei.arcane_crafting");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/arcane_crafting_overlay.png"), 0, 0, 132, 68);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.TAINTED_ARCANE_CRAFTING_TABLE.get()));
    }
    
    @Override
    public void draw(ArcaneCraftingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
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
    public Class<? extends ArcaneCraftingRecipe> getRecipeClass()
    {
        return ArcaneCraftingRecipe.class;
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
    public void setIngredients(ArcaneCraftingRecipe arcaneCraftingRecipe, IIngredients iIngredients)
    {
    
    }
    
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, ArcaneCraftingRecipe arcaneCraftingRecipe, IIngredients iIngredients)
    {
        for (int i = 0; i < arcaneCraftingRecipe.inputItems.length; i++)
        {
            if (i == 0)
            {
                iRecipeLayout.getItemStacks().init(i, true, 58, 28);
                iRecipeLayout.getItemStacks().set(i, new ItemStack(arcaneCraftingRecipe.inputItems[0], arcaneCraftingRecipe.inputItemCounts[0]));
            }
            else
            {
//                iRecipeLayout.getItemStacks().init(i, true, x, y);
//                iRecipeLayout.getItemStacks().set(i, new ItemStack(arcaneCraftingRecipe.inputItems[i], arcaneCraftingRecipe.inputItemCounts[i]));
            }
        }
    }
}
