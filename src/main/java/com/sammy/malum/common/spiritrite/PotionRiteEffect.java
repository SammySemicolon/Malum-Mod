package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.BlockSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.MajorEntityEffectParticlePacket;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;


import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.INFERNAL_SPIRIT;

public class PotionRiteEffect extends TotemicRiteEffect {

    public final Class<? extends LivingEntity> targetClass;
    public final Supplier<MobEffect> mobEffectSupplier;

    public PotionRiteEffect(Class<? extends LivingEntity> targetClass, Supplier<MobEffect> mobEffectSupplier) {
        super(MalumRiteEffectCategory.AURA);
        this.targetClass = targetClass;
        this.mobEffectSupplier = mobEffectSupplier;
    }

    @Override
    public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
        getNearbyEntities(totemBase, targetClass).filter(getEntityPredicate()).forEach(e -> {
            MobEffectInstance instance = new MobEffectInstance(mobEffectSupplier.get(), 400, 1, true, true);
            if (!e.hasEffect(instance.getEffect())) {
                MALUM_CHANNEL.sendToClientsTrackingAndSelf(new MajorEntityEffectParticlePacket(totemBase.activeRite.getIdentifyingSpirit().getPrimaryColor(), e.getX(), e.getY()+ e.getBbHeight() / 2f, e.getZ()), e);
            }
            e.addEffect(instance);
        });
    }

    public Predicate<LivingEntity> getEntityPredicate() {
        return e -> !(e instanceof Monster);
    }
}