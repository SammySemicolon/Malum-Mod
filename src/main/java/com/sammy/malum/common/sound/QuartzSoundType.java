package com.sammy.malum.common.sound;

import com.sammy.malum.core.setup.content.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.sound.ExtendedSoundType;

import java.util.function.Supplier;

public class QuartzSoundType extends ExtendedSoundType {
    public QuartzSoundType(float volumeIn, float pitchIn, Supplier<SoundEvent> breakSoundIn, Supplier<SoundEvent> stepSoundIn, Supplier<SoundEvent> placeSoundIn, Supplier<SoundEvent> hitSoundIn, Supplier<SoundEvent> fallSoundIn) {
        super(volumeIn, pitchIn, breakSoundIn, stepSoundIn, placeSoundIn, hitSoundIn, fallSoundIn);
    }

    @Override
    public void onPlayBreakSound(Level level, BlockPos pos) {
        level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundRegistry.QUARTZ_CLUSTER_BLOCK_BREAK.get(), SoundSource.BLOCKS, (getVolume() + 1.0F) / 6.0F, getPitch() * 1.8F, false);
    }

    @Override
    public void onPlayPlaceSound(Level level, BlockPos pos, Player player) {
        level.playSound(player, pos, SoundRegistry.QUARTZ_CLUSTER_BLOCK_PLACE.get(), SoundSource.BLOCKS, (getVolume() + 1.0F) / 8.0F, getPitch() * 1.6F);
    }

    @Override
    @OnlyIn(value = Dist.CLIENT)
    public void onPlayHitSound(BlockPos pos) {
        Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(SoundRegistry.QUARTZ_CLUSTER_BLOCK_HIT.get(), SoundSource.BLOCKS, (getVolume() + 1.0F) / 12.0F, getPitch() * 1.65F, SoundInstance.createUnseededRandom(), pos));
    }
}