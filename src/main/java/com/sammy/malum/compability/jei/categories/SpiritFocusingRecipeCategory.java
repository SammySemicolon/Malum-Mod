package com.sammy.malum.compability.jei.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.renderTexture;
import static com.sammy.malum.core.helper.DataHelper.prefix;

public class SpiritFocusingRecipeCategory implements IRecipeCategory<SpiritFocusingRecipe> {
    public static final ResourceLocation BACKGROUND_TEXTURE = prefix("textures/gui/book/pages/spirit_crucible_page.png");
    public static final ResourceLocation UID = prefix("spirit_focusing");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritFocusingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 185);
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_focusing_jei.png"), 0, 0, 140, 183);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemRegistry.SPIRIT_CRUCIBLE.get()));
    }

    @Override
    public void draw(SpiritFocusingRecipe recipe, PoseStack poseStack, double mouseX, double mouseY) {
        overlay.draw(poseStack);
        if (recipe.spirits.size() > 0) {
            ProgressionBookScreen.renderItemFrames(poseStack, 60, 12, false, recipe.spirits.size());
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends SpiritFocusingRecipe> getRecipeClass() {
        return SpiritFocusingRecipe.class;
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
    public void setIngredients(SpiritFocusingRecipe recipe, IIngredients iIngredients) {
        ArrayList<ItemStack> items = new ArrayList<>(Arrays.asList(recipe.input.getItems()));

        recipe.spirits.forEach(ingredient -> items.add(ingredient.getStack()));
        iIngredients.setInputs(VanillaTypes.ITEM, items);
        iIngredients.setOutputs(VanillaTypes.ITEM, recipe.output.getStacks());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SpiritFocusingRecipe recipe, IIngredients iIngredients) {
        int index = 0;
        index = ProgressionBookScreen.addItemsToJei(iRecipeLayout, 60, 12, false, recipe.spirits, index);

        iRecipeLayout.getItemStacks().init(index + 1, true, 61, 56);
        iRecipeLayout.getItemStacks().set(index + 1, Arrays.asList(recipe.input.getItems()));

        iRecipeLayout.getItemStacks().init(index + 2, true, 61, 123);
        iRecipeLayout.getItemStacks().set(index + 2, recipe.output.getStacks());
    }
}