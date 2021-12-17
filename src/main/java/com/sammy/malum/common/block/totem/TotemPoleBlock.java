package com.sammy.malum.common.block.totem;

import com.sammy.malum.common.tile.TotemPoleTileEntity;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.systems.block.SimpleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.HitResult;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemPoleBlock extends SimpleBlock<TotemPoleTileEntity>
{
    public final Supplier<? extends Block> logBlock;
    public final boolean corrupted;
    public TotemPoleBlock(Properties properties, Supplier<? extends Block> logBlock, boolean corrupted)
    {
        super(properties.lootFrom(logBlock));
        this.logBlock = logBlock;
        this.corrupted = corrupted;
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
        setTile(TileEntityRegistry.TOTEM_POLE_TILE_ENTITY);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return logBlock.get().getCloneItemStack(world, pos, state);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }
}
