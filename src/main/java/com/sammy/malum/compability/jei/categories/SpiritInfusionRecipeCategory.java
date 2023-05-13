package com.sammy.malum.compability.jei.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.compability.jei.JEIHandler;
import com.sammy.malum.registry.common.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

import static com.sammy.malum.MalumMod.malumPath;

public class SpiritInfusionRecipeCategory implements IRecipeCategory<SpiritInfusionRecipe> {

    public static final ResourceLocation UID = malumPath("spirit_infusion");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritInfusionRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 185);
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MALUM, "textures/gui/spirit_infusion_jei.png"), 0, 0, 142, 183);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemRegistry.SPIRIT_ALTAR.get()));
    }

    @Override
    public void draw(SpiritInfusionRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        overlay.draw(poseStack);
        ArcanaCodexHelper.renderItemFrames(poseStack, recipe.spirits.size(), 19, 48, true);
        if (!recipe.extraItems.isEmpty()) {
            ArcanaCodexHelper.renderItemFrames(poseStack, recipe.extraItems.size(), 103, 48, true);
        }
    }

    @Override
    public RecipeType<SpiritInfusionRecipe> getRecipeType() {
        return JEIHandler.SPIRIT_INFUSION;
    }

    @Nonnull
    @Override
    @SuppressWarnings("removal")
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    @SuppressWarnings("removal")
    public Class<? extends SpiritInfusionRecipe> getRecipeClass() {
        return SpiritInfusionRecipe.class;
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
    public void setRecipe(IRecipeLayoutBuilder builder, SpiritInfusionRecipe recipe, IFocusGroup focuses) {
        JEIHandler.addItemsToJei(builder, RecipeIngredientRole.INPUT, 20, 49, true, recipe.spirits);
        JEIHandler.addItemsToJei(builder, RecipeIngredientRole.INPUT, 104, 49, true, recipe.extraItems);

        builder.addSlot(RecipeIngredientRole.INPUT, 63, 57)
             .addItemStacks(recipe.input.getStacks());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 124)
             .addItemStack(recipe.output);
    }
}
