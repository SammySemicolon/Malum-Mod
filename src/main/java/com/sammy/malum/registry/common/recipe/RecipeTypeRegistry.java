package com.sammy.malum.registry.common.recipe;

import com.sammy.malum.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.common.recipe.spirit.focusing.*;
import com.sammy.malum.common.recipe.spirit.infusion.*;
import com.sammy.malum.common.recipe.void_favor.*;
import com.sammy.malum.core.systems.recipe.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.registries.*;

public class RecipeTypeRegistry {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MalumMod.MALUM);

    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SingleRecipeInput, FavorOfTheVoidRecipe>> VOID_FAVOR = registerRecipeType(FavorOfTheVoidRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SpiritBasedRecipeInput, SpiritInfusionRecipe>> SPIRIT_INFUSION = registerRecipeType(SpiritInfusionRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SpiritBasedRecipeInput>> RUNEWORKING = registerRecipeType(RunicWorkbenchRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SpiritBasedRecipeInput, SpiritFocusingRecipe>> SPIRIT_FOCUSING = registerRecipeType(SpiritFocusingRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SingleRecipeInput>> SPIRIT_TRANSMUTATION = registerRecipeType(SpiritTransmutationRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SpiritBasedRecipeInput>> SPIRIT_REPAIR = registerRecipeType(SpiritRepairRecipe.NAME);

    public static <T extends RecipeInput, K extends Recipe<T>> DeferredHolder<RecipeType<?>, LodestoneRecipeType<T, K>> registerRecipeType(ResourceLocation identifier) {
        return RECIPE_TYPES.register(identifier.getPath(), () -> new LodestoneRecipeType<>(identifier));
    }
}