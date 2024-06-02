package com.sammy.malum.common.spiritrite.arcane;

import com.sammy.malum.common.spiritrite.PotionRiteEffect;
import com.sammy.malum.common.spiritrite.TotemicRiteEffect;
import com.sammy.malum.common.spiritrite.TotemicRiteType;
import com.sammy.malum.registry.common.MobEffectRegistry;
import net.minecraft.world.entity.LivingEntity;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.AQUEOUS_SPIRIT;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class AqueousRiteType extends TotemicRiteType {
    public AqueousRiteType() {
        super("aqueous_rite", ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.POSEIDONS_GRASP);
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.ANGLERS_LURE);
    }
}