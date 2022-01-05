package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.registry.ParticleRegistry;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class EldritchEarthenRiteType extends MalumRiteType {
    public EldritchEarthenRiteType() {
        super("eldritch_earthen_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public int interval(boolean corrupted) {
        return corrupted ? defaultInterval() : defaultInterval() * 5;
    }

    @Override
    public int range(boolean corrupted) {
        return defaultRange() / 4;
    }

    @Override
    public void riteEffect(Level level, BlockPos pos) {
        BlockState filter = level.getBlockState(pos.below());
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, level, pos, false);
        positions.removeIf(p ->{
            if (p.getX() == pos.getX() && p.getZ() == pos.getZ())
            {
                return true;
            }
            BlockState state = level.getBlockState(p);
            if (state.isAir())
            {
                return true;
            }
            return !filter.isAir() && !filter.is(state.getBlock());
        });
        positions.forEach(p -> {
            if (!level.isClientSide) {
                level.destroyBlock(p, true);
            } else {
                particles(level, p);
            }
        });
    }

    @Override
    public void corruptedRiteEffect(Level level, BlockPos pos) {
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, level, pos, false);
        positions.removeIf(p -> p.getX() == pos.getX() && p.getZ() == pos.getZ() || !level.getBlockState(p).isAir());
        positions.forEach(p -> {
            BlockState cobblestone = Blocks.COBBLESTONE.defaultBlockState();
            if (!level.isClientSide) {
                level.setBlockAndUpdate(p, cobblestone);
                level.levelEvent(2001, p, Block.getId(cobblestone));
            } else {
                particles(level, p);
            }
        });
    }

    public void particles(Level level, BlockPos pos) {
        Color color = EARTHEN_SPIRIT_COLOR;
        RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.2f, 0f)
                .setLifetime(20)
                .setSpin(0.2f)
                .setScale(0.4f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(level, pos, 4, Direction.UP, Direction.DOWN);
        RenderUtilities.create(ParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(40)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color, color)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(level, pos, 6, Direction.UP, Direction.DOWN);
    }
}