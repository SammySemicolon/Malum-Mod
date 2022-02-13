package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.setup.ParticleRegistry;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;

public class EldritchAqueousRiteType extends MalumRiteType {
    private static final BlockState OBSIDIAN = Blocks.OBSIDIAN.defaultBlockState();
    private static final BlockState ICE = Blocks.ICE.defaultBlockState();

    public EldritchAqueousRiteType() {
        super("eldritch_aqueous_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT);
    }

    @Override
    public int interval(boolean corrupted) {
        return corrupted ? defaultInterval() * 5 : defaultInterval() * 2;
    }

    @Override
    public int range(boolean corrupted) {

        return defaultRange() / (corrupted ? 4 : 2);
    }

    @Override
    public void riteEffect(Level level, BlockPos pos) {
        ArrayList<BlockPos> positions = getNearbyBlocks(PointedDripstoneBlock.class, level, pos, false);
        positions.removeIf(p -> !PointedDripstoneBlock.isStalactiteStartPos(level.getBlockState(p), level, p));
        positions.forEach(p -> {
            if (level.isClientSide) {
                particles(level, p);
            } else {
                if (level.random.nextFloat() < 0.1f) {
                    level.getBlockState(p).randomTick((ServerLevel) level, p, level.random);
                }
            }
        });
    }

    @Override
    public void corruptedRiteEffect(Level level, BlockPos pos) {
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, level, pos, false);
        positions.removeIf(p -> p.getX() == pos.getX() && p.getZ() == pos.getZ() || level.getBlockState(p).getFluidState().isEmpty());
        positions.forEach(p -> {
            FluidState fluidState = level.getFluidState(p);
            BlockState state = null;
            if (fluidState.is(Fluids.WATER))
            {
                state = ICE;
            }
            else if (fluidState.is(Fluids.LAVA))
            {
                state = OBSIDIAN;
            }
            if (state == null)
            {
                return;
            }
            if (!level.isClientSide) {
                level.setBlockAndUpdate(p, state);
                level.levelEvent(2001, p, Block.getId(state));
            } else {
                particles(level, p);
            }
        });
    }

    public void particles(Level level, BlockPos pos) {
        Color color = AQUEOUS_SPIRIT_COLOR;
        RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.2f, 0f)
                .setLifetime(20)
                .setSpin(0.2f)
                .setScale(0.4f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(level, pos, 6, Direction.UP);
        RenderUtilities.create(ParticleRegistry.TWINKLE_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(40)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color, color)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(level, pos, 8, Direction.UP);
    }
}