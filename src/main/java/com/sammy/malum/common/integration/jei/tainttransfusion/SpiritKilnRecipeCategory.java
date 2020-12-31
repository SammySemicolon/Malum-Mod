package com.sammy.malum.common.integration.jei.tainttransfusion;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.recipes.SpiritKilnRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import java.util.ArrayList;

import static com.sammy.malum.MalumHelper.prefix;

public class SpiritKilnRecipeCategory implements IRecipeCategory<SpiritKilnRecipe>
{
    public static final ResourceLocation UID = prefix("furnace_taint_transfusion");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    
    public SpiritKilnRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(121, 57);
        localizedName = I18n.format("malum.jei.furnace_taint_transfusion");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/tainted_furnace_recipe_overlay.png"), 0, 0, 119, 55);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.SPIRIT_KILN.get()));
    }
    
    @Override
    public void draw(SpiritKilnRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
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
    public Class<? extends SpiritKilnRecipe> getRecipeClass()
    {
        return SpiritKilnRecipe.class;
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
    public void setIngredients(SpiritKilnRecipe recipe, IIngredients iIngredients)
    {
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.outputItem.getDefaultInstance());
        ArrayList<ItemStack> stacks = new ArrayList<>();
        stacks.add(recipe.inputItem.getDefaultInstance());
        if (recipe.isAdvanced())
        {
            for (Item item : recipe.extraItems)
            {
                stacks.add(item.getDefaultInstance());
            }
        }
        iIngredients.setInputs(VanillaTypes.ITEM, stacks);
    }
    
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SpiritKilnRecipe recipe, IIngredients iIngredients)
    {
        iRecipeLayout.getItemStacks().init(0, true, 21, 6);
        iRecipeLayout.getItemStacks().set(0, new ItemStack(recipe.inputItem, recipe.inputItemCount));
        iRecipeLayout.getItemStacks().init(1, true, 81, 6);
        iRecipeLayout.getItemStacks().set(1, new ItemStack(recipe.outputItem, recipe.outputItemCount));
        if (recipe.isAdvanced())
        {
            int i = 0;
            for (Item item : recipe.extraItems)
            {
                iRecipeLayout.getItemStacks().init(i + 2, true, 21+30*i, 33);
                iRecipeLayout.getItemStacks().set(i + 2, item.getDefaultInstance());
                i++;
            }
        }
    }
}
