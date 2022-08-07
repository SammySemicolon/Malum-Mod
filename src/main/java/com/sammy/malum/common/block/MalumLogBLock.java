package com.sammy.malum.common.block;

import com.sammy.malum.common.blockentity.totem.TotemPoleBlockEntity;
import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import team.lodestar.lodestone.systems.block.LodestoneLogBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class MalumLogBLock extends LodestoneLogBlock {
    private final boolean isCorrupt;

    public MalumLogBLock(Properties properties, Supplier<Block> stripped, boolean isCorrupt) {
        super(properties, stripped);
        this.isCorrupt = isCorrupt;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(handIn);
        if (stack.getItem() instanceof MalumSpiritItem item) {
            if (hit.getDirection().equals(Direction.UP) || hit.getDirection().equals(Direction.DOWN)) {
                return InteractionResult.FAIL;
            }
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            boolean success = createTotemPole(level, pos, player, handIn, hit, stack, item);
            if (success) {
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, handIn, hit);
    }

    public boolean createTotemPole(Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, ItemStack stack, MalumSpiritItem spirit) {
        level.setBlockAndUpdate(pos, spirit.type.getBlockState(isCorrupt, hit));
        if (level.getBlockEntity(pos) instanceof TotemPoleBlockEntity blockEntity) {
            blockEntity.create(spirit.type);
        }
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        level.levelEvent(2001, pos, Block.getId(level.getBlockState(pos)));
        player.swing(handIn, true);
        return true;
    }
}