package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class SacredRiteType extends MalumRiteType {
    public SacredRiteType() {
        super("sacred_rite", ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public void riteEffect(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            getNearbyEntities(Player.class, level, pos, false).forEach(e -> {
                if (e.getEffect(EffectRegistry.SACRED_AURA.get()) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(SACRED_SPIRIT_COLOR, e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                }
                e.addEffect(new MobEffectInstance(EffectRegistry.SACRED_AURA.get(), 200, 1));
            });
        }
    }

    @Override
    public void corruptedRiteEffect(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            getNearbyEntities(Animal.class, level, pos, true).forEach(e -> {
                if (level.random.nextFloat() <= 0.04f) {
                    if (e.getAge() < 0) {
                        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(SACRED_SPIRIT_COLOR, e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                        e.ageUp(25);
                    }
                }
            });
        }
    }

    @Override
    public int range(boolean corrupted) {
        return defaultRange() / 2;
    }
}