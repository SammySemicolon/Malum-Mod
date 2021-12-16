package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.Level.Level;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class EldritchEarthenRiteType extends MalumRiteType {
    public EldritchEarthenRiteType() {
        super("eldritch_earthen_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public void riteEffect(Level Level, BlockPos pos) {
        BlockState filter = Level.getBlockState(pos.below());
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, Level, pos, false);
        positions.removeIf(p ->{
            if (p.getX() == pos.getX() && p.getZ() == pos.getZ())
            {
                return true;
            }
            BlockState state = Level.getBlockState(p);
            if (state.isAir(Level, p))
            {
                return true;
            }
            return !filter.isAir(Level, pos) && !filter.is(state.getBlock());
        });
        positions.forEach(p -> {
            if (MalumHelper.areWeOnServer(Level)) {
                Level.destroyBlock(p, true);
            } else {
                particles(Level, p);
            }
        });
    }

    @Override
    public void corruptedRiteEffect(Level Level, BlockPos pos) {
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, Level, pos, false);
        positions.removeIf(p -> p.getX() == pos.getX() && p.getZ() == pos.getZ() || !Level.getBlockState(p).isAir(Level, p));
        positions.forEach(p -> {
            BlockState cobblestone = Blocks.COBBLESTONE.defaultBlockState();
            if (MalumHelper.areWeOnServer(Level)) {
                Level.setBlockAndUpdate(p, cobblestone);
                Level.levelEvent(2001, p, Block.getId(cobblestone));
            } else {
                particles(Level, p);
            }
        });
    }

    @Override
    public int interval(boolean corrupted) {
        return defaultInterval() * 5;
    }

    @Override
    public int range(boolean corrupted) {
        return defaultRange() / 2;
    }

    public void particles(Level Level, BlockPos pos) {
        Color color = EARTHEN_SPIRIT_COLOR;
        ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.2f, 0f)
                .setLifetime(20)
                .setSpin(0.2f)
                .setScale(0.4f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(Level, pos, 4, Direction.UP, Direction.DOWN);
        ParticleManager.create(ParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(40)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color, color)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(Level, pos, 6, Direction.UP, Direction.DOWN);
    }
}