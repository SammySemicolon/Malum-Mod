package com.sammy.malum.core.handlers;

import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.tools.ModScytheItem;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.damage.DamageSourceRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AttributeEventHandler {
    public static void processAttributes(LivingHurtEvent event) {
        if (event.isCanceled() || event.getAmount() == 0) {
            return;
        }
        DamageSource source = event.getSource();
        LivingEntity target = event.getEntityLiving();
        if (source.isMagic()) {
            float amount = event.getAmount();
            if (source.getEntity() instanceof LivingEntity attacker) {
                float proficiency = (float) attacker.getAttributeValue(AttributeRegistry.MAGIC_PROFICIENCY.get());
                amount *= 1 * Math.exp(0.075f * proficiency);
            }
            float resistance = (float) target.getAttributeValue(AttributeRegistry.MAGIC_RESISTANCE.get());
            event.setAmount((float) (amount * 1 * Math.exp(-0.15f * resistance)));
        }
        if (source.getEntity() instanceof LivingEntity attacker) {
            float amount = event.getAmount();
            ItemStack stack = attacker.getMainHandItem();
            if (source.getDirectEntity() instanceof ScytheBoomerangEntity) {
                stack = ((ScytheBoomerangEntity) source.getDirectEntity()).scythe;
            }
            if (!source.isMagic()) {
                float damage = (float) attacker.getAttributeValue(AttributeRegistry.MAGIC_DAMAGE.get());
                if (damage > 0 && target.isAlive()) {
                    target.invulnerableTime = 0;
                    target.hurt(DamageSourceRegistry.causeVoodooDamage(attacker), damage);
                }
            }
            if (source instanceof EntityDamageSource entityDamageSource) {
                if (entityDamageSource.isThorns()) {
                    return;
                }
            }
            if (stack.getItem() instanceof ModScytheItem) {
                float proficiency = (float) attacker.getAttributeValue(AttributeRegistry.SCYTHE_PROFICIENCY.get());
                event.setAmount(amount + proficiency * 0.5f);
            }
        }
    }
}