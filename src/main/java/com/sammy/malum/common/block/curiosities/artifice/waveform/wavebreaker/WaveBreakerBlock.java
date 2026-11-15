package com.sammy.malum.common.block.curiosities.artifice.waveform.wavebreaker;

import com.sammy.malum.common.block.curiosities.artifice.waveform.WaveformDiodeBlock;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WaveBreakerBlock extends WaveformDiodeBlock<WaveBreakerBlockEntity> {

    public WaveBreakerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean processUpdate(Level level, BlockPos pos, BlockState state, WaveBreakerBlockEntity diode, int cachedSignal, int liveSignal) {
        if (diode.outputSignal == diode.pendingSignal) {
            if (cachedSignal != diode.pendingSignal) {
                diode.pendingSignal = cachedSignal;
                return true;
            }

            return false;
        }
        diode.outputSignal = diode.pendingSignal;

        var sound = diode.pendingSignal == 0 ? MalumBlockSoundEvents.WAVEBREAKER_RELEASE.get() : MalumBlockSoundEvents.WAVEBREAKER_STORE.get();
        level.playSound(null, pos, sound, SoundSource.BLOCKS);
        updateState(level, pos, state, diode);
        return cachedSignal != diode.outputSignal;
    }

    @Override
    public int redstoneTicksUntilUpdate(Level level, BlockPos pos, BlockState state, WaveBreakerBlockEntity diode, int cachedSignal, int liveSignal) {
        if (liveSignal == 0) {
            return 4;
        }
        return super.redstoneTicksUntilUpdate(level, pos, state, diode, cachedSignal, liveSignal);
    }

    @Override
    public boolean shouldUpdateWhenNeighborChanged(Level level, BlockPos pos, BlockState state, WaveBreakerBlockEntity diode, int liveSignal) {
		return liveSignal != diode.pendingSignal;
	}
}
