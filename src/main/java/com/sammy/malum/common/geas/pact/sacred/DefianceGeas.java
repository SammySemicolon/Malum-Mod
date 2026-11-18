package com.sammy.malum.common.geas.pact.sacred;

import com.google.common.collect.Multimap;
import com.sammy.malum.core.handlers.GeasEffectHandler;
import com.sammy.malum.core.helpers.TooltipComponentHelper;
import com.sammy.malum.core.systems.geas.GeasEffect;
import com.sammy.malum.registry.common.magic.MalumGeasEffectTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.TooltipFlag;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;

import java.util.function.Consumer;

public class DefianceGeas extends GeasEffect {

    public DefianceGeas() {
        super(MalumGeasEffectTypes.PACT_OF_DEFIANCE.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("faster_natural_healing"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, WaywardAttributeTypes.HEALING_MULTIPLIER, 0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(modifiers, Attributes.MAX_HEALTH, -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return modifiers;
    }

    public static int accelerateNaturalHealing(LivingEntity entity) {
        if (GeasEffectHandler.hasGeas(entity, MalumGeasEffectTypes.PACT_OF_DEFIANCE)) {
            return entity.getHealth() < entity.getMaxHealth()/4f ? 2 : 1;
        }
        return 0;
    }
}
