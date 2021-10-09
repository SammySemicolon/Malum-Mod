package com.sammy.malum.common.event;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.init.worldgen.MalumFeatures;
import com.sammy.malum.core.mod_systems.spirit.SpiritReloadListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.UUID;

import static com.sammy.malum.common.item.equipment.curios.CurioTokenOfGratitude.sammy_uuid;

@Mod.EventBusSubscriber
public class GenericEvents
{
    @SubscribeEvent
    public static void registerListeners(AddReloadListenerEvent event)
    {
        event.addListener(new SpiritReloadListener());
    }
    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event)
    {
        if (event.getCategory().equals(Biome.Category.PLAINS) || event.getCategory().equals(Biome.Category.EXTREME_HILLS)) {
            if (CommonConfig.GENERATE_RUNEWOOD_TREES.get()) {
                event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> MalumFeatures.COMMON_RUNEWOOD_TREE);
            }
        }
        if (event.getCategory().equals(Biome.Category.FOREST)) {
            if (CommonConfig.GENERATE_RUNEWOOD_TREES.get()) {
                event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> MalumFeatures.RARE_CONFIGURED_RUNEWOOD_TREE);
            }
        }
        if (event.getCategory().equals(Biome.Category.NETHER)) {
            if (CommonConfig.GENERATE_BLAZE_QUARTZ.get()) {
                event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MalumFeatures.BLAZE_QUARTZ_ORE);
            }
        }
        if (CommonConfig.GENERATE_SOULSTONE.get()) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MalumFeatures.SOULSTONE_ORE);
        }

        if (CommonConfig.GENERATE_SURFACE_SOULSTONE.get()) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MalumFeatures.SOULSTONE_ORE_SURFACE);
        }
    }
    @SubscribeEvent
    public static void showGratitude(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntity();
            if (MalumHelper.areWeOnServer(playerEntity.world))
            {
                if (playerEntity.getUniqueID().equals(UUID.fromString(sammy_uuid)))
                {
                    if (!MalumHelper.findCosmeticCurio(s -> s.getItem().equals(MalumItems.TOKEN_OF_GRATITUDE.get()), playerEntity).isPresent())
                    {
                        ItemHandlerHelper.giveItemToPlayer(playerEntity, MalumItems.TOKEN_OF_GRATITUDE.get().getDefaultInstance());
                    }
                }
            }
        }
    }
}