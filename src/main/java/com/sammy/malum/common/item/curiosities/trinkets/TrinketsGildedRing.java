package com.sammy.malum.common.item.curiosities.trinkets;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public class TrinketsGildedRing extends MalumTinketsItem {
    public TrinketsGildedRing(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.ARMOR, uuid -> new AttributeModifier(uuid,
                "Curio Armor", 1f, AttributeModifier.Operation.ADDITION));
    }
}