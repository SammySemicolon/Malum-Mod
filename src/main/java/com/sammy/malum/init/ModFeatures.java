package com.sammy.malum.init;

import com.sammy.malum.MalumMod;
import com.sammy.malum.capabilities.IMalumData;
import com.sammy.malum.capabilities.MalumData;
import com.sammy.malum.capabilities.MalumDataStorage;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.sammy.malum.MalumMod.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures
{
    public static ConfiguredFeature<?, ?> CONFIGURED_COAL_ORE;
    
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent e)
    {
        CONFIGURED_COAL_ORE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.brittle_bedrock.getDefaultState(), 17)).range(128).square().func_242731_b(20);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(MalumMod.MODID, "coal_ore"), CONFIGURED_COAL_ORE);
    }
}