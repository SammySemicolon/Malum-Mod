package com.sammy.malum.common.events;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.tools.spirittools.TyrvingItem;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.init.worldgen.MalumFeatures;
import com.sammy.malum.network.packets.particle.BurstParticlePacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.UUID;

import static com.sammy.malum.common.items.equipment.curios.CurioTokenOfGratitude.sammy_uuid;
import static com.sammy.malum.core.modcontent.MalumSpiritTypes.*;
import static com.sammy.malum.network.NetworkManager.INSTANCE;

@Mod.EventBusSubscriber
public class Events
{
    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event)
    {
        if (event.getCategory().equals(Biome.Category.PLAINS) || event.getCategory().equals(Biome.Category.EXTREME_HILLS))
        {
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> MalumFeatures.CONFIGURED_RUNEWOOD_TREE);
        }
        if (event.getCategory().equals(Biome.Category.FOREST))
        {
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> MalumFeatures.RARE_CONFIGURED_RUNEWOOD_TREE);
        }
        if (event.getCategory().equals(Biome.Category.NETHER))
        {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MalumFeatures.BLAZE_QUARTZ_ORE);
        }
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MalumFeatures.SOULSTONE_ORE);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MalumFeatures.SOULSTONE_ORE_SURFACE);
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