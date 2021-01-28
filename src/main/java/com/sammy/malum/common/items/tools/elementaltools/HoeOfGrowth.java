package com.sammy.malum.common.items.tools.elementaltools;

import com.sammy.malum.common.items.tools.ModHoeItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

public class HoeOfGrowth extends ModHoeItem
{
    public HoeOfGrowth(IItemTier material, int damage, float speed, Properties properties)
    {
        super(material, damage, speed, properties);
    }
    
    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        boolean dub = false;
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
        if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
        if (context.getFace() != Direction.DOWN)
        {
            Vector3i vector3i = context.getPlacementHorizontalFacing().getDirectionVec();
            for (int i = 0; i < 6; i++)
            {
                BlockPos adjustedPos = blockpos.add(vector3i.getX() * i, 0, vector3i.getZ() * i);
                BlockState blockstate = world.getBlockState(adjustedPos).getToolModifiedState(world, adjustedPos, context.getPlayer(), context.getItem(), net.minecraftforge.common.ToolType.HOE);
                if (blockstate != null && world.isAirBlock(adjustedPos.up()))
                {
                    PlayerEntity playerentity = context.getPlayer();
                    world.playSound(playerentity, adjustedPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (!world.isRemote)
                    {
                        world.setBlockState(adjustedPos, blockstate, 11);
                        if (playerentity != null)
                        {
                            context.getItem().damageItem(1, playerentity, (player) -> player.sendBreakAnimation(context.getHand()));
                        }
                    }
                    dub = true;
                }
                else
                {
                    break;
                }
            }
        }
        if (dub)
        {
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.PASS;
    }
}