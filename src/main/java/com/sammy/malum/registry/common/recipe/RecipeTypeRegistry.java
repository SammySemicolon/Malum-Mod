package com.sammy.malum.registry.common.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.*;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypeRegistry {

    public static final LazyRegistrar<RecipeType<?>> RECIPE_TYPES = LazyRegistrar.create(BuiltInRegistries.RECIPE_TYPE, MalumMod.MALUM);

    public static final RegistryObject<RecipeType<FavorOfTheVoidRecipe>> VOID_FAVOR = RECIPE_TYPES.register(FavorOfTheVoidRecipe.NAME, () -> registerRecipeType(FavorOfTheVoidRecipe.NAME));
    public static final RegistryObject<RecipeType<SpiritInfusionRecipe>> SPIRIT_INFUSION = RECIPE_TYPES.register(SpiritInfusionRecipe.NAME, () -> registerRecipeType(SpiritInfusionRecipe.NAME));
    public static final RegistryObject<RecipeType<RunicWorkbenchRecipe>> RUNEWORKING = RECIPE_TYPES.register(RunicWorkbenchRecipe.NAME, () -> registerRecipeType(RunicWorkbenchRecipe.NAME));
    public static final RegistryObject<RecipeType<SpiritFocusingRecipe>> SPIRIT_FOCUSING = RECIPE_TYPES.register(SpiritFocusingRecipe.NAME, () -> registerRecipeType(SpiritFocusingRecipe.NAME));
    public static final RegistryObject<RecipeType<SpiritTransmutationRecipe>> SPIRIT_TRANSMUTATION = RECIPE_TYPES.register(SpiritTransmutationRecipe.NAME, () -> registerRecipeType(SpiritTransmutationRecipe.NAME));
    public static final RegistryObject<RecipeType<SpiritRepairRecipe>> SPIRIT_REPAIR = RECIPE_TYPES.register(SpiritRepairRecipe.NAME, () -> registerRecipeType(SpiritRepairRecipe.NAME));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>() {
            public String toString() {
                return MalumMod.MALUM + ":" + identifier;
            }
        };
    }
}