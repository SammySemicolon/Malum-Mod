package com.sammy.malum.common.block.misc;

import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.Level.Level;
import net.minecraftforge.common.ToolType;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Properties;

public class MalumLogBlock extends RotatedPillarBlock
{
    public final Supplier<Block> stripped;
    public MalumLogBlock(Properties properties, Supplier<Block> stripped)
    {
        super(properties);
        this.stripped = stripped;
    }
    
    @Override
    public BlockState getToolModifiedState(BlockState state, Level Level, BlockPos pos, Player player, ItemStack stack, ToolType toolType)
    {
        return stripped.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
    }
}
