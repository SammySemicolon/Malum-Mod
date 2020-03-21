package com.kittykitcatcat.malum.integration.jei.blockTransmutation;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.recipes.BlockTransmutationRecipe;
import com.mojang.blaze3d.platform.GlStateManager;
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

public class TransmutationRecipeCategory implements IRecipeCategory<BlockTransmutationRecipe>
{

    public static final ResourceLocation UID = new ResourceLocation(MalumMod.MODID, "transmutation");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public TransmutationRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(80, 24);
        localizedName = I18n.format("malum.jei.transmutation");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/transmutation_overlay.png"),
            0, 0, 86, 30);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModItems.block_transmutation_tool));
    }

    @Override
    public void draw(BlockTransmutationRecipe recipe, double mouseX, double mouseY)
    {
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
        overlay.draw(-2, -2);
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
    public Class<? extends BlockTransmutationRecipe> getRecipeClass()
    {
        return BlockTransmutationRecipe.class;
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
    public void setIngredients(BlockTransmutationRecipe blockTransmutationRecipe, IIngredients iIngredients)
    {
        iIngredients.setInput(VanillaTypes.ITEM, new ItemStack(blockTransmutationRecipe.getBlock().asItem()));
        iIngredients.setOutput(VanillaTypes.ITEM, new ItemStack(blockTransmutationRecipe.getReplacementBlock().asItem()));
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, BlockTransmutationRecipe blockTransmutationRecipe, IIngredients iIngredients)
    {
        iRecipeLayout.getItemStacks().init(0, true, 1, 3);
        iRecipeLayout.getItemStacks().set(0, new ItemStack(blockTransmutationRecipe.getBlock()));
        iRecipeLayout.getItemStacks().init(1, true, 31, 3);
        iRecipeLayout.getItemStacks().set(1, new ItemStack(ModItems.block_transmutation_tool));
        iRecipeLayout.getItemStacks().init(2, true, 61, 3);
        iRecipeLayout.getItemStacks().set(2, new ItemStack(blockTransmutationRecipe.getReplacementBlock()));
    }
}
