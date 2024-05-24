package com.sammy.malum.compability.jei.categories;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

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
    public void draw(SpiritInfusionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        overlay.draw(guiGraphics);
        int spiritOffset = recipe.spirits.size() > 5 ? (recipe.spirits.size()-5)*10 : 0;
        ArcanaCodexHelper.renderItemFrames(guiGraphics.pose(), recipe.spirits.size(), 19, 48+spiritOffset, true);
        if (!recipe.extraItems.isEmpty()) {
            int itemOffset = recipe.extraItems.size() > 5 ? (recipe.extraItems.size()-5)*10 : 0;
            ArcanaCodexHelper.renderItemFrames(guiGraphics.pose(), recipe.extraItems.size(), 103, 48+itemOffset, true);
        }
    }

    @Override
    public RecipeType<SpiritInfusionRecipe> getRecipeType() {
        return JEIHandler.SPIRIT_INFUSION;
    }


    @Override
    public Component getTitle() {
        return Component.translatable("malum.jei." + UID.getPath());
    }

    @NotNull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @NotNull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpiritInfusionRecipe recipe, IFocusGroup focuses) {
        int spiritOffset = recipe.spirits.size() > 5 ? (recipe.spirits.size()-5)*10 : 0;
        int itemOffset = recipe.extraItems.size() > 5 ? (recipe.extraItems.size()-5)*10 : 0;
        JEIHandler.addItemsToJei(builder, RecipeIngredientRole.INPUT, 20, 49+spiritOffset, true, recipe.spirits);
        JEIHandler.addItemsToJei(builder, RecipeIngredientRole.INPUT, 104, 49+itemOffset, true, recipe.extraItems);

        builder.addSlot(RecipeIngredientRole.INPUT, 63, 57)
                .addItemStacks(recipe.input.getStacks());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 124)
                .addItemStack(recipe.output);
    }
}
