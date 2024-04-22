package com.sammy.malum.common.item.curiosities.curios.runes;

import com.sammy.malum.common.spiritrite.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.function.*;

public class TotemicRuneCurioItem extends AbstractRuneCurioItem {

    public final Supplier<MobEffect> mobEffectSupplier;

    public TotemicRuneCurioItem(Properties builder, TotemicRiteType riteType, boolean corrupted) {
        super(builder, riteType.getIdentifyingSpirit());
        if (!(riteType.getRiteEffect(corrupted) instanceof PotionRiteEffect potionRiteEffect)) {
            throw new IllegalArgumentException("Supplied rite type must have an aura effect");
        }
        mobEffectSupplier = potionRiteEffect.mobEffectSupplier;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        final LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.level().getGameTime() % 40L == 0) {
           livingEntity.addEffect(new MobEffectInstance(mobEffectSupplier.get(), 200, 0, true, true));
        }
        super.curioTick(slotContext, stack);
    }
}
