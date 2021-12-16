package com.sammy.malum.common.item.misc;

import com.sammy.malum.common.entity.MalumBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AABB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.Level.Level;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.item.Item.Properties;

public class MalumBoatItem extends Item
{
    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);
    private final RegistryObject<EntityType<MalumBoatEntity>> boat;
    public MalumBoatItem(Properties properties, RegistryObject<EntityType<MalumBoatEntity>> boat)
    {
        super(properties);
        this.boat = boat;
    }

    public ActionResult<ItemStack> use(Level LevelIn, Player playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(LevelIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS)
        {
            return ActionResult.pass(itemstack);
        }
        else
        {
            Vector3d vector3d = playerIn.getViewVector(1.0F);
            List<Entity> list = LevelIn.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty())
            {
                Vector3d vector3d1 = playerIn.getEyePosition(1.0F);

                for (Entity entity : list)
                {
                    AABB AABB = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (AABB.contains(vector3d1))
                    {
                        return ActionResult.pass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK)
            {
                BoatEntity boatEntity = boat.get().create(LevelIn);
                boatEntity.setPos(raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z);
                boatEntity.yRot = playerIn.yRot;
                if (!LevelIn.noCollision(boatEntity, boatEntity.getBoundingBox().inflate(-0.1D)))
                {
                    return ActionResult.fail(itemstack);
                }
                else
                {
                    if (!LevelIn.isClientSide)
                    {
                        LevelIn.addFreshEntity(boatEntity);
                        if (!playerIn.abilities.instabuild)
                        {
                            itemstack.shrink(1);
                        }
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.sidedSuccess(itemstack, LevelIn.isClientSide());
                }
            }
            else
            {
                return ActionResult.pass(itemstack);
            }
        }
    }
}