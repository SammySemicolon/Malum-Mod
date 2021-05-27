package com.sammy.malum;

import com.sammy.malum.core.data.*;
import com.sammy.malum.core.systems.particles.ParticleRendering;
import net.minecraft.data.BlockTagsProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static com.sammy.malum.core.init.MalumEffects.EFFECTS;
import static com.sammy.malum.core.init.MalumEntities.ENTITY_TYPES;
import static com.sammy.malum.core.init.MalumSounds.SOUNDS;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;
import static com.sammy.malum.core.init.blocks.MalumTileEntities.TILE_ENTITIES;
import static com.sammy.malum.core.init.enchantments.MalumEnchantments.ENCHANTMENTS;
import static com.sammy.malum.core.init.items.MalumItems.ITEMS;
import static com.sammy.malum.core.init.particles.MalumParticles.PARTICLES;
import static com.sammy.malum.core.init.worldgen.MalumFeatures.FEATURES;

@SuppressWarnings("unused")
@Mod("malum")
public class MalumMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "malum";
    public static final Random RANDOM = new Random();
    
    public MalumMod()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modBus);
        ENCHANTMENTS.register(modBus);
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TILE_ENTITIES.register(modBus);
        ENTITY_TYPES.register(modBus);
        EFFECTS.register(modBus);
        PARTICLES.register(modBus);
        SOUNDS.register(modBus);
        FEATURES.register(modBus);
        modBus.addListener(this::gatherData);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            MinecraftForge.EVENT_BUS.register(new ParticleRendering());
            return new Object();
        });
    }
    public void gatherData(GatherDataEvent evt)
    {
        BlockTagsProvider provider = new MalumBlockTagProvider(evt.getGenerator());
        evt.getGenerator().addProvider(new MalumBlockStateProvider(evt.getGenerator(), evt.getExistingFileHelper()));
        evt.getGenerator().addProvider(new MalumItemModelProvider(evt.getGenerator(), evt.getExistingFileHelper()));
        evt.getGenerator().addProvider(new MalumLangProvider(evt.getGenerator()));
        evt.getGenerator().addProvider(provider);
        evt.getGenerator().addProvider(new MalumLootTableProvider(evt.getGenerator()));
        evt.getGenerator().addProvider(new MalumItemTagProvider(evt.getGenerator(),provider));
        evt.getGenerator().addProvider(new MalumRecipeProvider(evt.getGenerator()));
    }
}