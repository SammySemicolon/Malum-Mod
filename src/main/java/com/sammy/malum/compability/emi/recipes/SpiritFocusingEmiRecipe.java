package com.sammy.malum.compability.emi.recipes;

import com.google.common.collect.Lists;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
import com.sammy.malum.client.screen.codex.screens.AbstractProgressionCodexScreen;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.compability.emi.EMIHandler;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpiritFocusingEmiRecipe implements EmiRecipe {
    private static final ResourceLocation BACKGROUND_LOCATION = MalumMod.malumPath("textures/gui/spirit_focusing_jei.png");

    private final SpiritFocusingRecipe recipe;

    private final List<EmiIngredient> inputs;
    private final List<EmiIngredient> spirits;
    private final List<EmiStack> result;

    public SpiritFocusingEmiRecipe(SpiritFocusingRecipe recipe) {
        this.recipe = recipe;

        this.inputs = Lists.newArrayList();
        this.inputs.add(EmiIngredient.of(recipe.input));
        this.inputs.addAll(this.spirits = EMIHandler.convertSpiritWithCounts(recipe.spirits));

        this.result = List.of(EmiStack.of(recipe.output));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIHandler.SPIRIT_FOCUSING;
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
            if(!spirits.isEmpty())
            {
                ArcanaCodexHelper.renderItemFrames(guiGraphics.pose(), recipe.spirits.size(), 61, 12, false);
            }
        });

        EMIHandler.addItems(widgets, 61, 12, false, spirits);

        widgets.addSlot(inputs.get(0), 62, 56).catalyst(true).drawBack(false);

        widgets.addSlot(result.get(0), 62, 123).recipeContext(this).drawBack(false);
    }
}
