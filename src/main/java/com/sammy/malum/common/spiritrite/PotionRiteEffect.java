package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.effect.aura.EarthenAura;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.MajorEntityEffectParticlePacket;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class PotionRiteEffect extends TotemicRiteEffect {

    public final Class<? extends LivingEntity> targetClass;
    public final Supplier<EarthenAura> mobEffectSupplier;

    public PotionRiteEffect(Class<? extends LivingEntity> targetClass, Supplier<EarthenAura> mobEffectSupplier) {
        super(MalumRiteEffectCategory.AURA);
        this.targetClass = targetClass;
        this.mobEffectSupplier = mobEffectSupplier;
    }

    @Override
    public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
        getNearbyEntities(totemBase, targetClass).filter(getEntityPredicate()).forEach(e -> {
            MobEffectInstance instance = new MobEffectInstance(mobEffectSupplier.get(), 1200, 1, true, true);
            if (!e.hasEffect(instance.getEffect())) {
                MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> e), new MajorEntityEffectParticlePacket(totemBase.activeRite.getIdentifyingSpirit().getPrimaryColor(), e.getX(), e.getY()+ e.getBbHeight() / 2f, e.getZ()));
            }
            e.addEffect(instance);
        });
    }

    public Predicate<LivingEntity> getEntityPredicate() {
        return e -> !(e instanceof Monster);
    }
}