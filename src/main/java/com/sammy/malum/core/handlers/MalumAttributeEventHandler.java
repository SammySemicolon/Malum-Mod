package com.sammy.malum.core.handlers;

import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.tools.MalumScytheItem;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MalumAttributeEventHandler {
    public static void processAttributes(LivingHurtEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0) {
            return;
        }
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof LivingEntity attacker) {
            float amount = event.getAmount();
            ItemStack stack = MalumScytheItem.getScytheItemStack(source, attacker);
            if (stack.isEmpty()) {
                return;
            }
            if (source instanceof EntityDamageSource entityDamageSource) {
                if (entityDamageSource.isThorns()) {
                    return;
                }
            }
            if (!event.getSource().isMagic()) {
                AttributeInstance scytheProficiency = attacker.getAttribute(AttributeRegistry.SCYTHE_PROFICIENCY.get());
                if (scytheProficiency != null && scytheProficiency.getValue() > 0) {
                    event.setAmount((float) (amount + scytheProficiency.getValue() * 0.5f));
                }

            }
        }
    }
}