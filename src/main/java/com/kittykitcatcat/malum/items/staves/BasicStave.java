package com.kittykitcatcat.malum.items.staves;

import com.kittykitcatcat.malum.SpiritConsumer;
import com.kittykitcatcat.malum.SpiritDescription;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.items.staves.effects.ModEffect;
import com.kittykitcatcat.malum.items.staves.effects.ModEffect.effectTypeEnum;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.MalumHelper.getClosestEntity;
import static com.kittykitcatcat.malum.SpiritDataHelper.getHusk;
import static com.kittykitcatcat.malum.items.staves.effects.ModEffect.effectTypeEnum.*;
import static net.minecraft.util.SoundCategory.PLAYERS;

public abstract class BasicStave extends Item implements SpiritConsumer, SpiritDescription
{
    @Override
    public int durability()
    {
        if (effect != null)
        {
            return effect.durability();
        }
        return 0;
    }

    @Override
    public String spirit()
    {
       if (effect != null)
       {
           return effect.spirit();
       }
       return null;
    }

    @Override
    public ArrayList<ITextComponent> components()
    {
        if (effect != null)
        {
            return effect.components();
        }
        return null;
    }

    ModEffect effect;
    public BasicStave(Properties builder, ModEffect effect)
    {
        super(builder);
        this.effect = effect;
    }

    //region HARVESTING

    public static boolean isEntityValid(PlayerEntity playerEntity, LivingEntity livingEntity)
    {
        if (livingEntity.getHealth() > playerEntity.getHealth() && !playerEntity.isCreative())
        {
            return false;
        }
        return !getHusk(livingEntity);
    }
    public static LivingEntity findEntity(PlayerEntity player)
    {
        World world = player.world;
        Vec3d pos = player.getPositionVec();
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(pos.x - 10, pos.y - 10, pos.z - 10, pos.x + 10, pos.y + 10, pos.z + 10));
        List<Entity> finalList = new ArrayList<>();
        if (!list.isEmpty())
        {
            for (Entity entity : list)
            {
                if (entity instanceof LivingEntity)
                {
                    if (isEntityValid(player, (LivingEntity) entity))
                    {
                        Vec3d vecA = player.getLookVec().normalize();
                        Vec3d vecB = (entity.getPositionVec().subtract(player.getPositionVec())).normalize();
                        double angle = 2.0d * Math.atan((vecA.subtract(vecB)).length() / (vecA.add(vecB)).length());
                        if (angle <= 0.6f && angle >= -0.6f)
                        {
                            finalList.add(entity);
                        }
                    }
                }
            }
        }
        if (!finalList.isEmpty())
        {
            return (LivingEntity) getClosestEntity(finalList, player.getPositionVec());
        }
        return null;
    }
    //endregion
    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IWorldReader world, BlockPos pos, PlayerEntity player)
    {
        return true;
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    public int getUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        PlayerEntity playerEntity = context.getPlayer();
        if (playerEntity.isSneaking() && effect != null)
        {
            if (effect.type() == blockInteraction)
            {
                BlockState state = context.getWorld().getBlockState(context.getPos());
                ItemStack stack = context.getItem();
                playerEntity.swingArm(context.getHand());
                effect.effect(playerEntity, context.getItem(), state);
                playerEntity.addStat(Stats.ITEM_USED.get(stack.getItem()));
                playerEntity.getCooldownTracker().setCooldown(stack.getItem(), effect.cooldown());
                return ActionResultType.SUCCESS;
            }
        }
        return super.onItemUse(context);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.isSneaking())
        {
            playerIn.world.playSound(null, playerIn.getPosition(),ModSounds.spirit_harvest_drain, PLAYERS, 1,1);
            playerIn.setActiveHand(handIn);
        }
        else if (effect != null)
        {
            if (effect.type() == rightClick)
            {
                playerIn.swingArm(handIn);
                effect.effect(playerIn, itemstack);
                playerIn.addStat(Stats.ITEM_USED.get(playerIn.getHeldItemMainhand().getItem()));
                playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItemMainhand().getItem(), effect.cooldown());
                return ActionResult.resultSuccess(itemstack);
            }
        }
        return ActionResult.resultSuccess(itemstack);
    }
}