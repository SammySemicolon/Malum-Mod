package com.sammy.malum;

import com.sammy.malum.core.data.BlockStateProvider;
import com.sammy.malum.core.data.BlockTagProvider;
import com.sammy.malum.core.data.ItemModelProvider;
import com.sammy.malum.core.data.LangProvider;
import com.sammy.malum.core.init.MalumBiomes;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.MalumTileEntities;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static com.sammy.malum.core.init.MalumBiomes.BIOMES;
import static com.sammy.malum.core.init.MalumBlocks.BLOCKS;
import static com.sammy.malum.core.init.MalumItems.ITEMS;
import static com.sammy.malum.core.init.MalumSounds.SOUNDS;
import static com.sammy.malum.core.init.MalumTileEntities.TILE_ENTITIES;

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
        BIOMES.register(modBus);
        MalumSounds.init();
        
        modBus.addListener(this::gatherData);
    }
    
    public void gatherData(GatherDataEvent evt)
    {
        evt.getGenerator().addProvider(new BlockStateProvider(evt.getGenerator(), evt.getExistingFileHelper()));
        evt.getGenerator().addProvider(new ItemModelProvider(evt.getGenerator(), evt.getExistingFileHelper()));
        evt.getGenerator().addProvider(new LangProvider(evt.getGenerator()));
        evt.getGenerator().addProvider(new BlockTagProvider(evt.getGenerator()));
    }
}