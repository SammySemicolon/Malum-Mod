package com.sammy.malum.common.block;

import com.sammy.malum.common.blockentity.totem.TotemPoleTileEntity;
import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.ortus.block.OrtusLogBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class RunewoodLogBlock extends OrtusLogBlock {
    private final boolean isCorrupt;

    public RunewoodLogBlock(Properties properties, Supplier<Block> stripped, boolean isCorrupt) {
        super(properties, stripped);
        this.isCorrupt = isCorrupt;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(handIn);
        if (level.isClientSide) {
            if (stack.getItem() instanceof MalumSpiritItem) {
                return InteractionResult.SUCCESS;
            }
        }
        if (stack.getItem() instanceof MalumSpiritItem item) {
            level.setBlockAndUpdate(pos, item.type.getBlockState(isCorrupt, hit));
            if (level.getBlockEntity(pos) instanceof TotemPoleTileEntity blockEntity) {
                blockEntity.create(item.type);
            }
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            player.swing(handIn, true);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, handIn, hit);
    }
}