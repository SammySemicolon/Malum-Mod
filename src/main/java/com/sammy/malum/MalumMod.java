package com.sammy.malum;

import com.sammy.malum.compability.create.CreateCompat;
import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.data.MalumBiomeTags;
import com.sammy.malum.data.MalumLang;
import com.sammy.malum.data.MalumRecipes;
import com.sammy.malum.data.MalumWorldgenProvider;
import com.sammy.malum.data.block.MalumBlockLootTables;
import com.sammy.malum.data.block.MalumBlockStates;
import com.sammy.malum.data.block.MalumBlockTags;
import com.sammy.malum.data.item.MalumItemModels;
import com.sammy.malum.data.item.MalumItemTags;
import com.sammy.malum.data.recipe.*;
import com.sammy.malum.registry.common.item.tabs.CreativeTabRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

import static com.sammy.malum.registry.client.ParticleRegistry.PARTICLES;
import static com.sammy.malum.registry.common.AttributeRegistry.ATTRIBUTES;
import static com.sammy.malum.registry.common.ContainerRegistry.CONTAINERS;
import static com.sammy.malum.registry.common.MobEffectRegistry.EFFECTS;
import static com.sammy.malum.registry.common.SoundRegistry.SOUNDS;
import static com.sammy.malum.registry.common.block.BlockEntityRegistry.BLOCK_ENTITY_TYPES;
import static com.sammy.malum.registry.common.block.BlockRegistry.BLOCKS;
import static com.sammy.malum.registry.common.entity.EntityRegistry.ENTITY_TYPES;
import static com.sammy.malum.registry.common.item.EnchantmentRegistry.ENCHANTMENTS;
import static com.sammy.malum.registry.common.item.ItemRegistry.ITEMS;
import static com.sammy.malum.registry.common.item.tabs.CreativeTabRegistry.CREATIVE_MODE_TABS;
import static com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry.RECIPE_SERIALIZERS;
import static com.sammy.malum.registry.common.recipe.RecipeTypeRegistry.RECIPE_TYPES;
import static com.sammy.malum.registry.common.worldgen.FeatureRegistry.FEATURE_TYPES;
import static com.sammy.malum.registry.common.worldgen.StructureRegistry.STRUCTURES;

@SuppressWarnings("unused")
@Mod(MalumMod.MALUM)
public class MalumMod {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MALUM = "malum";
    public static final RandomSource RANDOM = RandomSource.create();

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
        RECIPE_TYPES.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
        FEATURE_TYPES.register(modBus);
        STRUCTURES.register(modBus);
        CREATIVE_MODE_TABS.register(modBus);

        //TetraCompat.init();
        FarmersDelightCompat.init();
        CreateCompat.init();

        modBus.addListener(CreativeTabRegistry::populateItemGroups);
        modBus.addListener(DataOnly::gatherData);
    }

    public static ResourceLocation malumPath(String path) {
        return new ResourceLocation(MALUM, path);
    }

    public static class DataOnly {
        public static void gatherData(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
            ExistingFileHelper helper = event.getExistingFileHelper();


            MalumItemModels itemModelsProvider = new MalumItemModels(output, helper);
            MalumBlockTags blockTagsProvider = new MalumBlockTags(output, provider, helper);

            generator.addProvider(event.includeClient(), new MalumBlockStates(output, helper, itemModelsProvider));
            generator.addProvider(event.includeClient(), itemModelsProvider);
            generator.addProvider(event.includeClient(), new MalumLang(output));

            //TODO generator.addProvider(event.includeServer(), blockTagsProvider);
            generator.addProvider(event.includeServer(), new MalumBlockLootTables(output));
            //TODO generator.addProvider(event.includeServer(), new MalumItemTags(output, provider, blockTagsProvider.contentsGetter(), helper));

            generator.addProvider(event.includeServer(), new MalumRecipes(output));
            generator.addProvider(event.includeServer(), new MalumVanillaRecipeReplacements(output));
            generator.addProvider(event.includeServer(), new MalumSpiritInfusionRecipes(output));
            generator.addProvider(event.includeServer(), new MalumSpiritFocusingRecipes(output));
            generator.addProvider(event.includeServer(), new MalumSpiritTransmutationRecipes(output));
            generator.addProvider(event.includeServer(), new MalumVoidFavorRecipes(output));

            generator.addProvider(event.includeServer(), new MalumWorldgenProvider(output, provider));
            generator.addProvider(event.includeServer(), new MalumBiomeTags(output, provider, helper));
        }
    }
}