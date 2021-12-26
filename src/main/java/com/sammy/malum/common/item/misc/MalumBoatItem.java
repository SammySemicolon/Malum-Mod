package com.sammy.malum.common.item.misc;

import com.sammy.malum.common.entity.MalumBoatEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Predicate;

public class MalumBoatItem extends Item
{
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final RegistryObject<EntityType<MalumBoatEntity>> boat;
    public MalumBoatItem(Properties properties, RegistryObject<EntityType<MalumBoatEntity>> boat)
    {
        super(properties);
        this.boat = boat;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn)
    {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        HitResult raytraceresult = getPlayerPOVHitResult(level, playerIn, ClipContext.Fluid.ANY);
        if (raytraceresult.getType() == HitResult.Type.MISS)
        {
            return InteractionResultHolder.pass(itemstack);
        }
        else
        {
            Vec3 vector3d = playerIn.getViewVector(1.0F);
            List<Entity> list = level.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty())
            {
                Vec3 vector3d1 = playerIn.getEyePosition(1.0F);

                for (Entity entity : list)
                {
                    AABB AABB = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (AABB.contains(vector3d1))
                    {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == HitResult.Type.BLOCK)
            {
                MalumBoatEntity boatEntity = boat.get().create(level);
                boatEntity.setPos(raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z);
                boatEntity.setYRot(playerIn.getYRot());
                if (!level.noCollision(boatEntity, boatEntity.getBoundingBox().inflate(-0.1D)))
                {
                    return InteractionResultHolder.fail(itemstack);
                }
                else
                {
                    if (!level.isClientSide)
                    {
                        level.addFreshEntity(boatEntity);
                        if (!playerIn.getAbilities().instabuild)
                        {
                            itemstack.shrink(1);
                        }
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            }
            else
            {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }
}