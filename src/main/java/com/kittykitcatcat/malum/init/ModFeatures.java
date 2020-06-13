package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.world.biomes.SpiritForest;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import static com.kittykitcatcat.malum.world.biomes.SpiritForest.addSpiritwoodTree;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures
{
    public static Biome spirit_forest;

    // BIOME REGISTRATION
    @SubscribeEvent
    public static void onBiomeRegistry(final RegistryEvent.Register<Biome> BiomeRegistryEvent)
    {
        IForgeRegistry<Biome> registry = BiomeRegistryEvent.getRegistry();
        spirit_forest = register(registry, new SpiritForest(), "spirit_forest", 4, BiomeManager.BiomeType.WARM, BiomeDictionary.Type.CONIFEROUS);

        for (Biome biome : ForgeRegistries.BIOMES)
        {
            if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS))
            {
                addSpiritwoodTree(biome);
            }
        }
    }

    public static Biome register(IForgeRegistry<Biome> registry, Biome biome, String name, int weight, BiomeManager.BiomeType type, BiomeDictionary.Type... types)
    {
        registry.register(biome.setRegistryName(MalumMod.MODID, name));
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biome, weight));
        BiomeDictionary.addTypes(biome, types);
        return biome;
    }
}