package com.sammy.malum.common.events;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumDamageSources;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.init.worldgen.MalumFeatures;
import com.sammy.malum.core.init.worldgen.MalumStaticFeatures;
import com.sammy.malum.core.systems.souls.SpiritHelper;
import com.sammy.malum.network.packets.TyrvingParticlePacket;
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

import static com.sammy.malum.network.NetworkManager.INSTANCE;

@Mod.EventBusSubscriber
public class Events
{

    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event)
    {
        if (event.getCategory().equals(Biome.Category.PLAINS))
        {
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> MalumFeatures.CONFIGURED_RUNEWOOD_TREE);
        }
        if (event.getCategory().equals(Biome.Category.FOREST))
        {
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> MalumFeatures.RARE_CONFIGURED_RUNEWOOD_TREE);
        }
        if (event.getCategory().equals(Biome.Category.NETHER))
        {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MalumStaticFeatures.BLAZE_QUARTZ_ORE);
        }
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MalumStaticFeatures.RUNESTONE_ORE);
    }
    @SubscribeEvent
    public static void giveCattoHisTreat(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntity();
            if (MalumHelper.areWeOnServer(playerEntity.world))
            {
                if (playerEntity.getUniqueID().equals(UUID.fromString("0ca54301-6170-4c44-b3e0-b8afa6b81ed2")))
                {
                    if (!MalumHelper.findCosmeticCurio(s -> s.getItem().equals(MalumItems.FLUFFY_TAIL.get()), playerEntity).isPresent())
                    {
                        ItemHandlerHelper.giveItemToPlayer(playerEntity, MalumItems.FLUFFY_TAIL.get().getDefaultInstance());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {
        if (event.getSource().equals(MalumDamageSources.VOODOO))
        {
            return;
        }
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
            if (playerEntity.swingingHand != null)
            {
                ItemStack stack = playerEntity.getHeldItem(playerEntity.swingingHand);
                if (stack.getItem().equals(MalumItems.TYRVING.get()))
                {
                    LivingEntity entity = event.getEntityLiving();
                    event.getEntity().hurtResistantTime = 0;
                    float amount = event.getAmount() * (1 + entity.getTotalArmorValue()/10);
                    SpiritHelper.causeVoodooDamage(playerEntity, entity, amount);
                    event.setAmount(0);
                    playerEntity.world.playSound(null, entity.getPosition(), MalumSounds.TYRVING_CRUSH, SoundCategory.PLAYERS, 1, 1f + playerEntity.world.rand.nextFloat() * 0.25f);
                    if (playerEntity.world instanceof ServerWorld)
                    {
                        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new TyrvingParticlePacket(entity.getPosX(), entity.getPosY() + entity.getHeight() / 2, entity.getPosZ()));
                    }
                }
            }
        }
    }
}