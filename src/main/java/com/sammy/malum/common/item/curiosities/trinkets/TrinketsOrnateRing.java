package com.sammy.malum.common.item.curiosities.trinkets;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public class TrinketsOrnateRing extends MalumTinketsItem {
    public TrinketsOrnateRing(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack, LivingEntity entity) {
        addAttributeModifier(map, Attributes.ARMOR_TOUGHNESS, uuid -> new AttributeModifier(uuid,
                "Curio Armor Toughness", 1f, AttributeModifier.Operation.ADDITION));
    }
}