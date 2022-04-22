package com.sammy.malum.core.setup.content;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.BlockTransmutationRecipe;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import com.sammy.malum.common.recipe.vanilla.MetalNodeBlastingRecipe;
import com.sammy.malum.common.recipe.vanilla.MetalNodeRecipeSerializer;
import com.sammy.malum.common.recipe.vanilla.MetalNodeSmeltingRecipe;
import com.sammy.malum.common.recipe.vanilla.NBTCarryRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializerRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MalumMod.MODID);

    public static final RegistryObject<RecipeSerializer<NBTCarryRecipe>> NBT_CARRY_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(NBTCarryRecipe.NAME, NBTCarryRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<MetalNodeBlastingRecipe>> METAL_NODE_BLASTING_SERIALIZER = RECIPE_SERIALIZERS.register(MetalNodeBlastingRecipe.NAME, ()->new MetalNodeRecipeSerializer<>(MetalNodeBlastingRecipe::new, 100));
    public static final RegistryObject<RecipeSerializer<MetalNodeSmeltingRecipe>> METAL_NODE_SMELTING_SERIALIZER = RECIPE_SERIALIZERS.register(MetalNodeSmeltingRecipe.NAME, ()->new MetalNodeRecipeSerializer<>(MetalNodeSmeltingRecipe::new, 200));

    public static final RegistryObject<RecipeSerializer<SpiritInfusionRecipe>> INFUSION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(SpiritInfusionRecipe.NAME, SpiritInfusionRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<SpiritFocusingRecipe>> FOCUSING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(SpiritFocusingRecipe.NAME, SpiritFocusingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<SpiritRepairRecipe>> REPAIR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(SpiritRepairRecipe.NAME, SpiritRepairRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<BlockTransmutationRecipe>> BLOCK_TRANSMUTATION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(BlockTransmutationRecipe.NAME, BlockTransmutationRecipe.Serializer::new);
}
