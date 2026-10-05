package com.sammy.malum.common.block.blight;

import com.sammy.malum.common.block.building.ColumnBlock;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.common.worldgen.blight.BlightFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ColumnarBlightBlock extends ColumnBlock implements BonemealableBlock {

    public ColumnarBlightBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level instanceof ServerLevel serverLevel) {
            if (stack.getItem() instanceof SpiritShardItem) {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                serverLevel.levelEvent(1505, pos, 0);
                performBonemeal(serverLevel, level.random, pos, state);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(AXIS).equals(Direction.Axis.Y);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        BlightFeature.generateBlight(pLevel, pPos, false, 3).place(pLevel);
        int addedHeight = pRandom.nextInt(2, 4);
        BlockPos.MutableBlockPos mutable = pPos.mutable();
        for (int i = 0; i < addedHeight; i++) {
            mutable.move(Direction.UP);
            pLevel.setBlockAndUpdate(mutable, pState.setValue(BOTTOM, true));
        }
    }
}