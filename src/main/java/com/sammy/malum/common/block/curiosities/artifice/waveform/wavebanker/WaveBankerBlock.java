package com.sammy.malum.common.block.curiosities.artifice.waveform.wavebanker;

import com.sammy.malum.common.block.curiosities.artifice.waveform.WaveformDiodeBlock;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WaveBankerBlock extends WaveformDiodeBlock<WaveBankerBlockEntity> {
    public WaveBankerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean processUpdate(Level level, BlockPos pos, BlockState state, WaveBankerBlockEntity diode, int cachedSignal, int liveSignal) {
        int previousSignal = diode.outputSignal;
        diode.outputSignal = cachedSignal;
        var sound = previousSignal > diode.outputSignal ? MalumBlockSoundEvents.WAVEBANKER_RELEASE.get() : MalumBlockSoundEvents.WAVEBANKER_STORE.get();
        level.playSound(null, pos, sound, SoundSource.BLOCKS);
        updateState(level, pos, state, diode);
        return diode.outputSignal > 0 && liveSignal == 0;
    }

    @Override
    public boolean shouldUpdateWhenNeighborChanged(Level level, BlockPos pos, BlockState state, WaveBankerBlockEntity diode, int liveSignal) {
        return liveSignal != diode.outputSignal;
    }

    @Override
    public int redstoneTicksUntilUpdate(Level level, BlockPos pos, BlockState state, WaveBankerBlockEntity diode, int cachedSignal, int liveSignal) {
        if (liveSignal >= diode.outputSignal) {
            return 4;
        }
        return super.redstoneTicksUntilUpdate(level, pos, state, diode, cachedSignal, liveSignal);
    }
}
