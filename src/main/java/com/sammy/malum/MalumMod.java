package com.sammy.malum;

import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.compability.supplementaries.SupplementariesCompat;
import com.sammy.malum.compability.tetra.TetraCompat;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.data.*;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static com.sammy.malum.core.setup.client.ParticleRegistry.PARTICLES;
import static com.sammy.malum.core.setup.content.AttributeRegistry.ATTRIBUTES;
import static com.sammy.malum.core.setup.content.ContainerRegistry.CONTAINERS;
import static com.sammy.malum.core.setup.content.RecipeSerializerRegistry.RECIPE_SERIALIZERS;
import static com.sammy.malum.core.setup.content.SoundRegistry.SOUNDS;
import static com.sammy.malum.core.setup.content.block.BlockEntityRegistry.BLOCK_ENTITY_TYPES;
import static com.sammy.malum.core.setup.content.block.BlockRegistry.BLOCKS;
import static com.sammy.malum.core.setup.content.entity.EntityRegistry.ENTITY_TYPES;
import static com.sammy.malum.core.setup.content.item.ItemRegistry.ITEMS;
import static com.sammy.malum.core.setup.content.item.MalumEnchantments.ENCHANTMENTS;
import static com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry.EFFECTS;
import static com.sammy.malum.core.setup.content.worldgen.FeatureRegistry.FEATURE_TYPES;

@SuppressWarnings("unused")
@Mod(MalumMod.MALUM)
public class MalumMod {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MALUM = "malum";
    public static final Random RANDOM = new Random();

    public MalumMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);

        ENCHANTMENTS.register(modBus);
        BLOCKS.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);
        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);
        EFFECTS.register(modBus);
        PARTICLES.register(modBus);
        SOUNDS.register(modBus);
        CONTAINERS.register(modBus);
        ATTRIBUTES.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
        FEATURE_TYPES.register(modBus);

        TetraCompat.init();
        FarmersDelightCompat.init();
        SupplementariesCompat.init();

        modBus.addListener(DataOnly::gatherData);
    }

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MALUM, path);
    }


    public static class DataOnly {
        public static void gatherData(GatherDataEvent event) {
            BlockTagsProvider provider = new MalumBlockTags(event.getGenerator(), event.getExistingFileHelper());
            event.getGenerator().addProvider(new MalumBlockStates(event.getGenerator(), event.getExistingFileHelper()));
            event.getGenerator().addProvider(new MalumItemModels(event.getGenerator(), event.getExistingFileHelper()));
            event.getGenerator().addProvider(new MalumLang(event.getGenerator()));
            event.getGenerator().addProvider(provider);
            event.getGenerator().addProvider(new MalumBlockLootTables(event.getGenerator()));
            event.getGenerator().addProvider(new MalumItemTags(event.getGenerator(), provider, event.getExistingFileHelper()));
            event.getGenerator().addProvider(new MalumRecipes(event.getGenerator()));
            event.getGenerator().addProvider(new MalumSpiritInfusionRecipes(event.getGenerator()));
            event.getGenerator().addProvider(new MalumSpiritFocusingRecipes(event.getGenerator()));
            event.getGenerator().addProvider(new MalumBlockTransmutationRecipes(event.getGenerator()));
        }
    }
}