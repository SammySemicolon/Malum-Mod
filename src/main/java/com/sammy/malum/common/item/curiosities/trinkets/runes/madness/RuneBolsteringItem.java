package com.sammy.malum.common.item.curiosities.trinkets.runes.madness;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.trinkets.runes.AbstractRuneTrinketsItem;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;


public class RuneBolsteringItem extends AbstractRuneTrinketsItem {

    public RuneBolsteringItem(Properties builder) {
        super(builder, SpiritTypeRegistry.SACRED_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack, LivingEntity entity) {
        addAttributeModifier(map, Attributes.MAX_HEALTH, uuid -> new AttributeModifier(uuid,
                "Curio Max Health", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}