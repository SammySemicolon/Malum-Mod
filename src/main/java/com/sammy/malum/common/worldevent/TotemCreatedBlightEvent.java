package com.sammy.malum.common.worldevent;

import com.sammy.malum.common.blockentity.totem.TotemBaseTileEntity;
import com.sammy.malum.common.blockentity.totem.TotemPoleTileEntity;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.ortus.helpers.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.sammy.malum.core.setup.content.block.BlockRegistry.SOULWOOD_TOTEM_BASE;
import static com.sammy.malum.core.setup.content.block.BlockRegistry.SOULWOOD_TOTEM_POLE;

public class TotemCreatedBlightEvent extends ActiveBlightEvent {
    public int totemTakeoverTimer;

    @Override
    public void tick(Level level) {
        if (totemTakeoverTimer == 0) {
            BlockState state = BlockHelper.setBlockStateWithExistingProperties(level, sourcePos, SOULWOOD_TOTEM_BASE.get().defaultBlockState(), 3);
            level.setBlockEntity(new TotemBaseTileEntity(sourcePos, state));
            level.levelEvent(null, 2001, sourcePos, Block.getId(state));
            level.playSound(null, sourcePos, SoundRegistry.MINOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1f, 1.8f);
        }
        totemTakeoverTimer++;
        if (totemTakeoverTimer % 2 == 0) {
            if (level.random.nextBoolean()) {
                totemTakeoverTimer--;
            }
            int offset = totemTakeoverTimer / 2;
            BlockPos totemPos = sourcePos.above(offset);
            if (level.getBlockEntity(totemPos) instanceof TotemPoleTileEntity totemPoleTile) {
                MalumSpiritType type = totemPoleTile.type;
                BlockState state = BlockHelper.setBlockStateWithExistingProperties(level, totemPos, SOULWOOD_TOTEM_POLE.get().defaultBlockState(), 3);
                TotemPoleTileEntity newTileEntity = new TotemPoleTileEntity(totemPos, state);
                newTileEntity.setLevel(level);
                newTileEntity.create(type);
                level.setBlockEntity(newTileEntity);
                level.setBlockAndUpdate(totemPos, state);

                level.levelEvent(null, 2001, totemPos, Block.getId(state));
                level.playSound(null, sourcePos, SoundRegistry.MINOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1f, 1.8f);
            }
        }
        super.tick(level);

    }

    @Override
    public void end(Level level) {
        if (totemTakeoverTimer >= 12) {
            super.end(level);
        }
    }
}