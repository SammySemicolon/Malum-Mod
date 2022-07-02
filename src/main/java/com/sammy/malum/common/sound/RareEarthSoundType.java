package com.sammy.malum.common.sound;

import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.ortus.systems.sound.ExtendedSoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class RareEarthSoundType extends ExtendedSoundType {
    public RareEarthSoundType(float volumeIn, float pitchIn, Supplier<SoundEvent> breakSoundIn, Supplier<SoundEvent> stepSoundIn, Supplier<SoundEvent> placeSoundIn, Supplier<SoundEvent> hitSoundIn, Supplier<SoundEvent> fallSoundIn) {
        super(volumeIn, pitchIn, breakSoundIn, stepSoundIn, placeSoundIn, hitSoundIn, fallSoundIn);
    }

    @Override
    @OnlyIn(value = Dist.CLIENT)
    public void onPlayHitSound(BlockPos pos) {
        MultiPlayerGameMode gameMode = Minecraft.getInstance().gameMode;
        if (gameMode != null) {
            float progress = gameMode.destroyProgress;
            float volume = (getVolume() + progress * progress * 4f) / 12f;
            float pitch = getPitch() * (0.5f + 0.15f * progress);
            Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(SoundEvents.NETHER_ORE_HIT, SoundSource.BLOCKS, volume, pitch, pos));
        }
    }
}