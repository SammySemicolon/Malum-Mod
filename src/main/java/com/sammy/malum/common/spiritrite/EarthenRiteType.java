package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.setup.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.setup.PacketRegistry.INSTANCE;

public class EarthenRiteType extends MalumRiteType
{
    public EarthenRiteType()
    {
        super("earthen_rite", ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public void riteEffect(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            getNearbyEntities(Player.class, level, pos, false).forEach(e -> {
                if (e.getEffect(EffectRegistry.EARTHEN_AURA.get()) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(EARTHEN_SPIRIT_COLOR, e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                }
                e.addEffect(new MobEffectInstance(EffectRegistry.EARTHEN_AURA.get(), 200, 1));
            });
        }
    }

    @Override
    public void corruptedRiteEffect(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            getNearbyEntities(Player.class, level, pos, false).forEach(e -> {
                if (e.getEffect(EffectRegistry.CORRUPTED_EARTHEN_AURA.get()) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(EARTHEN_SPIRIT_COLOR, e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                }
                e.addEffect(new MobEffectInstance(EffectRegistry.CORRUPTED_EARTHEN_AURA.get(), 200, 1));
            });
        }
    }
}
