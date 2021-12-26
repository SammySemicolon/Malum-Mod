package com.sammy.malum;

import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.data.*;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static com.sammy.malum.core.registry.block.BlockRegistry.BLOCKS;
import static com.sammy.malum.core.registry.block.BlockEntityRegistry.TILE_ENTITIES;
import static com.sammy.malum.core.registry.content.RecipeSerializerRegistry.RECIPE_SERIALIZERS;
import static com.sammy.malum.core.registry.enchantment.MalumEnchantments.ENCHANTMENTS;
import static com.sammy.malum.core.registry.item.ItemRegistry.ITEMS;
import static com.sammy.malum.core.registry.misc.AttributeRegistry.ATTRIBUTES;
import static com.sammy.malum.core.registry.misc.ContainerRegistry.CONTAINERS;
import static com.sammy.malum.core.registry.misc.EffectRegistry.EFFECTS;
import static com.sammy.malum.core.registry.misc.EntityRegistry.ENTITY_TYPES;
import static com.sammy.malum.core.registry.misc.ParticleRegistry.PARTICLES;
import static com.sammy.malum.core.registry.misc.SoundRegistry.SOUNDS;
import static com.sammy.malum.core.registry.worldgen.FeatureRegistry.FEATURE_TYPES;

@SuppressWarnings("unused")
@Mod(MalumMod.MODID)
public class MalumMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "malum";
    public static final Random RANDOM = new Random();

    public MalumMod()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);

        ENCHANTMENTS.register(modBus);
        BLOCKS.register(modBus);
        TILE_ENTITIES.register(modBus);
        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);
        EFFECTS.register(modBus);
        PARTICLES.register(modBus);
        SOUNDS.register(modBus);
        CONTAINERS.register(modBus);
        ATTRIBUTES.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
        FEATURE_TYPES.register(modBus);
        modBus.addListener(this::gatherData);
    }


    public void gatherData(GatherDataEvent event)
    {
        BlockTagsProvider provider = new MalumBlockTags(event.getGenerator(), event.getExistingFileHelper());
        event.getGenerator().addProvider(new MalumBlockStates(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new MalumItemModels(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new MalumLang(event.getGenerator()));
        event.getGenerator().addProvider(provider);
        event.getGenerator().addProvider(new MalumBlockLootTables(event.getGenerator()));
        event.getGenerator().addProvider(new MalumItemTags(event.getGenerator(), provider, event.getExistingFileHelper()));
        event.getGenerator().addProvider(new MalumRecipes(event.getGenerator()));
        event.getGenerator().addProvider(new MalumSpiritInfusionRecipes(event.getGenerator()));
        event.getGenerator().addProvider(new MalumSpiritCrucibleRecipes(event.getGenerator()));
        event.getGenerator().addProvider(new MalumBlockTransmutationRecipes(event.getGenerator()));
    }
}