package com.kittykitcatcat.malum.events;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
import com.kittykitcatcat.malum.items.BowofLostSouls;
import com.kittykitcatcat.malum.items.curios.*;
import com.kittykitcatcat.malum.items.staves.BasicStave;
import com.kittykitcatcat.malum.network.packets.HuskChangePacket;
import com.kittykitcatcat.malum.particles.skull.SkullParticleData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosAPI;

import static com.kittykitcatcat.malum.MalumMod.random;
import static com.kittykitcatcat.malum.SpiritDataHelper.consumeSpirit;
import static com.kittykitcatcat.malum.SpiritDataHelper.getHusk;
import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber()
public class RuntimeEvents
{
    //region HUSKS
    @SubscribeEvent
    public static void syncHuskData(PlayerEvent.StartTracking event)
    {
        if (event.getTarget() instanceof LivingEntity)
        {
            if (event.getEntityLiving().world instanceof ServerWorld)
            {
                INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getEntityLiving()),
                        new HuskChangePacket(event.getTarget().getEntityId(), getHusk((LivingEntity) event.getTarget())));
            }
        }
    }

    @SubscribeEvent
    public static void cancelHuskTargetEvent(LivingSetAttackTargetEvent event)
    {
        if (event.getEntityLiving() instanceof MobEntity)
        {
            if (getHusk(event.getEntityLiving()))
            {
                if (CapabilityValueGetter.getRogue(event.getEntityLiving()))
                {
                    if (!CapabilityValueGetter.getRogue(event.getTarget()))
                    {
                        ((MobEntity) event.getEntityLiving()).setAttackTarget(null);
                    }
                    if (event.getTarget() instanceof PlayerEntity)
                    {
                        PlayerEntity playerEntity = (PlayerEntity) event.getTarget();
                        if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioJesterHat, playerEntity).isPresent())
                        {
                            ((MobEntity) event.getEntityLiving()).setAttackTarget(null);
                        }
                    }
                }
                else
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
            if (getHusk(livingEntity))
            {
                Vec3d pos = MalumHelper.randExtendedPosofEntity(livingEntity, random, 0.5f);
                if (random.nextInt(4) == 0)
                {
                    world.addParticle(new SkullParticleData(0.25f), pos.x, pos.y, pos.z, 0, 0.04, 0);
                }
            }
        }
    }
    //endregion

    //region CURIOS
    @SubscribeEvent
    public static void necroticCatalystEffect(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioNecroticCatalyst, playerEntity).isPresent())
            {
                if (random.nextDouble() < 0.2f);
                {
                    if (playerEntity.getFoodStats().needFood())
                    {
                        CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioNecroticCatalyst, playerEntity).ifPresent(triple -> {
                            boolean success = consumeSpirit(playerEntity, triple.right);
                            if (success)
                            {
                                playerEntity.getFoodStats().addStats(1, 1);
                            }
                        });
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void vampireNecklaceEffect(LivingHurtEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
            if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioVampireNecklace, playerEntity).isPresent())
            {
                if (random.nextDouble() < 0.2f || event.getEntityLiving().getHealth() <= 0)
                {
                    CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioVampireNecklace, playerEntity).ifPresent(triple ->
                    {
                        boolean success = consumeSpirit(playerEntity, triple.right);
                        if (success)
                        {
                            playerEntity.heal(2);
                        }
                    });
                }
            }
        }
    }
    @SubscribeEvent
    public static void etherealBulwarkEffect(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioEtherealBulwark, playerEntity).isPresent())
            {
                if (playerEntity.getActiveItemStack().getItem() instanceof BasicStave)
                {
                    event.setCanceled(true);
                }
            }
        }
    }
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
                    if (getHusk((LivingEntity) event.getSource().getTrueSource()))
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
                if (getHusk(event.getEntityLiving()))
                {
                    CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioEnchantedLectern, playerEntity).ifPresent(triple ->
                    {
                        boolean success = consumeSpirit(playerEntity, triple.right);
                        if (success)
                        {
                            event.setDroppedExperience(event.getDroppedExperience()*2);
                        }
                    });
                    event.setDroppedExperience(event.getDroppedExperience()*2);
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void barkNecklaceEffect(SpiritHarvestEvent.Pre event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioSpiritwoodNecklace, playerEntity).isPresent())
        {
            event.extraSpirits +=1;
        }
    }
    
    @SubscribeEvent
    public static void seerOfMiraclesEffect(SpiritHarvestEvent.Pre event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioMiraclePearl, playerEntity).isPresent())
        {
            event.extraSpirits +=1;
        }
    }
    
    @SubscribeEvent
    public static void seerOfMiraclesEffect(SpiritIntegrityUpdateEvent.Fill event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioMiraclePearl, playerEntity).isPresent())
        {
            event.integrityChange *= 1.5f;
        }
    }
    
    @SubscribeEvent
    public static void seerOfMiraclesEffect(SpiritIntegrityUpdateEvent.Decrease event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioMiraclePearl, playerEntity).isPresent())
        {
            if (MathHelper.nextInt(random, 0, 3) == 0)
            {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public static void jesterHatEffect(SpiritHarvestEvent.Post event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof CurioJesterHat, playerEntity).isPresent())
        {
            CapabilityValueGetter.setRogue(event.target, true);
        }
    }
    //endregion

    //region ITEMS
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
                    boolean success = consumeSpirit(playerEntity, stack);
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