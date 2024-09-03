package com.sammy.malum.compability.rei.category;

import com.google.common.collect.Lists;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.RunicWorkbenchRecipe;
import com.sammy.malum.compability.rei.REIHandler;
import com.sammy.malum.registry.common.item.ItemRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.sammy.malum.compability.jei.categories.RuneworkingRecipeCategory.UID;

public class RuneworkingRecipeCategory implements DisplayCategory<RuneworkingRecipeCategory.RuneworkingDisplay> {

    public static final MutableComponent TITLE = Component.translatable("malum.jei." + UID.getPath());
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(ItemRegistry.RUNIC_WORKBENCH.get());

    @Override
    public CategoryIdentifier<? extends RuneworkingDisplay> getCategoryIdentifier() {
        return REIHandler.RUNEWORKING;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public List<Widget> setupDisplay(RuneworkingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        int width = 142;
        int height = 185;

        Point startPoint = new Point(bounds.getCenterX() - 64, bounds.getCenterY() - 8);
        widgets.add(Widgets.createTexturedWidget(MalumMod.malumPath("textures/gui/runeworking_jei.png"),
                startPoint.x + 50, startPoint.y - 9,
                0, 0, width, height, width, height, width, height));


        widgets.add(Widgets.createSlot(new Point(63, 14))
                .entries(display.secondary.get(0)).markInput().disableBackground());
        widgets.add(Widgets.createSlot(new Point(63, 57))
                .entries(display.primary.get(0)).markInput().disableBackground());
        widgets.add(Widgets.createSlot(new Point(63, 124))
                .entries(display.output.get(0)).markOutput().disableBackground());


        /*
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 14)
                .addItemStacks(recipe.secondaryInput.getStacks());
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 57)
                .addItemStacks(recipe.primaryInput.getStacks());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 124)
                .addItemStack(recipe.output);
         */

        return widgets;
    }

    public static class RuneworkingDisplay implements Display {

        private final List<EntryIngredient> primary = Lists.newArrayList();
        private final List<EntryIngredient> secondary = Lists.newArrayList();

        private final List<EntryIngredient> combined = Lists.newArrayList();

        private final List<EntryIngredient> output = Lists.newArrayList();;

        public RuneworkingDisplay(RunicWorkbenchRecipe recipe) {

            primary.add(EntryIngredients.ofItemStacks(recipe.primaryInput.getStacks()));
            secondary.add(EntryIngredients.ofItemStacks(recipe.secondaryInput.getStacks()));

            combined.addAll(primary);
            combined.addAll(secondary);

            output.add(EntryIngredients.of(recipe.output));
        }

        @Override
        public List<EntryIngredient> getInputEntries() {
            return combined;
        }

        @Override
        public List<EntryIngredient> getOutputEntries() {
            return output;
        }

        @Override
        public CategoryIdentifier<?> getCategoryIdentifier() {
            return REIHandler.RUNEWORKING;
        }
    }
}
