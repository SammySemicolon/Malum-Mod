package com.sammy.malum.common.block.curiosities.artifice;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.toolkit.blockentity.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.color.*;

import java.awt.*;

public abstract class ConfigurableArtificeBlockEntity extends LodestoneBlockEntity {

    protected ArtificeTinkeringInfo info;

    public int closeDelay;

    public ConfigurableArtificeBlockEntity(LodestoneBlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract void setInfo(ArtificeTinkeringInfo info);

    public abstract ArtificeTinkeringInfo defaultTinkeringState();

    @Override
    public void serverTick(ServerLevel level) {
        if (closeDelay > 0) {
            closeDelay--;
            if (closeDelay == 0) {
                handleTinkeredStateChange(level, false, defaultTinkeringState());
            }
        }
    }

    public void handleTinkeredStateChange(ServerLevel level, boolean openValue, ArtificeTinkeringInfo artificeTinkeringInfo) {
        if (!canBeTinkered()) {
            return;
        }
        var affected = redirectTinkerFocus();
        if (affected != this) {
            affected.handleTinkeredStateChange(level, openValue, artificeTinkeringInfo);
            return;
        }
        var state = getBlockState();
        if (state.getValue(BlockStateProperties.OPEN) != openValue) {
            level.setBlock(getBlockPos(), state.cycle(BlockStateProperties.OPEN), 3);
            var particleEffect = openValue ? MalumParticleEffectTypes.WAVEFORM_DIODE_OPEN : MalumParticleEffectTypes.WAVEFORM_DIODE_CLOSE;
            var sound = openValue ? MalumBlockSoundEvents.SPIRIT_DIODE_OPEN.get() : MalumBlockSoundEvents.SPIRIT_DIODE_CLOSE.get();
            float pitch = Easing.SINE_IN_OUT.asWeighedRandom(level.getRandom(), 0.9f, 1.1f);
            level.playSound(null, getBlockPos(), sound, SoundSource.BLOCKS, 0.8f, pitch);
            particleEffect.createEffect()
                    .at(getBlockPos().getCenter().add(0, openValue ? 0.5f : 0, 0))
                    .color(ColorParticleData.create(new Color(170, 15, 1), new Color(129, 12, 0)).build())
                    .spawn(level);
            setDirty();

            affected.setInfo(artificeTinkeringInfo);
        }
        closeDelay = openValue ? 100 : 0;
    }

    public ConfigurableArtificeBlockEntity redirectTinkerFocus() {
        return this;
    }

    public boolean canBeTinkered() {
        return true;
    }
}