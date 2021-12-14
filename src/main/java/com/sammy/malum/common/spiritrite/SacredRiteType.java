package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class SacredRiteType extends MalumRiteType {
    public SacredRiteType() {
        super("sacred_rite", false, ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public void riteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            getNearbyEntities(PlayerEntity.class, world, pos, false).forEach(e -> {
                if (e.getActivePotionEffect(EffectRegistry.SACRED_AURA.get()) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(SACRED_SPIRIT_COLOR, e.getPosition().getX(), e.getPosition().getY() + e.getHeight() / 2f, e.getPosition().getZ()));
                }
                e.addPotionEffect(new EffectInstance(EffectRegistry.SACRED_AURA.get(), 100, 1));
            });
        }
    }

    @Override
    public void corruptedRiteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            getNearbyEntities(AnimalEntity.class, world, pos, true).forEach(e -> {
                if (world.rand.nextFloat() <= 0.04f) {
                    if (e.getGrowingAge() < 0) {
                        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(SACRED_SPIRIT_COLOR, e.getPosition().getX(), e.getPosition().getY() + e.getHeight() / 2f, e.getPosition().getZ()));
                        e.addGrowth(25);
                    }
                }
            });
        }
    }

    @Override
    public int range(boolean isCorrupted) {
        return defaultRange() / 2;
    }
}