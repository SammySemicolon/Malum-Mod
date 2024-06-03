package com.sammy.malum.common.item.curiosities.trinkets.sets.misc;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import dev.emi.trinkets.api.SlotReference;
import io.github.fabricators_of_create.porting_lib.attributes.PortingLibAttributes;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class TrinketsWaterNecklace extends MalumTinketsItem implements IMalumEventResponderItem {
    public TrinketsWaterNecklace(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("better_conduit_power"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slot, ItemStack stack, LivingEntity entity) {
        addAttributeModifier(map, PortingLibAttributes.SWIM_SPEED, uuid -> new AttributeModifier(uuid,
                "Curio Swim Speed", 0.15f, AttributeModifier.Operation.ADDITION) {
            @Override
            public double getAmount() {
                double amount = super.getAmount();
                if (slot.inventory().getComponent().getEntity() != null) {
                    if (slot.inventory().getComponent().getEntity().hasEffect(MobEffects.CONDUIT_POWER)) {
                        return amount * 3f;
                    }
                }
                return amount;
            }
        });
        super.addAttributeModifiers(map, slot, stack, entity);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        LivingEntity livingEntity = entity;
        if (livingEntity.level().getGameTime() % 40L == 0 && livingEntity.isSwimming()) {
            AttributeInstance attribute = livingEntity.getAttribute(PortingLibAttributes.SWIM_SPEED);
            if (attribute != null) {
                attribute.setDirty();
            }
        }

        if (livingEntity.level().getGameTime() % 20L == 0) {
            if (livingEntity.hasEffect(MobEffects.CONDUIT_POWER)) {
                livingEntity.heal(2);
            }
        }
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.hasEffect(MobEffects.CONDUIT_POWER)) {
            event.setAmount(event.getAmount() * 0.5f);
        }
    }
}
