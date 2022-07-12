package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.rites.AuraRiteEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;

public class EarthenRiteType extends MalumRiteType {
    public EarthenRiteType() {
        super("earthen_rite", ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new AuraRiteEffect(LivingEntity.class, MalumMobEffectRegistry.GAIAN_BULWARK, EARTHEN_SPIRIT);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new AuraRiteEffect(LivingEntity.class, MalumMobEffectRegistry.EARTHEN_MIGHT, EARTHEN_SPIRIT);
    }
}
