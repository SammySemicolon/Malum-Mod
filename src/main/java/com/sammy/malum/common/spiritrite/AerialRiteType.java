package com.sammy.malum.common.spiritrite;

import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.core.systems.rites.AuraRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.entity.LivingEntity;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.AERIAL_SPIRIT;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class AerialRiteType extends MalumRiteType {
    public AerialRiteType() {
        super("aerial_rite", ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new AuraRiteEffect(LivingEntity.class, MobEffectRegistry.ZEPHYRS_COURAGE, AERIAL_SPIRIT);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new AuraRiteEffect(LivingEntity.class, MobEffectRegistry.AETHERS_CHARM, AERIAL_SPIRIT);
    }
}