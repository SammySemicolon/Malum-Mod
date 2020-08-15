package com.kittykitcatcat.malum.events;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.items.BowofLostSouls;
import com.kittykitcatcat.malum.items.curios.CurioEnchantedLectern;
import com.kittykitcatcat.malum.items.curios.CurioNecroticCatalyst;
import com.kittykitcatcat.malum.items.curios.CurioVacantAegis;
import com.kittykitcatcat.malum.items.curios.CurioVampireNecklace;
import com.kittykitcatcat.malum.items.staves.BasicStave;
import com.kittykitcatcat.malum.network.packets.HuskChangePacket;
import com.kittykitcatcat.malum.particles.tinyskull.TinySkullParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosAPI;

import static com.kittykitcatcat.malum.SpiritDataHelper.*;
import static com.kittykitcatcat.malum.capabilities.CapabilityValueGetter.getCachedTarget;
import static com.kittykitcatcat.malum.capabilities.CapabilityValueGetter.setCachedTarget;
import static com.kittykitcatcat.malum.items.staves.BasicStave.findEntity;
import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;
import static net.minecraft.util.SoundCategory.PLAYERS;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber()
public class RuntimeEvents
{
    //region HARVESTING

    //region UPDATES AND VISUALS
    @SubscribeEvent
    public static void updateCachedTarget(PlayerEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemOffhand().getItem() instanceof BasicStave || player.getHeldItemMainhand().getItem() instanceof BasicStave)
            {
                if (!player.isSneaking())
                {
                    if (!(player.getActiveItemStack().getItem() instanceof BasicStave))
                    {
                        if (player.world.getGameTime() % 4L == 0L)
                        {
                            setCachedTarget(player, findEntity(player));
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void previewTarget(PlayerEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof BasicStave || player.getHeldItem(Hand.OFF_HAND).getItem() instanceof BasicStave)
            {
                LivingEntity cachedTarget = getCachedTarget(player);
                if (cachedTarget != null && !getHusk(cachedTarget))
                {
                    cachedTarget.addPotionEffect(new EffectInstance(Effects.GLOWING, 5, 1, true, false));
                }
            }
        }
    }
    //endregion

    //region HELPER METHODS
    public static boolean tryCancel(PlayerEntity playerEntity, ItemStack stack)
    {
        if (getCachedTarget(playerEntity) == null || getCachedTarget(playerEntity) != null && getHusk(getCachedTarget(playerEntity)))
        {
            playerEntity.swingArm(playerEntity.getActiveHand());
            playerEntity.world.playSound(null, playerEntity.getPosition(), ModSounds.spirit_harvest_failure, PLAYERS, 1f, 1);
            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().getSoundHandler().stop(new ResourceLocation(MalumMod.MODID, "spirit_harvest_drain"), SoundCategory.PLAYERS));
            playerEntity.getCooldownTracker().setCooldown(stack.getItem(), 10);
            playerEntity.resetActiveHand();
            return true;
        }
        return false;
    }
    public static void end(PlayerEntity playerEntity, ItemStack stack, LivingEntity cachedTarget)
    {
        playerEntity.world.playSound(playerEntity,playerEntity.getPosition(), ModSounds.spirit_harvest_success, PLAYERS,1f,1);
        setHusk(cachedTarget, true);
        harvestSpirit(playerEntity, getSpirit(cachedTarget), 1);
        setCachedTarget(playerEntity, findEntity(playerEntity));
        playerEntity.addStat(Stats.ITEM_USED.get(stack.getItem()));
        playerEntity.getCooldownTracker().setCooldown(stack.getItem(), 40);
    }
    //endregion

    //region LOGIC
    @SubscribeEvent
    public static void cancelHarvestFromStarting(LivingEntityUseItemEvent.Start event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getActiveItemStack().getItem() instanceof BasicStave)
            {
                ItemStack heldItem = player.getActiveItemStack();
                BasicStave stave = (BasicStave) heldItem.getItem();
                LivingEntity cachedTarget = getCachedTarget(player);
                if (tryCancel(player, heldItem))
                {
                    event.setCanceled(true);
                }
            }
        }
    }
    @SubscribeEvent
    public static void completeActiveHarvest(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getActiveItemStack().getItem() instanceof BasicStave)
            {
                ItemStack heldItem = player.getActiveItemStack();
                BasicStave stave = (BasicStave) heldItem.getItem();
                LivingEntity cachedTarget = getCachedTarget(player);
                if (tryCancel(player, heldItem))
                {
                    event.setCanceled(true);
                }
                if ((heldItem.getUseDuration() - player.getItemInUseCount()) > 100)
                {
                    if (!tryCancel(player, heldItem))
                    {
                        end(player, heldItem, cachedTarget);
                    }
                    event.setCanceled(true);
                }
            }
        }
    }
    @SubscribeEvent
    public static void endActiveHarvest(LivingEntityUseItemEvent.Stop event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getActiveItemStack().getItem() instanceof BasicStave)
            {
                player.swingArm(player.getActiveHand());
                player.world.playSound(null,player.getPosition(), ModSounds.spirit_harvest_failure, PLAYERS,1f,1);
                DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().getSoundHandler().stop(new ResourceLocation(MalumMod.MODID, "spirit_harvest_drain"), PLAYERS));
                player.getCooldownTracker().setCooldown(player.getActiveItemStack().getItem(), 10);
            }
        }
    }
    //endregion
    //endregion
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
        if (event.getTarget() instanceof PlayerEntity)
        {
            if (event.getEntityLiving() instanceof MobEntity)
            {
                if (getHusk(event.getEntityLiving()))
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
                Vec3d pos = MalumHelper.randExtendedPosofEntity(livingEntity, world.rand, 0.5f);
                if (world.rand.nextInt(4) == 0)
                {
                    world.addParticle(new TinySkullParticleData(), pos.x, pos.y, pos.z, 0, 0.04, 0);
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
                if (playerEntity.world.rand.nextDouble() < 0.2f);
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
                if (playerEntity.world.rand.nextDouble() < 0.2f || event.getEntityLiving().getHealth() <= 0)
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