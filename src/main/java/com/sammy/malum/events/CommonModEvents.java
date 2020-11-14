package com.sammy.malum.events;

import com.sammy.malum.MalumMod;
import com.sammy.malum.capabilities.IMalumData;
import com.sammy.malum.capabilities.MalumData;
import com.sammy.malum.capabilities.MalumDataStorage;
import com.sammy.malum.init.ModFeatures;
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
import static com.sammy.malum.init.ModFeatures.CONFIGURED_COAL_ORE;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents
{
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent e)
    {
        CapabilityManager.INSTANCE.register(IMalumData.class, new MalumDataStorage(), MalumData::new);
    }
}