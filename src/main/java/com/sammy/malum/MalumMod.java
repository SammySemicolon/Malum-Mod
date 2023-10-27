package com.sammy.malum;

import com.sammy.malum.compability.create.*;
import com.sammy.malum.compability.farmersdelight.*;
import com.sammy.malum.compability.tetra.*;
import com.sammy.malum.config.*;
import com.sammy.malum.data.*;
import com.sammy.malum.data.block.*;
import com.sammy.malum.data.item.*;
import com.sammy.malum.data.recipe.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.resources.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.config.*;
import net.minecraftforge.fml.javafmlmod.*;
import org.apache.logging.log4j.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.*;

import static com.sammy.malum.registry.client.ParticleRegistry.*;
import static com.sammy.malum.registry.common.AttributeRegistry.*;
import static com.sammy.malum.registry.common.ContainerRegistry.*;
import static com.sammy.malum.registry.common.MobEffectRegistry.*;
import static com.sammy.malum.registry.common.SoundRegistry.*;
import static com.sammy.malum.registry.common.block.BlockEntityRegistry.*;
import static com.sammy.malum.registry.common.block.BlockRegistry.*;
import static com.sammy.malum.registry.common.entity.EntityRegistry.*;
import static com.sammy.malum.registry.common.item.EnchantmentRegistry.*;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry.*;
import static com.sammy.malum.registry.common.recipe.RecipeTypeRegistry.*;
import static com.sammy.malum.registry.common.worldgen.FeatureRegistry.*;

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

        //TetraCompat.init();
        FarmersDelightCompat.init();
        CreateCompat.init();

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

            BlockTagsProvider blockTagsProvider = new MalumBlockTags(output, provider, helper);
            MalumItemModels itemModelsProvider = new MalumItemModels(output, helper);
            MalumItemTags itemTagsProvider = new MalumItemTags(output, provider, blockTagsProvider.contentsGetter(), helper);
            MalumBlockStates blockStateProvider = new MalumBlockStates(output, helper, itemModelsProvider);
            MalumLang langProvider = new MalumLang(generator);
            MalumBlockLootTables lootTablesProvider = new MalumBlockLootTables(output);
            MalumRecipes recipeProvider = new MalumRecipes(output);
            MalumVanillaRecipeReplacements vanillaRecipeReplacementsProvider = new MalumVanillaRecipeReplacements(output);
            MalumSpiritInfusionRecipes spiritInfusionRecipesProvider = new MalumSpiritInfusionRecipes(output);
            MalumSpiritFocusingRecipes spiritFocusingRecipesProvider = new MalumSpiritFocusingRecipes(output);
            MalumSpiritTransmutationRecipes spiritTransmutationRecipesProvider =new MalumSpiritTransmutationRecipes(output);
            MalumVoidFavorRecipes voidFavorRecipesProvider = new MalumVoidFavorRecipes(output);



            generator.addProvider(event.includeClient(), blockStateProvider);
            generator.addProvider(event.includeClient(), itemModelsProvider);
            generator.addProvider(event.includeClient(), langProvider);

            generator.addProvider(event.includeServer(), blockTagsProvider);
            generator.addProvider(event.includeServer(), lootTablesProvider);
            generator.addProvider(event.includeServer(), itemTagsProvider);


            generator.addProvider(event.includeServer(), recipeProvider);
            generator.addProvider(event.includeServer(), vanillaRecipeReplacementsProvider);
            generator.addProvider(event.includeServer(), spiritInfusionRecipesProvider);
            generator.addProvider(event.includeServer(), spiritFocusingRecipesProvider);
            generator.addProvider(event.includeServer(), spiritTransmutationRecipesProvider);
            generator.addProvider(event.includeServer(), voidFavorRecipesProvider);
        }
    }
}