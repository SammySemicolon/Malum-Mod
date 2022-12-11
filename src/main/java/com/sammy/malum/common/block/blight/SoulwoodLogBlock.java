package com.sammy.malum.common.block.blight;

import com.sammy.malum.common.block.MalumLogBLock;
import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SoulwoodLogBlock extends MalumLogBLock {
    public SoulwoodLogBlock(Properties properties, Supplier<Block> stripped, boolean isCorrupt) {
        super(properties, stripped, isCorrupt);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (toolAction.equals(ToolActions.AXE_STRIP)) {
            if (!simulate) {
                context.getLevel().playSound(null, context.getClickedPos(), SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
            }
            return stripped.get().defaultBlockState();
        }
        return null;
    }

    @Override
    public boolean createTotemPole(Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, ItemStack stack, MalumSpiritItem spirit) {
        boolean success = super.createTotemPole(level, pos, player, handIn, hit, stack, spirit);
        if (success) {
            level.playSound(null, pos, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
        }
        return success;
    }
}