package com.sammy.malum.core.registry.content;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.BlockTransmutationRecipe;
import com.sammy.malum.common.recipe.NBTCarryRecipe;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializerRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MalumMod.MODID);

    public static final RegistryObject<RecipeSerializer<SpiritInfusionRecipe>> INFUSION_RECIPE_SERIALIZER = RECIPE_TYPES.register(SpiritInfusionRecipe.NAME, SpiritInfusionRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<NBTCarryRecipe>> NBT_CARRY_RECIPE_SERIALIZER = RECIPE_TYPES.register(NBTCarryRecipe.NAME, NBTCarryRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<BlockTransmutationRecipe>> BLOCK_TRANSMUTATION_RECIPE_SERIALIZER = RECIPE_TYPES.register(BlockTransmutationRecipe.NAME, BlockTransmutationRecipe.Serializer::new);
}
