package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.blockentity.totem.TotemBaseTileEntity;
import com.sammy.malum.common.blockentity.totem.TotemPoleTileEntity;
import com.sammy.malum.common.recipe.BlockTransmutationRecipe;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.core.setup.content.block.BlockRegistry.SOULWOOD_TOTEM_BASE;
import static com.sammy.malum.core.setup.content.block.BlockRegistry.SOULWOOD_TOTEM_POLE;

public class ArcaneRiteType extends MalumRiteType {
    public ArcaneRiteType() {
        super("arcane_rite", ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT);
    }

    @Override
    public boolean isInstant(boolean corrupted) {
        return !corrupted;
    }

    @Override
    public int interval(boolean corrupted) {
        return defaultInterval() * 5;
    }

    @Override
    public int range(boolean corrupted) {
        return defaultRange() / 4;
    }

    @Override
    public void riteEffect(Level level, BlockPos pos, int height) {
        if (level.isClientSide) {
            return;
        }
        for (int i = 1; i <= 5; i++) {
            BlockPos totemPos = pos.above(i);
            if (level.getBlockEntity(totemPos) instanceof TotemPoleTileEntity totemPoleTile) {
                MalumSpiritType type = totemPoleTile.type;
                BlockState state = BlockHelper.setBlockStateWithExistingProperties(level, totemPos, SOULWOOD_TOTEM_POLE.get().defaultBlockState(), 3);
                TotemPoleTileEntity newTileEntity = new TotemPoleTileEntity(totemPos, state);
                newTileEntity.setLevel(level);
                newTileEntity.create(type);
                level.setBlockEntity(newTileEntity);
                level.setBlockAndUpdate(totemPos, state);
                level.levelEvent(2001, totemPos, Block.getId(state));
            }
        }
        BlockState state = BlockHelper.setBlockStateWithExistingProperties(level, pos, SOULWOOD_TOTEM_BASE.get().defaultBlockState(), 3);
        level.setBlockEntity(new TotemBaseTileEntity(pos, state));
        level.levelEvent(2001, pos, Block.getId(state));
    }

    @Override
    public void corruptedRiteEffect(Level level, BlockPos pos, int height) {
        BlockState filter = level.getBlockState(pos.below());
        BlockTransmutationRecipe fillerRecipe = BlockTransmutationRecipe.getRecipe(level, filter.getBlock());
        ArrayList<BlockPos> positions = getNearbyBlocks(Block.class, level, pos.above(2), 3, false);
        positions.removeIf(p -> {
            if (p.getX() == pos.getX() && p.getZ() == pos.getZ()) {
                return true;
            }
            BlockState state = level.getBlockState(p);
            return fillerRecipe != null && !filter.isAir() && !filter.is(state.getBlock());
        });

        positions.forEach(p -> {
            BlockState state = level.getBlockState(p);
            BlockTransmutationRecipe recipe = BlockTransmutationRecipe.getRecipe(level, state.getBlock());
            if (recipe != null) {
                if (!level.isClientSide) {
                    Block block = recipe.output;
                    BlockEntity entity = level.getBlockEntity(p);
                    BlockState newState = BlockHelper.setBlockStateWithExistingProperties(level, p, block.defaultBlockState(), 3);
                    level.levelEvent(2001, p, Block.getId(newState));
                    if (block instanceof EntityBlock entityBlock) {
                        if (entity != null) {
                            BlockEntity newEntity = entityBlock.newBlockEntity(pos, newState);
                            if (newEntity != null) {
                                if (newEntity.getClass().equals(entity.getClass())) {
                                    level.setBlockEntity(entity);
                                }
                            }
                        }
                    }
                } else {
                    particles(level, p);
                }
            }
        });
    }

    public void particles(Level level, BlockPos pos) {
        Color color = ARCANE_SPIRIT.getColor();
        ParticleBuilders.create(OrtusParticleRegistry.TWINKLE_PARTICLE)
                .setAlpha(0.4f, 0f)
                .setLifetime(20)
                .setSpin(0.3f)
                .setScale(0.2f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomMotion(0.001f, 0.001f)
                .evenlyRepeatEdges(level, pos, 4, Direction.UP, Direction.DOWN);
        ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
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