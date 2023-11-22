package com.sammy.malum;

import com.sammy.malum.compability.create.*;
import com.sammy.malum.compability.farmersdelight.*;
import com.sammy.malum.compability.tetra.*;
import com.sammy.malum.config.*;
import com.sammy.malum.data.*;
import com.sammy.malum.data.block.*;
import com.sammy.malum.data.item.*;
import com.sammy.malum.data.recipe.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.config.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.forge.event.lifecycle.*;
import org.apache.logging.log4j.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.registry.client.ParticleRegistry.*;
import static com.sammy.malum.registry.common.AttributeRegistry.*;
import static com.sammy.malum.registry.common.ContainerRegistry.*;
import static com.sammy.malum.registry.common.MobEffectRegistry.*;
import static com.sammy.malum.registry.common.SoundRegistry.*;
import static com.sammy.malum.registry.common.SpiritRiteRegistry.RITES;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.SPIRITS;
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
        RECIPE_TYPES.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
        FEATURE_TYPES.register(modBus);
        SPIRITS.register(modBus);
        RITES.register(modBus);

        TetraCompat.init();
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
            BlockTagsProvider provider = new MalumBlockTags(generator, event.getExistingFileHelper());
            MalumItemModels itemProvider = new MalumItemModels(generator, event.getExistingFileHelper());
            MalumBlockStates blockStateProvider = new MalumBlockStates(generator, event.getExistingFileHelper(), itemProvider);
            generator.addProvider(blockStateProvider);
            generator.addProvider(itemProvider);
            generator.addProvider(new MalumLang(generator));
            generator.addProvider(provider);
            generator.addProvider(new MalumBlockLootTables(generator));
            generator.addProvider(new MalumItemTags(generator, provider, event.getExistingFileHelper()));
            generator.addProvider(new MalumRecipes(generator));
            generator.addProvider(new MalumVanillaRecipeReplacements(generator));
            generator.addProvider(new MalumSpiritInfusionRecipes(generator));
            generator.addProvider(new MalumSpiritFocusingRecipes(generator));
            generator.addProvider(new MalumSpiritTransmutationRecipes(generator));
            generator.addProvider(new MalumVoidFavorRecipes(generator));
        }
    }
}