package com.sammy.malum.registry.common.recipe;

import com.sammy.malum.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.common.recipe.furnace.crystallarium.ConjunctureCrystallariumRecipe;
import com.sammy.malum.common.recipe.derealization.OreDerealizationRecipe;
import com.sammy.malum.common.recipe.spirit_repair.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.registries.*;
import team.lodestar.lodestone.modules.toolkit.recipe.LodestoneRecipeType;

public class MalumRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MalumMod.MALUM);

    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SpiritInfusionRecipe>> SPIRIT_INFUSION = registerRecipeType(SpiritInfusionRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<RuneworkingRecipe>> RUNEWORKING = registerRecipeType(RuneworkingRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SoulBindingRecipe>> SOUL_BINDING = registerRecipeType(SoulBindingRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SpiritFocusingRecipe>> SPIRIT_FOCUSING = registerRecipeType(SpiritFocusingRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<UnchainedTransmutationRecipe>> UNCHAINED_TRANSMUTATION = registerRecipeType(UnchainedTransmutationRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SpiritRepairRecipe>> SPIRIT_REPAIR = registerRecipeType(SpiritRepairRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<VoidFavorRecipe>> VOID_FAVOR = registerRecipeType(VoidFavorRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<OreDerealizationRecipe>> ORE_DEREALIZATION = registerRecipeType(OreDerealizationRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<ConjunctureCrystallariumRecipe>> CONJUNCTURE_CRYSTALLARIUM = registerRecipeType(ConjunctureCrystallariumRecipe.NAME);

    public static <T extends Recipe<?>> DeferredHolder<RecipeType<?>, LodestoneRecipeType<T>> registerRecipeType(String identifier) {
        return RECIPE_TYPES.register(identifier, () -> new LodestoneRecipeType<>(MalumMod.malumPath(identifier)));
    }
}