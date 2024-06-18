package com.sammy.malum.common.item.curiosities.trinkets.runes;

import com.sammy.malum.common.spiritrite.PotionRiteEffect;
import com.sammy.malum.common.spiritrite.TotemicRiteType;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class TotemicRuneTrinketsItem extends AbstractRuneTrinketsItem {

    public final Supplier<MobEffect> mobEffectSupplier;
    public final Predicate<LivingEntity> entityPredicate;
    private final int interval;

    public TotemicRuneTrinketsItem(Properties builder, TotemicRiteType riteType, boolean corrupted) {
        this(builder, riteType, corrupted, 40);
    }

    public TotemicRuneTrinketsItem(Properties builder, TotemicRiteType riteType, boolean corrupted, int interval) {
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
