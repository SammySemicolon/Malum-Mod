package com.sammy.malum.events;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.init.ModSounds;
import com.sammy.malum.items.curios.CurioBandOfFriendship;
import com.sammy.malum.items.curios.CurioNetherborneCapacitor;
import com.sammy.malum.items.staves.BasicStave;
import com.sammy.malum.network.packets.SpiritHarvestFailurePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sammy.malum.SpiritDataHelper.*;
import static com.sammy.malum.capabilities.MalumDataProvider.*;
import static com.sammy.malum.network.NetworkManager.INSTANCE;
import static net.minecraft.util.SoundCategory.PLAYERS;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber()
public class HarvestingEvents
{
    public static boolean tryCancel(PlayerEntity playerEntity, ItemStack stack)
    {
        if (playerEntity instanceof ServerPlayerEntity)
        {
            if (getHarvestTarget(playerEntity) == null)
            {
                cancel(playerEntity, stack);
                return true;
            }
            else
            {
                Entity entity = ((ServerPlayerEntity) playerEntity).getServerWorld().getEntityByUuid(getHarvestTarget(playerEntity));
                if (entity instanceof LivingEntity)
                {
                    boolean isAlive = entity.isAlive();
                    if (!isAlive || SpiritDataHelper.lacksSpirit((LivingEntity) entity))
                    {
                        cancel(playerEntity, stack);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static void cancel(PlayerEntity playerEntity, ItemStack stack)
    {
        playerEntity.swingArm(playerEntity.getActiveHand());
        setHarvestTarget(playerEntity, null);
        playerEntity.world.playSound(null, playerEntity.getPosition(), ModSounds.spirit_harvest_failure, PLAYERS, 1f, 1);
        if (playerEntity instanceof ServerPlayerEntity)
        {
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerEntity), new SpiritHarvestFailurePacket());
        }
        playerEntity.getCooldownTracker().setCooldown(stack.getItem(), 10);
        playerEntity.resetActiveHand();
    }
    
    public static void success(PlayerEntity playerEntity, ItemStack stack, LivingEntity cachedTarget)
    {
        playerEntity.world.playSound(null, playerEntity.getPosition(), ModSounds.spirit_harvest_success, PLAYERS, 1f, 1);
        if (CuriosApi.getCuriosHelper().findEquippedCurio(band -> band.getItem() instanceof CurioBandOfFriendship, playerEntity).isPresent())
        {
            setCharm(cachedTarget, true);
        }
        else
        {
            setDread(cachedTarget, true);
        }
        setHarvestTarget(playerEntity, null);
        harvestSpirit(playerEntity, stack, cachedTarget, getSpirit(cachedTarget), 1);
        playerEntity.addStat(Stats.ITEM_USED.get(stack.getItem()));
        playerEntity.getCooldownTracker().setCooldown(stack.getItem(), 40);
        playerEntity.resetActiveHand();
    }
    
    @SubscribeEvent
    public static void updateCachedTarget(PlayerEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.world.getGameTime() % 20L == 0L) //dont do it too often
            {
                if (!player.isHandActive()) //we dont wanna update the target if the player is harvesting a spirit
                {
                    ItemStack stack = player.getHeldItemMainhand();
                    if (!(stack.getItem() instanceof BasicStave))
                    {
                        stack = player.getHeldItemOffhand();
                    }
                    if (stack.getItem() instanceof BasicStave)
                    {
                        if (((BasicStave) stack.getItem()).getOption(stack).option == 0)
                        {
                            LivingEntity entity = findEntityHarvestViable(player, 0.6f);
                            if (entity != null)
                            {
                                setHarvestTarget(player, entity.getUniqueID());
                            }
                        }
                    }
                    else
                    {
                        if (getHarvestTarget(player) != null)
                        {
                            setHarvestTarget(player, null);
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void previewCachedTarget(PlayerEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = player.getHeldItemMainhand();
            if (!(stack.getItem() instanceof BasicStave))
            {
                stack = player.getHeldItemOffhand();
            }
            if (stack.getItem() instanceof BasicStave)
            {
                if (((BasicStave) stack.getItem()).getOption(stack).option == 0)
                {
                    UUID cachedTarget = getHarvestTarget(player);
                    if (cachedTarget != null)
                    {
                        if (player.world instanceof ServerWorld)
                        {
                            Entity entity = ((ServerWorld) player.world).getEntityByUuid(cachedTarget);
                            if (entity instanceof LivingEntity)
                            {
                                ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.GLOWING, 5, 1, true, false));
                            }
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void harvestStart(LivingEntityUseItemEvent.Start event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = event.getItem();
            if (stack.getItem() instanceof BasicStave)
            {
                if (((BasicStave) stack.getItem()).getOption(stack).option == 0)
                {
            
                    if (tryCancel(player, event.getItem()))
                    {
                        player.resetActiveHand();
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void harvestTick(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = event.getItem();
            if (stack.getItem() instanceof BasicStave) {
                if (((BasicStave) stack.getItem()).getOption(stack).option == 0) {
                    if (tryCancel(player, stack)) {
                        event.setCanceled(true);
                    }
                    int time = 100;

                    if (CuriosApi.getCuriosHelper().findEquippedCurio(curio -> curio.getItem() instanceof CurioNetherborneCapacitor, player).isPresent()) {
                        time = 50;
                    }
                    if ((stack.getUseDuration() - player.getItemInUseCount()) > time) {
                        if (!tryCancel(player, stack)) {
                            UUID cachedTarget = getHarvestTarget(player);
                            if (cachedTarget != null) {
                                if (player.world instanceof ServerWorld) {
                                    Entity entity = ((ServerWorld) player.world).getEntityByUuid(cachedTarget);
                                    if (entity instanceof LivingEntity) {
                                        success(player, stack, (LivingEntity) entity);
                                        event.setCanceled(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void stopHarvest(LivingEntityUseItemEvent.Stop event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (event.getItem().getItem() instanceof BasicStave)
            {
                cancel(player, player.getActiveItemStack());
            }
        }
    }
    
    public static boolean isEntityValid(PlayerEntity playerEntity, LivingEntity livingEntity)
    {
        if (livingEntity.getHealth() > playerEntity.getHealth() && !playerEntity.isCreative())
        {
            if (!CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioNetherborneCapacitor, playerEntity).isPresent())
            {
                return false;
            }
        }
        return !lacksSpirit(livingEntity);
    }
    
    public static LivingEntity findEntityHarvestViable(PlayerEntity player, float maxAngle)
    {
        World world = player.world;
        Vector3d pos = player.getPositionVec();
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(pos.x - 10, pos.y - 10, pos.z - 10, pos.x + 10, pos.y + 10, pos.z + 10)).stream().filter(e -> e instanceof LivingEntity && isEntityValid(player, (LivingEntity) e)).collect(Collectors.toList());
        List<Entity> finalList = new ArrayList<>();
        if (!list.isEmpty())
        {
            for (Entity entity : list)
            {
                Vector3d vecA = player.getLookVec().normalize();
                Vector3d vecB = (entity.getPositionVec().subtract(player.getPositionVec())).normalize();
                double angle = 2 * Math.atan((vecA.subtract(vecB)).length() / (vecA.add(vecB)).length());
                if (angle <= maxAngle && angle >= -maxAngle)
                {
                    finalList.add(entity);
                }
            }
        }
        if (!finalList.isEmpty())
        {
            return (LivingEntity) MalumHelper.getClosestEntity(finalList, player.getPositionVec());
        }
        return null;
    }
}