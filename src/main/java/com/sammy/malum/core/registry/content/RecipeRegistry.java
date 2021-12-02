package com.sammy.malum.core.registry.content;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.NBTCarryRecipe;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class RecipeRegistry {

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {

        Registry.register(Registry.RECIPE_TYPE, SpiritInfusionRecipe.NAME, SpiritInfusionRecipe.RECIPE_TYPE);
        event.getRegistry().register(SpiritInfusionRecipe.SERIALIZER.setRegistryName(SpiritInfusionRecipe.NAME));

        Registry.register(Registry.RECIPE_TYPE, NBTCarryRecipe.NAME, NBTCarryRecipe.RECIPE_TYPE);
        event.getRegistry().register(NBTCarryRecipe.SERIALIZER.setRegistryName(NBTCarryRecipe.NAME));
    }
}
