package com.sammy.malum.common.integration.jei.chiseling;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.modcontent.MalumChiseling.MalumChiselRecipe;
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

public class ChiselingRecipeCategory implements IRecipeCategory<MalumChiselRecipe>
{
    public static final ResourceLocation UID = prefix("runic_chiseling");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    
    public ChiselingRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(65, 27);
        localizedName = I18n.format("malum.jei.runic_chiseling");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/runic_chiseling_recipe_overlay.png"), 0, 0, 63, 25);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.CHISEL.get()));
    }
    
    @Override
    public void draw(MalumChiselRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
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
    public Class<? extends MalumChiselRecipe> getRecipeClass()
    {
        return MalumChiselRecipe.class;
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
    public void setIngredients(MalumChiselRecipe recipe, IIngredients iIngredients)
    {
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.outputItem.asItem().getDefaultInstance());
        iIngredients.setInput(VanillaTypes.ITEM, recipe.inputItem.asItem().getDefaultInstance());
    }
    
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MalumChiselRecipe recipe, IIngredients iIngredients)
    {
        iRecipeLayout.getItemStacks().init(0, true, 4, 2);
        iRecipeLayout.getItemStacks().set(0, recipe.inputItem.getDefaultInstance());
        iRecipeLayout.getItemStacks().init(1, true, 42, 2);
        iRecipeLayout.getItemStacks().set(1, recipe.outputItem.getDefaultInstance());
    }
}
