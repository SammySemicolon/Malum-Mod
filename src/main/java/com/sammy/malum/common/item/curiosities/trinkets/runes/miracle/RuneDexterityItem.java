package com.sammy.malum.common.item.curiosities.trinkets.runes.miracle;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.trinkets.runes.AbstractRuneTrinketsItem;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class RuneDexterityItem extends AbstractRuneTrinketsItem {

    public RuneDexterityItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AERIAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("low_health_speed"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack, LivingEntity entity) {
        addAttributeModifier(map, Attributes.MOVEMENT_SPEED, uuid -> new AttributeModifier(uuid,
                "Curio Movement Speed", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL) {
            @Override
            public double getAmount() {
                double amount = super.getAmount();
                final LivingEntity livingEntity = slotContext.inventory().getComponent().getEntity();
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
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        LivingEntity livingEntity = slot.inventory().getComponent().getEntity();
        if (livingEntity.level().getGameTime() % 5L == 0) {
            AttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null) {
                attribute.setDirty();
            }
        }
    }
}
