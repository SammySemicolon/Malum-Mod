package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EarthenRiteType extends MalumRiteType {
    public EarthenRiteType() {
        super("earthen_rite", ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.GAIAN_BULWARK);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.EARTHEN_MIGHT);
    }
}
