package com.sammy.malum.common.item.curiosities.curios.runes;

import com.sammy.malum.common.spiritrite.*;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;


import java.util.function.*;

public class TotemicRuneCurioItem extends AbstractRuneCurioItem {

    public final Supplier<MobEffect> mobEffectSupplier;
    public final Predicate<LivingEntity> entityPredicate;
    private final int interval;

    public TotemicRuneCurioItem(Properties builder, TotemicRiteType riteType, boolean corrupted) {
        this(builder, riteType, corrupted, 40);
    }

    public TotemicRuneCurioItem(Properties builder, TotemicRiteType riteType, boolean corrupted, int interval) {
        super(builder, riteType.getIdentifyingSpirit());
        this.interval = interval;
        if (!(riteType.getRiteEffect(corrupted) instanceof PotionRiteEffect potionRiteEffect)) {
            throw new IllegalArgumentException("Supplied rite type must have an aura effect");
        }
        mobEffectSupplier = potionRiteEffect.mobEffectSupplier;
        entityPredicate = potionRiteEffect.getEntityPredicate();
    }


    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        final LivingEntity livingEntity = slot.inventory().getComponent().getEntity();
        if (!livingEntity.level().isClientSide() && livingEntity.level().getGameTime() % interval == 0 && entityPredicate.test(livingEntity)) {
            livingEntity.addEffect(new MobEffectInstance(mobEffectSupplier.get(), 200, 0, true, true));
        }
        super.tick(stack, slot, entity);
    }
}
