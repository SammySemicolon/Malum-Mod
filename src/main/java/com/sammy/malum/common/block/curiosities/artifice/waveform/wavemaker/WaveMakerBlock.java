package com.sammy.malum.common.block.curiosities.artifice.waveform.wavemaker;

import com.sammy.malum.common.block.curiosities.artifice.waveform.WaveformDiodeBlock;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WaveMakerBlock extends WaveformDiodeBlock<WaveMakerBlockEntity> {
    public WaveMakerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (oldState.getBlock().equals(this)) {
            return;
        }
        level.scheduleTick(pos, this, 20);
    }

    @Override
    public int redstoneTicksUntilUpdate(Level level, BlockPos pos, BlockState state, WaveMakerBlockEntity diode, int cachedSignal, int liveSignal) {
        return diode.inverted ? 8 : super.redstoneTicksUntilUpdate(level, pos, state, diode, cachedSignal, liveSignal);
    }

    @Override
    public boolean processUpdate(Level level, BlockPos pos, BlockState state, WaveMakerBlockEntity diode, int cachedSignal, int liveSignal) {
        diode.outputSignal = liveSignal;

        if (!diode.inverted) {
            level.playSound(null, pos, MalumBlockSoundEvents.WAVEMAKER_PULSE.get(), SoundSource.BLOCKS);
        }
        diode.inverted = !diode.inverted;

        updateState(level, pos, state, diode);
        return true;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        // Will check during update loop anyway
    }

    @Override
    public boolean shouldUpdateWhenNeighborChanged(Level level, BlockPos pos, BlockState state, WaveMakerBlockEntity diode, int liveSignal) {
        return false;
    }
}
