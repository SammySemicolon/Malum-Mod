package com.sammy.malum.core.handlers;

import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.tools.ModScytheItem;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.damage.DamageSourceRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AttributeEventHandler {
    public static void processAttributes(LivingHurtEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0) {
            return;
        }
        DamageSource source = event.getSource();
        LivingEntity target = event.getEntityLiving();
        if (source.isMagic()) {
            float amount = event.getAmount();
            if (source.getEntity() instanceof LivingEntity attacker) {
                AttributeInstance magicProficiency = attacker.getAttribute(AttributeRegistry.MAGIC_PROFICIENCY.get());
                if (magicProficiency != null && magicProficiency.getValue() > 0) {
                    amount += magicProficiency.getValue() * 0.5f;
                }
            }
            AttributeInstance magicResistance = target.getAttribute(AttributeRegistry.MAGIC_RESISTANCE.get());
            if (magicResistance != null && magicResistance.getValue() > 0) {
                amount *= applyMagicResistance(magicResistance.getValue());
            }
            event.setAmount(amount);
        }
        if (source.getEntity() instanceof LivingEntity attacker) {
            float amount = event.getAmount();
            ItemStack stack = attacker.getMainHandItem();

            if (source.getDirectEntity() instanceof ScytheBoomerangEntity) {
                stack = ((ScytheBoomerangEntity) source.getDirectEntity()).scythe;
            }
            if (!source.isMagic()) {
                AttributeInstance magicDamage = attacker.getAttribute(AttributeRegistry.MAGIC_DAMAGE.get());
                if (magicDamage != null) {
                    if (magicDamage.getValue() > 0 && target.isAlive()) {
                        target.invulnerableTime = 0;
                        target.hurt(DamageSourceRegistry.causeVoodooDamage(attacker), (float) magicDamage.getValue());
                    }
                }
            }
            if (source instanceof EntityDamageSource entityDamageSource) {
                if (entityDamageSource.isThorns()) {
                    return;
                }
            }
            if (stack.getItem() instanceof ModScytheItem) {
                AttributeInstance scytheProficiency = attacker.getAttribute(AttributeRegistry.SCYTHE_PROFICIENCY.get());
                if (scytheProficiency != null && scytheProficiency.getValue() > 0) {
                    event.setAmount((float) (amount + scytheProficiency.getValue() * 0.5f));
                }
            }
        }
    }

    public static double applyMagicResistance(double originalDamage) {
        return ((1 - (0.5 * (1 / (0.6 * originalDamage)))) * 0.6);
    }
}