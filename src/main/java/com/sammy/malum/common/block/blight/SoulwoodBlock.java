package com.sammy.malum.common.block.blight;

import com.sammy.malum.core.setup.content.SoundRegistry;
import team.lodestar.lodestone.systems.block.LodestoneLogBlock;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SoulwoodBlock extends LodestoneLogBlock {
    public SoulwoodBlock(Properties properties, Supplier<Block> stripped) {
        super(properties, stripped);
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
}
