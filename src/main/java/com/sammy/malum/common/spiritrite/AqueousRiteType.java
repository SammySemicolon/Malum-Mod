package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import com.sammy.malum.core.systems.rites.AuraRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;

public class AqueousRiteType extends MalumRiteType {
    public AqueousRiteType() {
        super("aqueous_rite", ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new AuraRiteEffect(LivingEntity.class, MalumMobEffectRegistry.POSEIDONS_GRASP, AQUEOUS_SPIRIT);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new AuraRiteEffect(LivingEntity.class, MalumMobEffectRegistry.ANGLERS_LURE, AQUEOUS_SPIRIT);
    }
}