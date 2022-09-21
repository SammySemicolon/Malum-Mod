 package com.sammy.malum.core.setup.content.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.*;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.FarmersDelight;

public class RecipeTypeRegistry {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE.key(), MalumMod.MALUM);

    public static final RegistryObject<RecipeType<SpiritInfusionRecipe>> SPIRIT_INFUSION = RECIPE_TYPES.register(SpiritInfusionRecipe.NAME, () -> registerRecipeType(SpiritInfusionRecipe.NAME));
    public static final RegistryObject<RecipeType<SpiritFocusingRecipe>> SPIRIT_FOCUSING = RECIPE_TYPES.register(SpiritFocusingRecipe.NAME, () -> registerRecipeType(SpiritFocusingRecipe.NAME));
    public static final RegistryObject<RecipeType<SpiritTransmutationRecipe>> SPIRIT_TRANSMUTATION = RECIPE_TYPES.register(SpiritTransmutationRecipe.NAME, () -> registerRecipeType(SpiritTransmutationRecipe.NAME));
    public static final RegistryObject<RecipeType<SpiritRepairRecipe>> SPIRIT_REPAIR = RECIPE_TYPES.register(SpiritRepairRecipe.NAME, () -> registerRecipeType(SpiritRepairRecipe.NAME));
    public static final RegistryObject<RecipeType<AugmentingRecipe>> AUGMENTING = RECIPE_TYPES.register(AugmentingRecipe.NAME, () -> registerRecipeType(AugmentingRecipe.NAME));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>() {
            public String toString() {
                return MalumMod.MALUM + ":" + identifier;
            }
        };
    }
}