package com.sammy.malum.compability.emi.recipes;

import com.google.common.collect.Lists;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.compability.emi.EMIHandler;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpiritInfusionEmiRecipe implements EmiRecipe {
    private static final ResourceLocation BACKGROUND_LOCATION = MalumMod.malumPath("textures/gui/spirit_infusion_jei.png");

    private final SpiritInfusionRecipe recipe;

    private final List<EmiIngredient> inputs;
    private final List<EmiIngredient> extraItems;
    private final List<EmiIngredient> spirits;
    private final List<EmiStack> result;

    public SpiritInfusionEmiRecipe(SpiritInfusionRecipe recipe) {
        this.recipe = recipe;

        this.inputs = Lists.newArrayList();
        this.inputs.add(EMIHandler.convertIngredientWithCount(recipe.input));
        this.inputs.addAll(this.extraItems = EMIHandler.convertIngredientWithCounts(recipe.extraItems));
        this.inputs.addAll(this.spirits = EMIHandler.convertSpiritWithCounts(recipe.spirits));

        this.result = List.of(EmiStack.of(recipe.output));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIHandler.SPIRIT_INFUSION;
    }

    @Override
    public ResourceLocation getId() {
        return recipe.getId();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return result;
    }

    @Override
    public int getDisplayWidth() {
        return 142;
    }

    @Override
    public int getDisplayHeight() {
        return 185;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BACKGROUND_LOCATION, 0, 0, this.getDisplayWidth(), this.getDisplayHeight(), 0, 0);

        widgets.addDrawable(0, 0, 0, 0, (guiGraphics, mx, my, d) ->
        {
            ArcanaCodexHelper.renderItemFrames(guiGraphics.pose(), spirits.size(), 19, 48, true);
            if(!extraItems.isEmpty())
            {
                ArcanaCodexHelper.renderItemFrames(guiGraphics.pose(), extraItems.size(), 103, 48, true);
            }
        });

        EMIHandler.addItems(widgets, 19, 48, true, spirits);
        EMIHandler.addItems(widgets, 103, 48, true, extraItems);

        widgets.addSlot(inputs.get(0), 62, 56).drawBack(false);

        widgets.addSlot(result.get(0), 62, 123).recipeContext(this).drawBack(false);
    }
}
