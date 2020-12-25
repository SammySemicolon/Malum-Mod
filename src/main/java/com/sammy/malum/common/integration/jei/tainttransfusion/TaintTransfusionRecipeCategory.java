package com.sammy.malum.common.integration.jei.tainttransfusion;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.recipes.TaintTransfusion;
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

public class TaintTransfusionRecipeCategory implements IRecipeCategory<TaintTransfusion>
{
    public static final ResourceLocation UID = prefix("taint_transfusion");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    
    public TaintTransfusionRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(121, 32);
        localizedName = I18n.format("malum.jei.taint_transfusion");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/taint_transfusion_overlay.png"), 0, 0, 119, 30);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.TAINT_RUDIMENT.get()));
    }
    
    @Override
    public void draw(TaintTransfusion recipe, MatrixStack matrixStack, double mouseX, double mouseY)
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
    public Class<? extends TaintTransfusion> getRecipeClass()
    {
        return TaintTransfusion.class;
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
    public void setIngredients(TaintTransfusion taintTransfusion, IIngredients iIngredients)
    {
        iIngredients.setInput(VanillaTypes.ITEM, taintTransfusion.inputBlock.asItem().getDefaultInstance());
        iIngredients.setOutput(VanillaTypes.ITEM, taintTransfusion.outputBlock.asItem().getDefaultInstance());
    }
    
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, TaintTransfusion taintTransfusion, IIngredients iIngredients)
    {
        iRecipeLayout.getItemStacks().init(0, true, 21, 6);
        iRecipeLayout.getItemStacks().set(0, taintTransfusion.inputBlock.asItem().getDefaultInstance());
        iRecipeLayout.getItemStacks().init(1, true, 81, 6);
        iRecipeLayout.getItemStacks().set(1, taintTransfusion.outputBlock.asItem().getDefaultInstance());
    }
}
