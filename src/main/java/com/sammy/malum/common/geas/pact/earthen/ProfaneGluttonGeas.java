package com.sammy.malum.common.geas.pact.earthen;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;

import javax.annotation.Nullable;
import java.util.function.*;

public class ProfaneGluttonGeas extends GeasEffect {

    public ProfaneGluttonGeas() {
        super(MalumGeasEffectTypes.PACT_OF_THE_PROFANE_GLUTTON.get());
    }

    @Override
    public void modifyGluttonyPropertiesEvent(ModifyGluttonyPropertiesEvent event, LivingEntity collector) {
        event.getProperties()
                .scaleInitialAmplifier(2)
                .scaleAmplifierGain(2)
                .scaleAmplifierLimit(2)
                .replaceEffectType(MalumMobEffects.DESPERATE_NEED);
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("desperate_need"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("desperate_need_vulnerability"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("desperate_need_poison"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, @Nullable LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var effect = target.getEffect(MalumMobEffects.DESPERATE_NEED);
        if (effect == null) {
            return;
        }
        float pct = 1 + (effect.getAmplifier()+1) * 0.05f;
        if (event.getSource().is(NeoForgeMod.POISON_DAMAGE)) {
            pct *= 2;
        }
        event.setNewDamage(event.getNewDamage() * pct);
    }

    @Override
    public void finalizedIncomingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypes.STARVE)) {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
        }
    }
}