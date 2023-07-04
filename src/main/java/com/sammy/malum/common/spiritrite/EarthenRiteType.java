package com.sammy.malum.common.spiritrite;

import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.core.systems.rites.AuraRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.entity.LivingEntity;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.EARTHEN_SPIRIT;

public class EarthenRiteType extends MalumRiteType {
    public EarthenRiteType() {
        super("earthen_rite", ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new AuraRiteEffect(LivingEntity.class, MobEffectRegistry.GAIAN_BULWARK, EARTHEN_SPIRIT);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new AuraRiteEffect(LivingEntity.class, MobEffectRegistry.EARTHEN_MIGHT, EARTHEN_SPIRIT);
    }
}
