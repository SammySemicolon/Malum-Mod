package com.sammy.malum.items.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class ModExcavatorItem extends ToolItem
{
    
    public ModExcavatorItem(float damage, float speed, IItemTier tier, Set<Block> effectiveBlocksIn, Properties properties)
    {
        super(damage + 1, speed - 3f, tier, effectiveBlocksIn, properties.maxDamage(tier.getMaxUses()).addToolType(ToolType.PICKAXE, tier.getHarvestLevel()).addToolType(ToolType.AXE, tier.getHarvestLevel()).addToolType(ToolType.SHOVEL, tier.getHarvestLevel()));
    }
    
    public ActionResultType onItemUse(ItemUseContext context)
    {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = world.getBlockState(blockpos);
        BlockState block = blockstate.getToolModifiedState(world, blockpos, context.getPlayer(), context.getItem(), net.minecraftforge.common.ToolType.AXE);
        if (block != null)
        {
            PlayerEntity playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isRemote)
            {
                world.setBlockState(blockpos, block, 11);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.PASS;
    }
    
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        float efficiency = super.getDestroySpeed(stack, state);
        if (state.hardness >= 50f)
        {
            efficiency *= 100f;
        }
        return efficiency;
    }
}