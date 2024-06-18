package com.sammy.malum.compability.emi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.SpiritTransmutationRecipe;
import com.sammy.malum.compability.emi.recipes.*;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.registry.common.SpiritRiteRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class EMIHandler implements EmiPlugin {

    private static final EmiStack SPIRIT_INFUSION_WORKSTATION = EmiStack.of(ItemRegistry.SPIRIT_ALTAR.get());
    public static final EmiRecipeCategory SPIRIT_INFUSION = new EmiRecipeCategory(
            MalumMod.malumPath("spirit_infusion"),
            SPIRIT_INFUSION_WORKSTATION
    );

    private static final EmiStack SPIRIT_FOCUSING_WORKSTATION = EmiStack.of(ItemRegistry.SPIRIT_CRUCIBLE.get());
    public static final EmiRecipeCategory SPIRIT_FOCUSING = new EmiRecipeCategory(
            MalumMod.malumPath("spirit_focusing"),
            SPIRIT_FOCUSING_WORKSTATION
    );

    private static final EmiStack SPIRIT_TRANSMUTATION_WORKSTATION = EmiStack.of(ItemRegistry.SOULWOOD_TOTEM_BASE.get());
    public static final EmiRecipeCategory SPIRIT_TRANSMUTATION = new EmiRecipeCategory(
            MalumMod.malumPath("spirit_transmutation"),
            SPIRIT_TRANSMUTATION_WORKSTATION
    );

    private static final EmiStack SPIRIT_RITE_WORKSTATION = EmiStack.of(ItemRegistry.RUNEWOOD_TOTEM_BASE.get());
    public static final EmiRecipeCategory SPIRIT_RITE = new EmiRecipeCategory(
            MalumMod.malumPath("spirit_rite"),
            SPIRIT_RITE_WORKSTATION
    );

    private static final EmiStack SPIRIT_REPAIR_WORKSTATION = EmiStack.of(ItemRegistry.REPAIR_PYLON.get());
    public static final EmiRecipeCategory SPIRIT_REPAIR = new EmiRecipeCategory(
            MalumMod.malumPath("spirit_repair"),
            SPIRIT_REPAIR_WORKSTATION
    );

    private <C extends Container, R extends Recipe<C>, E extends EmiRecipe> void registerRecipeTypeCategory(EmiRegistry registry, EmiRecipeCategory category, EmiStack workstation) {
        registry.addCategory(category);
        registry.addWorkstation(category, workstation);
    }

    private <C extends Container, R extends Recipe<C>, E extends EmiRecipe> void registerRecipeType(EmiRegistry registry, EmiRecipeCategory category, EmiStack workstation, RecipeType<R> type, Function<R, E> builder) {
        registerRecipeTypeCategory(registry, category, workstation);
        registry.getRecipeManager().getAllRecipesFor(type).forEach((recipe) -> registry.addRecipe(builder.apply(recipe)));
    }


    @Override
    public void register(EmiRegistry registry) {
        this.registerRecipeType(registry, SPIRIT_INFUSION, SPIRIT_INFUSION_WORKSTATION, RecipeTypeRegistry.SPIRIT_INFUSION.get(), SpiritInfusionEmiRecipe::new);
        this.registerRecipeType(registry, SPIRIT_FOCUSING, SPIRIT_FOCUSING_WORKSTATION, RecipeTypeRegistry.SPIRIT_FOCUSING.get(), SpiritFocusingEmiRecipe::new);

        this.registerRecipeTypeCategory(registry, SPIRIT_TRANSMUTATION, SPIRIT_TRANSMUTATION_WORKSTATION);
        List<SpiritTransmutationRecipe> transmutation = registry.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.SPIRIT_TRANSMUTATION.get());
        List<SpiritTransmutationRecipe> leftovers = Lists.newArrayList();
        Map<String, List<SpiritTransmutationRecipe>> groups = Maps.newLinkedHashMap();
        transmutation.forEach((recipe) ->
        {
            if (recipe.group != null) {
                List<SpiritTransmutationRecipe> group = groups.computeIfAbsent(recipe.group, (k) -> Lists.newArrayList());
                group.add(recipe);
            } else {
                leftovers.add(recipe);
            }
        });
        groups.values().stream()
                .map(SpiritTransmuationRecipeWrapper::new)
                .forEach((recipe) -> registry.addRecipe(new SpiritTransmutationEmiRecipe(recipe)));
        leftovers.stream()
                .map(List::of)
                .map(SpiritTransmuationRecipeWrapper::new)
                .forEach((recipe) -> registry.addRecipe(new SpiritTransmutationEmiRecipe(recipe)));

        this.registerRecipeTypeCategory(registry, SPIRIT_RITE, SPIRIT_RITE_WORKSTATION);
        SpiritRiteRegistry.RITES.forEach((rite) -> registry.addRecipe(new SpiritRiteEmiRecipe(rite)));

        this.registerRecipeType(registry, SPIRIT_REPAIR, SPIRIT_REPAIR_WORKSTATION, RecipeTypeRegistry.SPIRIT_REPAIR.get(), SpiritRepairEmiRecipe::new);
    }

    public static void addItems(WidgetHolder widgets, int left, int top, boolean vertical, List<EmiIngredient> ingredients) {
        int slots = ingredients.size();
        if (vertical) {
            top -= 10 * (slots - 1);
        } else {
            left -= 10 * (slots - 1);
        }
        for (int i = 0; i < slots; i++) {
            int offset = i * 20;
            int offsetLeft = left + 1 + (vertical ? 0 : offset);
            int offsetTop = top + 1 + (vertical ? offset : 0);
            widgets.addSlot(ingredients.get(i), offsetLeft, offsetTop).drawBack(false);
        }
    }

    public static EmiIngredient convertIngredientWithCount(IngredientWithCount ingredient) {
        return EmiIngredient.of(ingredient.ingredient, ingredient.count);
    }

    public static List<EmiIngredient> convertIngredientWithCounts(List<IngredientWithCount> ingredients) {
        return ingredients.stream().map(EMIHandler::convertIngredientWithCount).toList();
    }

    public static EmiIngredient convertSpiritWithCount(SpiritWithCount spirit) {
        return EmiIngredient.of(Ingredient.of(spirit.type.spiritShard.get()), spirit.count);
    }

    public static List<EmiIngredient> convertSpiritWithCounts(List<SpiritWithCount> spirits) {
        return spirits.stream().map(EMIHandler::convertSpiritWithCount).toList();
    }
}
