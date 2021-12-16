package com.sammy.malum.core.systems.block;

import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SimpleBlock extends Block implements EntityBlock {
    protected VoxelShape shape = null;
    protected BlockEntityType<? extends BlockEntity> tileEntityType = null;

    public SimpleBlock(Properties properties) {
        super(properties);
    }

    public SimpleBlock setTile(BlockEntityType<? extends BlockEntity> type) {
        this.tileEntityType = type;
        return this;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return hasTileEntity(state) ? tileEntityType.create(pos, state) : null;
    }

    public boolean hasTileEntity(BlockState state) {
        return this.tileEntityType != null;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        onBlockBroken(state, level, pos);
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        onBlockBroken(state, level, pos);
        super.onBlockExploded(state, level, pos, explosion);
    }

    public void onBlockBroken(BlockState state, BlockGetter level, BlockPos pos) {
        if (hasTileEntity(state)) {
            BlockEntity tileEntity = Level.getBlockEntity(pos);
            if (tileEntity instanceof SimpleBlockEntity) {
                SimpleBlockEntity simpleTileEntity = (SimpleBlockEntity) tileEntity;
                simpleTileEntity.onBreak();
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (hasTileEntity(state)) {
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if (tileEntity instanceof SimpleBlockEntity) {
                SimpleBlockEntity simpleTileEntity = (SimpleBlockEntity) tileEntity;
                return simpleTileEntity.onUse(player, hand);
            }
        }
        return super.use(state, level, pos, player, hand, ray);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getVisualShape(state, Level, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getVisualShape(state, Level, pos, context);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shape != null ? shape : Shapes.block();
    }
}