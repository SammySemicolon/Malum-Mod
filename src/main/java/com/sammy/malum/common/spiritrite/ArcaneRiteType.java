package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.blockentity.TotemBaseTileEntity;
import com.sammy.malum.common.blockentity.TotemPoleTileEntity;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

import static com.sammy.malum.core.registry.block.BlockRegistry.SOULWOOD_TOTEM_BASE;
import static com.sammy.malum.core.registry.block.BlockRegistry.SOULWOOD_TOTEM_POLE;
import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.ARCANE_SPIRIT_COLOR;

public class ArcaneRiteType extends MalumRiteType
{
    public ArcaneRiteType()
    {
        super("arcane_rite", true, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT);
    }

    @Override
    public void riteEffect(Level level, BlockPos pos) {
        if (level.isClientSide)
        {
            return;
        }
        for (int i = 1; i <= 5; i++) {
            BlockPos totemPos = pos.above(i);
            if (level.getBlockEntity(totemPos) instanceof TotemPoleTileEntity totemPoleTile)
            {
                MalumSpiritType type = totemPoleTile.type;
                BlockState state = BlockHelper.setBlockStateWithExistingProperties(level, totemPos, SOULWOOD_TOTEM_POLE.get().defaultBlockState(),3);
                TotemPoleTileEntity newTileEntity = new TotemPoleTileEntity(totemPos, state);
                newTileEntity.setLevel(level);
                newTileEntity.create(type);
                level.setBlockEntity(newTileEntity);
                level.setBlockAndUpdate(totemPos, state);
                level.levelEvent(2001, totemPos, Block.getId(state));
            }
        }
        BlockState state = BlockHelper.setBlockStateWithExistingProperties(level, pos, SOULWOOD_TOTEM_BASE.get().defaultBlockState(),3);
        level.setBlockEntity(new TotemBaseTileEntity(pos, state));
        level.levelEvent(2001, pos, Block.getId(state));
    }

    @Override
    public void corruptedRiteEffect(Level level, BlockPos pos) {
    }



    public void particles(Level level, BlockPos pos) {
        Color color = ARCANE_SPIRIT_COLOR;
        RenderUtilities.create(ParticleRegistry.TWINKLE_PARTICLE)
                .setAlpha(0.4f, 0f)
                .setLifetime(20)
                .setSpin(0.3f)
                .setScale(0.2f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(level, pos, 4, Direction.UP, Direction.DOWN);
        RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
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
