package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class EldritchEarthenRiteType extends MalumRiteType {
    public EldritchEarthenRiteType() {
        super("eldritch_earthen_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public void riteEffect(World world, BlockPos pos) {
        BlockState filter = world.getBlockState(pos.down());
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, world, pos, false);
        positions.removeIf(p ->{
            if (p.getX() == pos.getX() && p.getZ() == pos.getZ())
            {
                return true;
            }
            BlockState state = world.getBlockState(p);
            if (state.isAir(world, p))
            {
                return true;
            }
            return !filter.isAir(world, pos) && !filter.isIn(state.getBlock());
        });
        positions.forEach(p -> {
            if (MalumHelper.areWeOnServer(world)) {
                world.destroyBlock(p, true);
            } else {
                particles(world, p);
            }
        });
    }

    @Override
    public void corruptedRiteEffect(World world, BlockPos pos) {
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, world, pos, false);
        positions.removeIf(p -> p.getX() == pos.getX() && p.getZ() == pos.getZ() || !world.getBlockState(p).isAir(world, p));
        positions.forEach(p -> {
            BlockState cobblestone = Blocks.COBBLESTONE.getDefaultState();
            if (MalumHelper.areWeOnServer(world)) {
                world.setBlockState(p, cobblestone);
                world.playEvent(2001, p, Block.getStateId(cobblestone));
            } else {
                particles(world, p);
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

    public void particles(World world, BlockPos pos) {
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
                .evenlyRepeatEdges(world, pos, 4, Direction.UP, Direction.DOWN);
        ParticleManager.create(ParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(40)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color, color)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(world, pos, 6, Direction.UP, Direction.DOWN);
    }
}