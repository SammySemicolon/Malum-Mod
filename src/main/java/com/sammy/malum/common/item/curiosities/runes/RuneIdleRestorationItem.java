package com.sammy.malum.common.item.curiosities.runes;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class RuneIdleRestorationItem extends MalumRuneCurioItem {

    public RuneIdleRestorationItem(Properties builder) {
        super(builder, SpiritTypeRegistry.SACRED_SPIRIT);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        final LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.level().getGameTime() % 40L == 0) {
            livingEntity.heal(1);
        }
        super.curioTick(slotContext, stack);
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("idle_restoration");
    }
}