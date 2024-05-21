package com.sammy.malum;

import com.mojang.brigadier.builder.*;
import net.minecraft.commands.*;
import net.minecraft.commands.arguments.coordinates.*;
import net.minecraft.core.*;
import net.minecraft.server.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.pattern.*;


public class ActivateEndPortalFrameCommand {
    public ActivateEndPortalFrameCommand() {
    }

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("activateendportalframe")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("pos", BlockPosArgument.blockPos())
                .executes((context) -> {
                    CommandSourceStack source = context.getSource();
                    MinecraftServer server = source.getServer();
                    ServerLevel level = source.getLevel();
                    BlockPos pos = BlockPosArgument.getLoadedBlockPos(context, "pos");
                    BlockState blockstate = level.getBlockState(pos);
                    if (blockstate.getBlock().equals(Blocks.END_PORTAL_FRAME)) {
                        BlockState blockstate1 = blockstate.setValue(EndPortalFrameBlock.HAS_EYE, true);
                        Block.pushEntitiesUp(blockstate, blockstate1, level, pos);
                        level.setBlock(pos, blockstate1, 2);
                        level.updateNeighbourForOutputSignal(pos, Blocks.END_PORTAL_FRAME);
                        level.levelEvent(1503, pos, 0);
                        BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = EndPortalFrameBlock.getOrCreatePortalShape().find(level, pos);
                        if (blockpattern$blockpatternmatch != null) {
                            BlockPos blockpos1 = blockpattern$blockpatternmatch.getFrontTopLeft().offset(-3, 0, -3);
                            for (int i = 0; i < 3; ++i) {
                                for (int j = 0; j < 3; ++j) {
                                    level.setBlock(blockpos1.offset(i, 0, j), Blocks.END_PORTAL.defaultBlockState(), 2);
                                }
                            }

                            level.globalLevelEvent(1038, blockpos1.offset(1, 0, 1), 0);
                        }
                    }
                    return 1;
                }));
    }
}