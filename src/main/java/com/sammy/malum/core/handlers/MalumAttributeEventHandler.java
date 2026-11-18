package com.sammy.malum.core.handlers;

import com.sammy.malum.config.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.event.entity.living.*;

import java.util.*;

public class MalumAttributeEventHandler {

    public static void processAttributes(LivingDamageEvent.Pre event) {
        if (event.getNewDamage() <= 0) {
            return;
        }
        var source = event.getSource();
        if (!(source.getEntity() instanceof LivingEntity attacker)) {
            return;
        }
        if (!source.is(MalumTags.DamageTypes.IS_SCYTHE)) {
            return;
        }

        double scytheProficiency = attacker.getAttributeValue(MalumAttributes.SCYTHE_PROFICIENCY);
        event.setNewDamage((float) (event.getNewDamage() * scytheProficiency));
    }

    public static Optional<Float> modifyMagicDamageArmorPiercing(LivingEntity livingEntity, DamageSource damageSource, float damageAmount) {
        if (!CommonConfig.MAGIC_DAMAGE_REDUCED_ARMOR_PIERCING.getConfigValue()) {
            return Optional.empty();
        }
        if (!damageSource.is(MalumTags.DamageTypes.BYPASSES_HALF_ARMOR)) {
            return Optional.empty();
        }
        float armor = livingEntity.getArmorValue() * 0.5f;
        float toughness = (float) livingEntity.getAttributeValue(Attributes.ARMOR_TOUGHNESS) * 0.5f;
        var newAmount = CombatRules.getDamageAfterAbsorb(livingEntity, damageAmount, damageSource, armor, toughness);
        return Optional.of(newAmount);
    }
}