package com.sammy.malum;

import com.sammy.malum.core.data.*;
import com.sammy.malum.core.init.MalumSounds;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static com.sammy.malum.core.init.MalumEntities.ENTITY_TYPES;
import static com.sammy.malum.core.init.MalumItems.ITEMS;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;
import static com.sammy.malum.core.init.blocks.MalumTileEntities.TILE_ENTITIES;
import static com.sammy.malum.core.init.worldgen.MalumFoliagePlacerTypes.FOLIAGE;
import static com.sammy.malum.core.init.worldgen.MalumSurfaceBuilders.BUILDERS;

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
    
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TILE_ENTITIES.register(modBus);
        ENTITY_TYPES.register(modBus);
        FOLIAGE.register(modBus);
        BUILDERS.register(modBus);
        MalumSounds.init();
        
        modBus.addListener(this::gatherData);
    }
    
    public void gatherData(GatherDataEvent evt)
    {
        evt.getGenerator().addProvider(new MalumBlockStateProvider(evt.getGenerator(), evt.getExistingFileHelper()));
        evt.getGenerator().addProvider(new MalumItemModelProvider(evt.getGenerator(), evt.getExistingFileHelper()));
        evt.getGenerator().addProvider(new MalumLangProvider(evt.getGenerator()));
        evt.getGenerator().addProvider(new MalumBlockTagProvider(evt.getGenerator()));
        evt.getGenerator().addProvider(new MalumLootTableProvider(evt.getGenerator()));
    }
}