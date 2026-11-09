package com.sammy.malum.common.geas.pact.infernal;

import com.sammy.malum.common.data.attachment.abilities.AvariceData;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.network.chat.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.level.*;

import java.util.function.*;

import static com.sammy.malum.common.item.nucleus.WindNucleusItem.getExplosionAffectedEntities;

public class BlastweaverGeas extends GeasEffect {

    public BlastweaverGeas() {
        super(MalumGeasEffectTypes.PACT_OF_THE_BLASTWEAVER.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("vastly_bigger_explosions"));
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("explosion_lover"));
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("avarice_explosions"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("avarice_vulnerability"));
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker != null && event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
            var effect = attacker.getEffect(MalumMobEffects.AVARICE);
            if (effect != null) {
                float modifier = 1 + (effect.getAmplifier() + 1) * 0.2f;
                event.setNewDamage(event.getNewDamage() * modifier);
            }
        }
    }

    public static void processExplosion(ExplosionEvent.Detonate event) {
        var explosion = event.getExplosion();
        var center = explosion.center();
        float radius = explosion.radius() * 20;
        var explosionAffectedEntities = getExplosionAffectedEntities(event.getLevel(), null, center, radius);
        for (Entity entity : explosionAffectedEntities) {
            if (entity instanceof Player player) {
                if (GeasEffectHandler.hasGeasEffect(player, MalumGeasEffectTypes.PACT_OF_THE_BLASTWEAVER)) {
                    AvariceData.applyAvarice(player, 2);
                }
            }
        }
    }

    public static float increaseExplosionRadius(LivingEntity source, float original) {
        if (source != null && GeasEffectHandler.hasGeasEffect(source, MalumGeasEffectTypes.PACT_OF_THE_BLASTWEAVER)) {
            float bonus = 2;
            var effect = source.getEffect(MalumMobEffects.AVARICE);
            if (effect != null) {
                bonus += (effect.getAmplifier() + 1) * 0.1f;
            }
            return original + bonus;
        }
        return original;
    }
}