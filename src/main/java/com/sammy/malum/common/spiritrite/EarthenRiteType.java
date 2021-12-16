package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.Level.Level;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class EarthenRiteType extends MalumRiteType
{
    public EarthenRiteType()
    {
        super("earthen_rite", false, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public void riteEffect(Level Level, BlockPos pos) {
        if (MalumHelper.areWeOnServer(Level)) {
            getNearbyEntities(Player.class, Level, pos, false).forEach(e -> {
                if (e.getEffect(EffectRegistry.EARTHEN_AURA.get()) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(EARTHEN_SPIRIT_COLOR, e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                }
                e.addEffect(new MobEffectInstance(EffectRegistry.EARTHEN_AURA.get(), 100, 1));
            });
        }
    }

    @Override
    public void corruptedRiteEffect(Level Level, BlockPos pos) {
        if (MalumHelper.areWeOnServer(Level)) {
            getNearbyEntities(Player.class, Level, pos, false).forEach(e -> {
                if (e.getEffect(EffectRegistry.CORRUPTED_EARTHEN_AURA.get()) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(EARTHEN_SPIRIT_COLOR, e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                }
                e.addEffect(new MobEffectInstance(EffectRegistry.CORRUPTED_EARTHEN_AURA.get(), 100, 1));
            });
        }
    }
}
