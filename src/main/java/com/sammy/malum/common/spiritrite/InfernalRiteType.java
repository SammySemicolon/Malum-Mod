package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.Level.Level;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class InfernalRiteType extends MalumRiteType {
    public InfernalRiteType() {
        super("infernal_rite", false, ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public void riteEffect(Level Level, BlockPos pos) {
        if (MalumHelper.areWeOnServer(Level)) {
            getNearbyEntities(Player.class, Level, pos, false).forEach(e -> {
                if (e.getEffect(EffectRegistry.INFERNAL_AURA.get()) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(INFERNAL_SPIRIT_COLOR, e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                }
                e.addEffect(new MobEffectInstance(EffectRegistry.INFERNAL_AURA.get(), 100, 1));
            });
        }
    }

    @Override
    public void corruptedRiteEffect(Level Level, BlockPos pos) {
        if (MalumHelper.areWeOnServer(Level)) {
            getNearbyEntities(Player.class, Level, pos, true).forEach(e -> {
                if (e.getEffect(Effects.FIRE_RESISTANCE) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(INFERNAL_SPIRIT_COLOR, e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                }
                e.addEffect(new MobEffectInstance(Effects.FIRE_RESISTANCE, 200, 0));
            });
        }
    }
}
