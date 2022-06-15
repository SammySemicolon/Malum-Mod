package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.entity.MajorEntityEffectParticlePacket;
import com.sammy.malum.common.packets.particle.entity.MinorEntityEffectParticlePacket;
import com.sammy.malum.core.setup.content.DamageSourceRegistry;
import com.sammy.malum.core.systems.rites.EntityAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.WICKED_SPIRIT;
import static com.sammy.malum.core.setup.server.PacketRegistry.MALUM_CHANNEL;

public class WickedRiteType extends MalumRiteType {
    public WickedRiteType() {
        super("wicked_rite", ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new EntityAffectingRiteEffect() {
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, LivingEntity.class).forEach(e -> {
                    if (e.getHealth() > 2.5f && !e.isInvulnerableTo(DamageSourceRegistry.VOODOO)) {
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> e), new MinorEntityEffectParticlePacket(getEffectSpirit().getColor(), e.getX(), e.getY()+ e.getBbHeight() / 2f, e.getZ()));
                        e.hurt(DamageSourceRegistry.VOODOO, 2);
                    }
                });
            }
        };

    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new EntityAffectingRiteEffect() {
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, LivingEntity.class).forEach(e -> {
                    if (e.getHealth() <= 2.5f && !e.isInvulnerableTo(DamageSourceRegistry.VOODOO)) {
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MajorEntityEffectParticlePacket(getEffectSpirit().getColor(), e.getX(), e.getY()+ e.getBbHeight() / 2f, e.getZ()));
                        e.hurt(DamageSourceRegistry.FORCED_SHATTER, 10f);
                    }
                });
            }
        };
    }
}