package com.sammy.malum.registry.common.recipe;

import com.sammy.malum.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.common.recipe.void_favor.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.registries.*;

public class RecipeTypeRegistry {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MalumMod.MALUM);

    public static final DeferredHolder<RecipeType<?>, RecipeType<FavorOfTheVoidRecipe>> VOID_FAVOR = RECIPE_TYPES.register(FavorOfTheVoidRecipe.NAME, () -> registerRecipeType(FavorOfTheVoidRecipe.NAME));
    public static final DeferredHolder<RecipeType<?>, RecipeType<SpiritInfusionRecipe>> SPIRIT_INFUSION = RECIPE_TYPES.register(SpiritInfusionRecipe.NAME, () -> registerRecipeType(SpiritInfusionRecipe.NAME));
    public static final DeferredHolder<RecipeType<?>, RecipeType<RunicWorkbenchRecipe>> RUNEWORKING = RECIPE_TYPES.register(RunicWorkbenchRecipe.NAME, () -> registerRecipeType(RunicWorkbenchRecipe.NAME));
    public static final DeferredHolder<RecipeType<?>, RecipeType<SpiritFocusingRecipe>> SPIRIT_FOCUSING = RECIPE_TYPES.register(SpiritFocusingRecipe.NAME, () -> registerRecipeType(SpiritFocusingRecipe.NAME));
    public static final DeferredHolder<RecipeType<?>, RecipeType<SpiritTransmutationRecipe>> SPIRIT_TRANSMUTATION = RECIPE_TYPES.register(SpiritTransmutationRecipe.NAME, () -> registerRecipeType(SpiritTransmutationRecipe.NAME));
    public static final DeferredHolder<RecipeType<?>, RecipeType<SpiritRepairRecipe>> SPIRIT_REPAIR = RECIPE_TYPES.register(SpiritRepairRecipe.NAME, () -> registerRecipeType(SpiritRepairRecipe.NAME));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>() {
            public String toString() {
                return MalumMod.MALUM + ":" + identifier;
            }
        };
    }
}