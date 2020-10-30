package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.sammy.malum.blocks.utility.multiblock.BoundingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritSmelteryBoundingBlock extends BoundingBlock
{
    public static final IntegerProperty x = IntegerProperty.create("x", -1, 1);
    public static final IntegerProperty y = IntegerProperty.create("y", 0, 2);
    public static final IntegerProperty z = IntegerProperty.create("z", -1, 1);
    
    public SpiritSmelteryBoundingBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(x, 0).with(y,0).with(z,0));
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(x);
        blockStateBuilder.add(y);
        blockStateBuilder.add(z);
    }
    
    @Override
    public BlockState state(BlockPos placePos, World world, PlayerEntity player, ItemStack stack, BlockState state, BlockPos pos)
    {
        return super.state(placePos, world, player, stack, state, pos).with(x,pos.getX()).with(y,pos.getY()).with(z,pos.getZ());
    }
    
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState().with(x, 0).with(y, 0).with(z, 0);
    }
}