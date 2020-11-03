package com.sammy.malum.blocks.machines.crystallineaccelerator;

import com.sammy.malum.blocks.utility.multiblock.BoundingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class CrystallineAcceleratorBoundingBlock extends BoundingBlock
{
    public CrystallineAcceleratorBoundingBlock(Properties properties)
    {
        super(properties);
    }
}