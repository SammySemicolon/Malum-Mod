package com.sammy.malum.common.blocks;

import com.sammy.malum.MalumHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class MalumLogBlock extends RotatedPillarBlock
{
    public final Block stripped;
    public MalumLogBlock(Properties properties, Block stripped)
    {
        super(properties);
        this.stripped = stripped;
    }
    @Override
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType)
    {
        return stripped.getDefaultState().with(AXIS, state.get(AXIS));
    }
}
