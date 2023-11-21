package com.sammy.malum.core.systems.rites;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.MajorEntityEffectParticlePacket;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class PotionRiteEffect extends MalumRiteEffect {

    public final Class<? extends LivingEntity> targetClass;
    public final Supplier<MobEffect> mobEffectSupplier;

    public PotionRiteEffect(Class<? extends LivingEntity> targetClass, Supplier<MobEffect> mobEffectSupplier) {
        super(MalumRiteEffectCategory.AURA);
        this.targetClass = targetClass;
        this.mobEffectSupplier = mobEffectSupplier;
    }

    @Override
    public void doRiteEffect(TotemBaseBlockEntity totemBase) {
        getNearbyEntities(totemBase, targetClass).filter(getEntityPredicate()).forEach(e -> {
            MobEffectInstance instance = new MobEffectInstance(mobEffectSupplier.get(), 400, 1, true, true);
            if (!e.hasEffect(instance.getEffect())) {
                MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> e), new MajorEntityEffectParticlePacket(totemBase.rite.getEffectSpirit().getPrimaryColor(), e.getX(), e.getY()+ e.getBbHeight() / 2f, e.getZ()));
            }
            e.addEffect(instance);
        });
    }

    public Predicate<LivingEntity> getEntityPredicate() {
        return e -> !(e instanceof Monster);
    }
}