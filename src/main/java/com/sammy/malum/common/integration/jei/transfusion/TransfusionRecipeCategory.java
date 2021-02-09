package com.sammy.malum.common.integration.jei.transfusion;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
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

public class TransfusionRecipeCategory implements IRecipeCategory<MalumTransfusionRecipe>
{
    public static final ResourceLocation UID = prefix("imbuement");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    
    public TransfusionRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(65, 27);
        localizedName = I18n.format("malum.tooltip.rite.rite_of_imbuement");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/runic_chiseling_recipe_overlay.png"), 0, 0, 63, 25);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.TOTEM_CORE.get()));
    }
    
    @Override
    public void draw(MalumTransfusionRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
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
    public Class<? extends MalumTransfusionRecipe> getRecipeClass()
    {
        return MalumTransfusionRecipe.class;
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
    public void setIngredients(MalumTransfusionRecipe recipe, IIngredients iIngredients)
    {
        iIngredients.setInputs(VanillaTypes.ITEM, recipe.input.stacks());
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.outputBlock.asItem().getDefaultInstance());
    }
    
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MalumTransfusionRecipe recipe, IIngredients iIngredients)
    {
        iRecipeLayout.getItemStacks().init(0, true, 4, 2);
        iRecipeLayout.getItemStacks().set(0, recipe.input.stacks());
        iRecipeLayout.getItemStacks().init(1, true, 42, 2);
        iRecipeLayout.getItemStacks().set(1, recipe.outputBlock.asItem().getDefaultInstance());
    }
}
