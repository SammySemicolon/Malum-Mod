package com.kittykitcatcat.malum.events;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.SpiritDataHelper;
import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
import com.kittykitcatcat.malum.items.BowofLostSouls;
import com.kittykitcatcat.malum.items.curios.CurioEnchantedLectern;
import com.kittykitcatcat.malum.items.curios.CurioVacantAegis;
import com.kittykitcatcat.malum.network.packets.HuskChangePacket;
import com.kittykitcatcat.malum.particles.tinyskull.TinySkullParticleData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosAPI;

import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber()
public class RuntimeEvents
{
    //region husk
    @SubscribeEvent
    public static void syncHuskData(PlayerEvent.StartTracking event)
    {
        if (event.getTarget() instanceof LivingEntity)
        {
            if (event.getEntityLiving().world instanceof ServerWorld)
            {
                INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getEntityLiving()),
                        new HuskChangePacket(event.getTarget().getEntityId(), CapabilityValueGetter.getHusk((LivingEntity) event.getTarget())));
            }
        }
    }

    @SubscribeEvent
    public static void phantomTargetEvent(LivingSetAttackTargetEvent event)
    {
        if (event.getTarget() instanceof PlayerEntity)
        {
            if (event.getEntityLiving() instanceof MobEntity)
            {
                if (CapabilityValueGetter.getHusk(event.getEntityLiving()))
                {
                    ((MobEntity) event.getEntityLiving()).setAttackTarget(null);
                }
            }
        }
    }

    @SubscribeEvent
    public static void handleHuskLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() != null)
        {
            LivingEntity livingEntity = event.getEntityLiving();
            World world = livingEntity.getEntityWorld();
            if (CapabilityValueGetter.getHusk(livingEntity))
            {
                Vec3d pos = MalumHelper.randExtendedPosofEntity(livingEntity, world.rand);
                if (world.rand.nextInt(4) == 0)
                {
                    world.addParticle(new TinySkullParticleData(), pos.x, pos.y, pos.z, 0, 0.04, 0);
                }
            }
        }
    }
    //endregion
    //region curios

    @SubscribeEvent
    public static void vacantAegisEffect(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioVacantAegis, playerEntity).isPresent())
            {
                if (event.getSource().getTrueSource() instanceof LivingEntity)
                {
                    if (CapabilityValueGetter.getHusk((LivingEntity) event.getSource().getTrueSource()))
                    {
                        event.setAmount(event.getAmount() * 0.7f);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void enchantedLecternEffect(LivingExperienceDropEvent event)
    {
        if (event.getAttackingPlayer() != null)
        {
            PlayerEntity playerEntity = event.getAttackingPlayer();
            if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioEnchantedLectern, playerEntity).isPresent())
            {
                if (CapabilityValueGetter.getHusk(event.getEntityLiving()))
                {
                    CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioEnchantedLectern, playerEntity).ifPresent(triple ->
                    {
                        boolean success = SpiritDataHelper.consumeSpirit(playerEntity, triple.right);
                        if (success)
                        {
                            playerEntity.giveExperiencePoints(event.getDroppedExperience() * 2);
                        }
                    });
                    playerEntity.giveExperiencePoints(event.getDroppedExperience() * 2);
                    event.setCanceled(true);
                }
            }
        }
    }
    //endregion
    //region items
    @SubscribeEvent
    public static void autofireBowofLostSouls(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = playerEntity.getActiveItemStack();
            if (stack.getItem() instanceof BowofLostSouls)
            {
                if ((stack.getUseDuration() - playerEntity.getItemInUseCount()) / BowofLostSouls.getDrawTime(stack) >= 1.1f)
                {
                    boolean success = SpiritDataHelper.consumeSpirit(playerEntity, stack);
                    if (success)
                    {
                        stack.onPlayerStoppedUsing(playerEntity.world, playerEntity, stack.getUseDuration() - playerEntity.getItemInUseCount());
                        playerEntity.resetActiveHand();
                    }
                }
            }
        }
    }
    //endregion
}