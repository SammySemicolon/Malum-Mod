package com.sammy.malum.core.handlers;

import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;

public class MalumAttributeEventHandler {
    public static void processAttributes(LivingHurtEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0) {
            return;
        }
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof LivingEntity attacker) {
            float amount = event.getAmount();
            var stack = SoulDataHandler.getScytheWeapon(source, attacker);
            if (stack.isEmpty()) {
                return;
            }
            if (source.is(DamageTypes.THORNS)) {
                return;
            }
            if (event.getSource().is(DamageTypeTagRegistry.IS_SCYTHE)) {
                var scytheProficiency = attacker.getAttribute(AttributeRegistry.SCYTHE_PROFICIENCY.get());
                if (scytheProficiency != null) {
                    final float amount1 = (float) (amount * scytheProficiency.getValue());
                    event.setAmount(amount1);
                }
            }
        }
    }
}