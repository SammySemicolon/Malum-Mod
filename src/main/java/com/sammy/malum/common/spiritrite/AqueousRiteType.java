package com.sammy.malum.common.spiritrite;

import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.core.systems.rites.PotionRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.entity.LivingEntity;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.AQUEOUS_SPIRIT;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class AqueousRiteType extends MalumRiteType {
    public AqueousRiteType() {
        super("aqueous_rite", ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.POSEIDONS_GRASP);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.ANGLERS_LURE);
    }
}