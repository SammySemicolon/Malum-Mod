package com.sammy.malum.common.items.tools.elementaltools;

import com.sammy.malum.common.items.tools.ModShovelItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
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

public class ShovelOfTremors extends ModShovelItem
{
    public ShovelOfTremors(IItemTier material, int damage, float speed, Properties properties)
    {
        super(material, damage, speed, properties);
    }
    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        boolean dub = false;
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = world.getBlockState(blockpos);
        if (context.getFace() == Direction.DOWN)
        {
            return ActionResultType.PASS;
        }
        else
        {
            PlayerEntity playerentity = context.getPlayer();
            Vector3i vector3i = context.getPlacementHorizontalFacing().getDirectionVec();
            for (int i = 0; i < 4; i++)
            {
                BlockPos adjustedPos = blockpos.add(vector3i.getX() * i, 0, vector3i.getZ() * i);
                BlockState blockstate1 = blockstate.getToolModifiedState(world, adjustedPos, playerentity, context.getItem(), net.minecraftforge.common.ToolType.SHOVEL);
                BlockState blockstate2 = null;
                if (blockstate1 == null)
                {
                    break;
                }
                if (world.isAirBlock(adjustedPos.up()))
                {
                    world.playSound(playerentity, adjustedPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    blockstate2 = blockstate1;
                }
                else if (blockstate1.getBlock() instanceof CampfireBlock && blockstate1.get(CampfireBlock.LIT))
                {
                    if (!world.isRemote())
                    {
                        world.playEvent(null, 1009, adjustedPos, 0);
                    }
                    
                    CampfireBlock.extinguish(world, adjustedPos, blockstate);
                    blockstate2 = blockstate.with(CampfireBlock.LIT, Boolean.FALSE);
                }
                
                if (blockstate2 != null)
                {
                    if (!world.isRemote)
                    {
                        world.setBlockState(adjustedPos, blockstate2, 11);
                        if (playerentity != null)
                        {
                            context.getItem().damageItem(1, playerentity, (player) -> {
                                player.sendBreakAnimation(context.getHand());
                            });
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
        return ActionResultType.FAIL;
    }
}
