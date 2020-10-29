package com.sammy.malum.events;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.items.Gauntlet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sammy.malum.capabilities.MalumDataProvider.getGauntletTarget;
import static com.sammy.malum.capabilities.MalumDataProvider.setGauntletTarget;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber()
public class ZAHANDOEvents
{
    
    @SubscribeEvent
    public static void updateCachedTarget(PlayerEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.world.getGameTime() % 20L == 0L) //dont do it too often
            {
                ItemStack stack = player.getHeldItemMainhand();
                if (!(stack.getItem() instanceof Gauntlet))
                {
                    stack = player.getHeldItemOffhand();
                }
                if (stack.getItem() instanceof Gauntlet)
                {
                    LivingEntity entity = findEntity(player, 0.2f);
                    if (entity != null)
                    {
                        setGauntletTarget(player, entity.getUniqueID());
                    }
                }
                else
                {
                    if (getGauntletTarget(player) != null)
                    {
                        setGauntletTarget(player, null);
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
            if (!(stack.getItem() instanceof Gauntlet))
            {
                stack = player.getHeldItemOffhand();
            }
            if (stack.getItem() instanceof Gauntlet)
            {
                UUID cachedTarget = getGauntletTarget(player);
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
    
    public static LivingEntity findEntity(PlayerEntity player, float maxAngle)
    {
        World world = player.world;
        Vector3d pos = player.getPositionVec();
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(pos.x - 20, pos.y - 20, pos.z - 20, pos.x + 20, pos.y + 20, pos.z + 20)).stream().filter(e -> e instanceof LivingEntity && e.getDistance(player) > 2).collect(Collectors.toList());
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