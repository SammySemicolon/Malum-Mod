package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.entity.MajorEntityEffectParticlePacket;
import com.sammy.malum.common.packets.particle.entity.MinorEntityEffectParticlePacket;
import com.sammy.malum.registry.common.DamageSourceRegistry;
import com.sammy.malum.core.systems.rites.EntityAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.WICKED_SPIRIT;
import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

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
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> e), new MinorEntityEffectParticlePacket(getEffectSpirit().getColor(), e.getX(), e.getY() + e.getBbHeight() / 2f, e.getZ()));
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
                getNearbyEntities(totemBase, Monster.class).forEach(e -> {

                    MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MajorEntityEffectParticlePacket(getEffectSpirit().getColor(), e.getX(), e.getY() + e.getBbHeight() / 2f, e.getZ()));
                    e.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1));
                    e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1));
                    e.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1));
                });
            }
        };
    }
}