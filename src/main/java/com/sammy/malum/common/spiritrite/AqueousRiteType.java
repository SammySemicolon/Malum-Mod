package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

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