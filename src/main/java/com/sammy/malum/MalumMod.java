package com.sammy.malum;

import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.registry.common.item.tabs.CreativeTabRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        modBus.addListener(CreativeTabRegistry::populateItemGroups);
    }

    public static ResourceLocation malumPath(String path) {
        return new ResourceLocation(MALUM, path);
    }


}