package com.sammy.malum.core.systems.block;

import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SimpleBlock <T extends BlockEntity> extends Block implements EntityBlock {
    protected Supplier<BlockEntityType<T>> blockEntityType = null;
    protected BlockEntityTicker<T> ticker = null;
    public SimpleBlock(Properties properties) {
        super(properties);
    }

    public SimpleBlock<T> setTile(Supplier<BlockEntityType<T>> type) {
        this.blockEntityType = type;
        this.ticker = (l, p, s, t) -> ((SimpleBlockEntity)t).tick();
        return this;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return hasTileEntity(state) ? blockEntityType.get().create(pos, state) : null;
    }

    public boolean hasTileEntity(BlockState state) {
        return this.blockEntityType != null;
    }

    @SuppressWarnings("all")
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (BlockEntityTicker<T>) ticker;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (hasTileEntity(pState)) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SimpleBlockEntity simpleBlockEntity) {
                simpleBlockEntity.onPlace(pPlacer, pStack);
            }
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        if (hasTileEntity(state)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SimpleBlockEntity simpleBlockEntity) {
                ItemStack stack = simpleBlockEntity.onClone(state, target, world, pos, player);
                if (!stack.isEmpty())
                {
                    return stack;
                }
            }
        }
        return super.getCloneItemStack(state, target, world, pos, player);
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
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SimpleBlockEntity simpleBlockEntity) {
                simpleBlockEntity.onBreak();
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (hasTileEntity(state)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SimpleBlockEntity simpleBlockEntity) {
                return simpleBlockEntity.onUse(player, hand);
            }
        }
        return super.use(state, level, pos, player, hand, ray);
    }
}