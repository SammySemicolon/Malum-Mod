package com.sammy.malum.registry.common.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.common.recipe.furnace.crystallarium.ConjunctureCrystallariumRecipe;
import com.sammy.malum.common.recipe.derealization.OreDerealizationRecipe;
import com.sammy.malum.common.recipe.node_cooking.IngredientBasedBlastingRecipe;
import com.sammy.malum.common.recipe.node_cooking.IngredientBasedSmeltingRecipe;
import com.sammy.malum.common.recipe.node_cooking.NodeCookingSerializer;
import com.sammy.malum.common.recipe.spirit_repair.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.modules.toolkit.recipe.LodestoneRecipeSerializer;

public class MalumRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MalumMod.MALUM);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<IngredientBasedBlastingRecipe>> METAL_NODE_BLASTING_SERIALIZER = RECIPE_SERIALIZERS.register(IngredientBasedBlastingRecipe.NAME, () -> new NodeCookingSerializer<>(IngredientBasedBlastingRecipe::new, 100));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<IngredientBasedSmeltingRecipe>> METAL_NODE_SMELTING_SERIALIZER = RECIPE_SERIALIZERS.register(IngredientBasedSmeltingRecipe.NAME, () -> new NodeCookingSerializer<>(IngredientBasedSmeltingRecipe::new, 200));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SpiritInfusionRecipe>> INFUSION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(SpiritInfusionRecipe.NAME, () -> new LodestoneRecipeSerializer<>(SpiritInfusionRecipe.CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RuneworkingRecipe>> RUNEWORKING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(RuneworkingRecipe.NAME, () -> new LodestoneRecipeSerializer<>(RuneworkingRecipe.CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SoulBindingRecipe>> SOUL_BINDING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(SoulBindingRecipe.NAME, () -> new LodestoneRecipeSerializer<>(SoulBindingRecipe.CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SpiritFocusingRecipe>> FOCUSING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(SpiritFocusingRecipe.NAME, () -> new LodestoneRecipeSerializer<>(SpiritFocusingRecipe.CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SpiritRepairRecipe>> REPAIR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(SpiritRepairRecipe.NAME, () -> new LodestoneRecipeSerializer<>(SpiritRepairRecipe.CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<UnchainedTransmutationRecipe>> SPIRIT_TRANSMUTATION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(UnchainedTransmutationRecipe.NAME, () -> new LodestoneRecipeSerializer<>(UnchainedTransmutationRecipe.CODEC));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<VoidFavorRecipe>> VOID_FAVOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(VoidFavorRecipe.NAME, () -> new LodestoneRecipeSerializer<>(VoidFavorRecipe.CODEC));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ConjunctureCrystallariumRecipe>> CONJUNCTURE_CRYSTALLARIUM_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ConjunctureCrystallariumRecipe.NAME, () -> new LodestoneRecipeSerializer<>(ConjunctureCrystallariumRecipe.CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<OreDerealizationRecipe>> ORE_DEREALIZATION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(OreDerealizationRecipe.NAME, () -> new LodestoneRecipeSerializer<>(OreDerealizationRecipe.CODEC));
}
