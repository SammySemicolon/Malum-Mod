package com.sammy.malum.compability.emi.recipes;

import com.google.common.collect.Lists;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.FavorOfTheVoidRecipe;
import com.sammy.malum.common.recipe.RunicWorkbenchRecipe;
import com.sammy.malum.compability.emi.EMIHandler;
import com.sammy.malum.registry.common.item.ItemRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeepingWellEmiRecipe implements EmiRecipe {

    private static final ResourceLocation BACKGROUND_LOCATION = MalumMod.malumPath("textures/gui/weeping_well_emi.png");

    private final FavorOfTheVoidRecipe recipe;

    private final List<EmiIngredient> input = Lists.newArrayList();
    private final List<EmiStack> output = Lists.newArrayList();

    public WeepingWellEmiRecipe(FavorOfTheVoidRecipe recipe) {
        this.recipe = recipe;

        input.add(EmiIngredient.of(recipe.input));
        output.add(EmiStack.of(recipe.output));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIHandler.WEEPING;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return recipe.getId();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 142;
    }

    @Override
    public int getDisplayHeight() {
        return 185 - 27;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BACKGROUND_LOCATION, 0, 0, this.getDisplayWidth(), this.getDisplayHeight(), 0, 0);

        widgets.addSlot(input.get(0), 63, 57 - 27).drawBack(false);
        widgets.addSlot(output.get(0), 63, 124 - 27).drawBack(false);

    }
}
