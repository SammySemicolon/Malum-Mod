package com.sammy.malum.common.integration.jei.arcanecrafting;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.recipes.ArcaneCraftingRecipe;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

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
        background = guiHelper.createBlankDrawable(189, 80);
        localizedName = I18n.format("malum.jei.arcane_crafting");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/arcane_crafting_overlay.png"), 0, 0, 186, 77);
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
        for (int i = 0; i < arcaneCraftingRecipe.itemStacks.size(); i++)
        {
            switch (i)
            {
                case 0:
                {
                    iRecipeLayout.getItemStacks().init(i, true, 37, 11);
                    iRecipeLayout.getItemStacks().set(i, arcaneCraftingRecipe.itemStacks.get(i));
                    break;
                }
                case 1:
                {
                    iRecipeLayout.getItemStacks().init(i, true, 58, 11);
                    iRecipeLayout.getItemStacks().set(i, arcaneCraftingRecipe.itemStacks.get(i));
                    break;
                }
                case 2:
                {
                    iRecipeLayout.getItemStacks().init(i, true, 79, 11);
                    iRecipeLayout.getItemStacks().set(i, arcaneCraftingRecipe.itemStacks.get(i));
                    break;
                }
                case 3:
                {
                    iRecipeLayout.getItemStacks().init(i, true, 37, 32);
                    iRecipeLayout.getItemStacks().set(i, arcaneCraftingRecipe.itemStacks.get(i));
                    break;
                }
                case 4:
                {
                    iRecipeLayout.getItemStacks().init(i, true, 58, 32);
                    iRecipeLayout.getItemStacks().set(i, arcaneCraftingRecipe.itemStacks.get(i));
                    break;
                }
                case 5:
                {
                    iRecipeLayout.getItemStacks().init(i, true, 79, 32);
                    iRecipeLayout.getItemStacks().set(i, arcaneCraftingRecipe.itemStacks.get(i));
                    break;
                }
                case 6:
                {
                    iRecipeLayout.getItemStacks().init(i, true, 37, 53);
                    iRecipeLayout.getItemStacks().set(i, arcaneCraftingRecipe.itemStacks.get(i));
                    break;
                }
                case 7:
                {
                    iRecipeLayout.getItemStacks().init(i, true, 58, 53);
                    iRecipeLayout.getItemStacks().set(i, arcaneCraftingRecipe.itemStacks.get(i));
                    break;
                }
                case 8:
                {
                    iRecipeLayout.getItemStacks().init(i, true, 79, 53);
                    iRecipeLayout.getItemStacks().set(i, arcaneCraftingRecipe.itemStacks.get(i));
                    break;
                }
            }
        }
        iRecipeLayout.getItemStacks().init(9, true, 147, 32);
        iRecipeLayout.getItemStacks().set(9, new ItemStack(arcaneCraftingRecipe.outputItem, arcaneCraftingRecipe.outputItemCount));
    }
}
