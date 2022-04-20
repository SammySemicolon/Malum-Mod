package com.sammy.malum.compability.jei.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.BlockTransmutationRecipe;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

import static com.sammy.malum.core.helper.DataHelper.prefix;

public class BlockTransmutationRecipeCategory implements IRecipeCategory<BlockTransmutationRecipe> {
    public static final ResourceLocation UID = prefix("block_transmutation");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public BlockTransmutationRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 83);
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/block_transmutation_jei.png"), 0, 0, 140, 81);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemRegistry.SOULWOOD_TOTEM_BASE.get()));
    }

    @Override
    public void draw(BlockTransmutationRecipe recipe, PoseStack poseStack, double mouseX, double mouseY) {
        overlay.draw(poseStack);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends BlockTransmutationRecipe> getRecipeClass() {
        return BlockTransmutationRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("malum.jei." + UID.getPath());
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(BlockTransmutationRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.ITEM, recipe.input.asItem().getDefaultInstance());
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.output.asItem().getDefaultInstance());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, BlockTransmutationRecipe recipe, IIngredients iIngredients) {
        int index = 0;
        iRecipeLayout.getItemStacks().init(index + 1, true, 27, 26);
        iRecipeLayout.getItemStacks().set(index + 1, recipe.input.asItem().getDefaultInstance());

        iRecipeLayout.getItemStacks().init(index + 2, true, 92, 25);
        iRecipeLayout.getItemStacks().set(index + 2, recipe.output.asItem().getDefaultInstance());
    }
}