package com.sammy.malum.common.item.curiosities.curios.runes;

import com.google.common.collect.Multimap;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class RuneDexterityItem extends MalumRuneCurioItem {

    public RuneDexterityItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AERIAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("low_health_speed"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.MOVEMENT_SPEED, uuid -> new AttributeModifier(uuid,
                "Curio Movement Speed", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL) {
            @Override
            public double getAmount() {
                double amount = super.getAmount();
                final LivingEntity livingEntity = slotContext.entity();
                if (livingEntity != null) {
                    float health = livingEntity.getHealth();
                    float maxHealth = livingEntity.getMaxHealth();
                    float pct = health / maxHealth;
                    return amount * (2 - pct);
                }
                return amount;
            }
        });
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.level().getGameTime() % 5L == 0) {
            AttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null) {
                attribute.setDirty();
            }
        }
    }
}
