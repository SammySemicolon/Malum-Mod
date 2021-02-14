package com.sammy.malum.common.integration.jei.spiritkiln;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes.MalumSpiritKilnRecipe;
import com.sammy.malum.core.systems.recipes.MalumItemIngredient;
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

import static com.sammy.malum.MalumHelper.prefix;

public class SpiritKilnRecipeCategory implements IRecipeCategory<MalumSpiritKilnRecipe>
{
    public static final ResourceLocation UID = prefix("spirit_infusion");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    
    public SpiritKilnRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(121, 57);
        localizedName = I18n.format("malum.jei.spirit_infusion");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/tainted_furnace_recipe_overlay.png"), 0, 0, 119, 55);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.SPIRIT_KILN.get()));
    }
    
    @Override
    public void draw(MalumSpiritKilnRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
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
    public Class<? extends MalumSpiritKilnRecipe> getRecipeClass()
    {
        return MalumSpiritKilnRecipe.class;
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
    public void setIngredients(MalumSpiritKilnRecipe recipe, IIngredients iIngredients)
    {
        iIngredients.setOutputs(VanillaTypes.ITEM, recipe.outputIngredient.stacks());
        iIngredients.setInputs(VanillaTypes.ITEM, recipe.inputIngredient.stacks());
    }
    
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MalumSpiritKilnRecipe recipe, IIngredients iIngredients)
    {
        iRecipeLayout.getItemStacks().init(0, true, 21, 6);
        iRecipeLayout.getItemStacks().set(0, recipe.inputIngredient.stacks());
        iRecipeLayout.getItemStacks().init(1, true, 81, 6);
        iRecipeLayout.getItemStacks().set(1, recipe.outputIngredient.stacks());
    }
}
