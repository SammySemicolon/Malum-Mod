package com.sammy.malum.common.geas.pact.aqueous;

import com.google.common.collect.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.visual_effects.networked.MalumNetworkedParticleEffectColorData;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;

import javax.annotation.Nullable;
import java.util.function.*;

public class HighPriestGeas extends GeasEffect {

    private static final int COOLDOWN_DURATION = 600;

    public boolean isActive = false;

    public HighPriestGeas() {
        super(MalumGeasEffectTypes.PACT_OF_THE_HIGH_PRIEST.get());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        if (isActive) {
            addAttributeModifier(modifiers, Attributes.ENTITY_INTERACTION_RANGE, 0.5f, AttributeModifier.Operation.ADD_VALUE);
            addAttributeModifier(modifiers, Attributes.BLOCK_INTERACTION_RANGE, 2f, AttributeModifier.Operation.ADD_VALUE);
        }
        return modifiers;
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("bonus_reach"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("fragile_reach"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("fragile_reach_slowdown"));
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        boolean hasEffect = entity.hasEffect(MalumMobEffects.SHAKEN_FAITH);
        if (isActive == hasEffect) {
            isActive = !isActive;
            setDirty();
        }
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, @Nullable LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (target.level() instanceof ServerLevel level) {
            var source = event.getSource();
            if (source.getDirectEntity() != null && source.getEntity() != null) {
                if (!isActive) {
                    return;
                }
            }
            MalumParticleEffectTypes.HIGH_PRIEST_PENANCE.createEffect(target)
                    .color(new MalumNetworkedParticleEffectColorData(MalumSpiritTypes.AQUEOUS_SPIRIT, MalumSpiritTypes.ELDRITCH_SPIRIT))
                    .spawn(level);
            target.addEffect(new MobEffectInstance(MalumMobEffects.SHAKEN_FAITH, COOLDOWN_DURATION, 0, true, true, true));
        }
    }
}