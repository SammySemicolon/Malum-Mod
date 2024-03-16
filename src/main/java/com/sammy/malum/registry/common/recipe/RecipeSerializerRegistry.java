package com.sammy.malum.registry.common.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.common.recipe.vanilla.MetalNodeBlastingRecipe;
import com.sammy.malum.common.recipe.vanilla.MetalNodeSmeltingRecipe;
import com.sammy.malum.common.recipe.vanilla.NodeCookingSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializerRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MalumMod.MALUM);

    public static final RegistryObject<RecipeSerializer<MetalNodeBlastingRecipe>> METAL_NODE_BLASTING_SERIALIZER = RECIPE_SERIALIZERS.register(MetalNodeBlastingRecipe.NAME, () -> new NodeCookingSerializer<>(MetalNodeBlastingRecipe::new, 100));
    public static final RegistryObject<RecipeSerializer<MetalNodeSmeltingRecipe>> METAL_NODE_SMELTING_SERIALIZER = RECIPE_SERIALIZERS.register(MetalNodeSmeltingRecipe.NAME, () -> new NodeCookingSerializer<>(MetalNodeSmeltingRecipe::new, 200));

    public static final RegistryObject<RecipeSerializer<SpiritInfusionRecipe>> INFUSION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(SpiritInfusionRecipe.NAME, SpiritInfusionRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<RunicWorkbenchRecipe>> RUNEWORKING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(RunicWorkbenchRecipe.NAME, RunicWorkbenchRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<SpiritFocusingRecipe>> FOCUSING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(SpiritFocusingRecipe.NAME, SpiritFocusingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<SpiritRepairRecipe>> REPAIR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(SpiritRepairRecipe.NAME, SpiritRepairRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<SpiritTransmutationRecipe>> SPIRIT_TRANSMUTATION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(SpiritTransmutationRecipe.NAME, SpiritTransmutationRecipe.Serializer::new);

    public static final RegistryObject<RecipeSerializer<FavorOfTheVoidRecipe>> VOID_FAVOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(FavorOfTheVoidRecipe.NAME, FavorOfTheVoidRecipe.Serializer::new);
}
