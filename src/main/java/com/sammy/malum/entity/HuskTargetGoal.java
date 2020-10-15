package com.sammy.malum.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.EnumSet;
import java.util.function.Predicate;

public class HuskTargetGoal<T extends LivingEntity> extends TargetGoal
{
    
    protected final Class<T> targetClass;
    protected LivingEntity nearestTarget;
    /**
     * This filter is applied to the Entity search. Only matching entities will be targeted.
     */
    protected EntityPredicate targetEntitySelector;
    
    public HuskTargetGoal(MobEntity husk, Class<T> huskClass)
    {
        super(husk, false, true);
        targetClass = huskClass;
        setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
        targetEntitySelector = (new EntityPredicate()).setDistance(this.getTargetDistance()).setCustomPredicate(null);
        
    }
    public boolean shouldExecute()
    {
        findNearestTarget();
        return nearestTarget != null;
    }
    
    protected AxisAlignedBB getTargetableArea(double targetDistance)
    {
        return goalOwner.getBoundingBox().grow(targetDistance, 4.0D, targetDistance);
    }
    
    protected void findNearestTarget()
    {
        if (targetClass != PlayerEntity.class && targetClass != ServerPlayerEntity.class)
        {
            nearestTarget = goalOwner.world.func_225318_b(targetClass, targetEntitySelector, goalOwner, goalOwner.getPosX(), goalOwner.getPosYEye(), goalOwner.getPosZ(), getTargetableArea(getTargetDistance()));
        }
        else
        {
            nearestTarget = goalOwner.world.getClosestPlayer(targetEntitySelector, goalOwner, goalOwner.getPosX(), goalOwner.getPosYEye(), goalOwner.getPosZ());
        }
        
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.goalOwner.setAttackTarget(this.nearestTarget);
        super.startExecuting();
    }
}