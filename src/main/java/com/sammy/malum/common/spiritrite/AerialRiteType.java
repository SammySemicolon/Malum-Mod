package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class AerialRiteType extends MalumRiteType {
    public AerialRiteType() {
        super("aerial_rite", ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.ZEPHYRS_COURAGE);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.AETHERS_CHARM);
    }
}