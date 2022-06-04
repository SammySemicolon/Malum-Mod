package com.sammy.malum.core.systems.rites;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

import static com.sammy.malum.core.setup.server.PacketRegistry.INSTANCE;

public class PotionRiteEffect extends EntityAffectingRiteEffect {

    public final Class<? extends LivingEntity> targetClass;
    public final Supplier<MobEffect> effect;
    public final MalumSpiritType spirit;

    public PotionRiteEffect(Class<? extends LivingEntity> targetClass, Supplier<MobEffect> effect, MalumSpiritType spirit) {
        super();
        this.targetClass = targetClass;
        this.effect = effect;
        this.spirit = spirit;
    }
    @SuppressWarnings("ConstantConditions")
    @Override
    public void riteEffect(TotemBaseBlockEntity totemBase) {
        if (totemBase.getLevel().isClientSide) {
            return;
        }
        getNearbyEntities(totemBase, getEntityClass()).forEach(e -> {
            if (e.getEffect(effect.get()) == null) {
                INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(spirit.getColor(), e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
            }
            e.addEffect(new MobEffectInstance(effect.get(), getEffectDuration(), getEffectAmplifier()));
        });
    }

    public Class<? extends LivingEntity> getEntityClass() {
        return targetClass;
    }

    public int getEffectDuration() {
        return 300;
    }

    public int getEffectAmplifier() {
        return 1;
    }
}