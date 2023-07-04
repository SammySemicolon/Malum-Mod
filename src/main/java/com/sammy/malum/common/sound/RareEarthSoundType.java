package com.sammy.malum.common.sound;

import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.sound.ExtendedSoundType;

import java.util.function.Supplier;

public class RareEarthSoundType extends ExtendedSoundType {
    public RareEarthSoundType(float volumeIn, float pitchIn, Supplier<SoundEvent> breakSoundIn, Supplier<SoundEvent> stepSoundIn, Supplier<SoundEvent> placeSoundIn, Supplier<SoundEvent> hitSoundIn, Supplier<SoundEvent> fallSoundIn) {
        super(volumeIn, pitchIn, breakSoundIn, stepSoundIn, placeSoundIn, hitSoundIn, fallSoundIn);
    }

    @Override
    public void onPlayBreakSound(Level level, BlockPos pos) {
        level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundRegistry.CTHONIC_GOLD_BREAK.get(), SoundSource.BLOCKS, (getVolume() + 1.0F) / 4.0F, 0.95F - level.random.nextFloat() * 0.4f, false);
    }

    @Override
    public void onPlayPlaceSound(Level level, BlockPos pos, Player player) {
        level.playSound(player, pos, SoundRegistry.CTHONIC_GOLD_PLACE.get(), SoundSource.BLOCKS, (getVolume() + 2.0F) / 4.0F, 1.05f - level.random.nextFloat() * 0.4f);
    }

    @Override
    @OnlyIn(value = Dist.CLIENT)
    public void onPlayHitSound(BlockPos pos) {
        MultiPlayerGameMode gameMode = Minecraft.getInstance().gameMode;
        if (gameMode != null) {
            float progress = gameMode.destroyProgress;
            float volume = (getVolume() + progress * progress * 4f) / 12f;
            float pitch = getPitch() * (0.5f + 0.15f * progress);
            Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(SoundEvents.NETHER_GOLD_ORE_HIT, SoundSource.BLOCKS, volume, pitch, SoundInstance.createUnseededRandom(), pos));
        }
    }
}