package com.sammy.malum.common.geas.pact.aerial;

import com.google.common.collect.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;

import java.util.function.*;

public class WindsweptGeas extends GeasEffect {

    public static final int MAX_SPRINT_STACKS = 50;
    public int sprintStacks;

    public WindsweptGeas() {
        super(MalumGeasEffectTypes.PACT_OF_THE_WINDSWEPT.get());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        if (sprintStacks > 0) {
            addAttributeModifier(modifiers, Attributes.MOVEMENT_SPEED, 0.01f * sprintStacks, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
        return modifiers;
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("movement_acceleration"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("knockback_also_accelerates"));
    }

    public static void scaleKnockback(LivingKnockBackEvent event) {
        var entity = event.getEntity();
        var geas = GeasEffectHandler.getGeasEffect(entity, MalumGeasEffectTypes.PACT_OF_THE_WINDSWEPT);
        if (geas instanceof WindsweptGeas windsweptGeas) {
            if (windsweptGeas.sprintStacks <= 0) {
                return;
            }
            float knockbackStrength = Math.max(0, windsweptGeas.sprintStacks - MAX_SPRINT_STACKS/5)*0.1f;
            event.setStrength(event.getStrength() * (1+knockbackStrength));
        }
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        if (entity.level().getGameTime() % 10L == 0) {
            if (entity.isSprinting()) {
                if (sprintStacks < MAX_SPRINT_STACKS) {
                    sprintStacks++;
                }
            } else {
                if (sprintStacks > 0) {
                    sprintStacks = Math.max(0, sprintStacks - 5);
                }
            }
            setDirty();
        }
    }
}