package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;

public class EldritchEarthenRiteType extends MalumRiteType {
    public EldritchEarthenRiteType() {
        super("eldritch_earthen_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public int interval(boolean corrupted) {
        return corrupted ? defaultInterval() : defaultInterval() * 3;
    }

    @Override
    public int range(boolean corrupted) {
        return defaultRange() / 4;
    }

    @Override
    public void riteEffect(Level level, BlockPos pos, int height) {
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
            BlockState state = level.getBlockState(p);
            boolean canBreak = state.is(BlockTags.NEEDS_STONE_TOOL) || state.is(BlockTags.NEEDS_IRON_TOOL) || state.is(BlockTags.NEEDS_DIAMOND_TOOL);
            if (canBreak) {
                if (!level.isClientSide) {
                    level.destroyBlock(p, true);
                } else {
                    particles(level, p);
                }
            }
        });
    }

    @Override
    public void corruptedRiteEffect(Level level, BlockPos pos, int height) {
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
        Color color = EARTHEN_SPIRIT.getColor();
        ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.2f, 0f)
                .setLifetime(20)
                .setSpin(0.2f)
                .setScale(0.4f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomMotion(0.001f, 0.001f)
                .evenlyRepeatEdges(level, pos, 4, Direction.UP, Direction.DOWN);
        ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(40)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color, color)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomMotion(0.001f, 0.001f)
                .evenlyRepeatEdges(level, pos, 6, Direction.UP, Direction.DOWN);
    }
}