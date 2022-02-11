package com.sammy.malum.common.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class ModAxeItem extends AxeItem {
    private Multimap<Attribute, AttributeModifier> attributes;

    public ModAxeItem(Tier material, float damage, float speed, Properties properties) {
        super(material, damage + 6, speed - 3.2f, properties.durability(material.getUses()));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (attributes == null) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = new ImmutableMultimap.Builder<>();
            attributeBuilder.putAll(defaultModifiers);
            attributeBuilder.putAll(createExtraAttributes().build());
            attributes = attributeBuilder.build();
        }
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        return new ImmutableMultimap.Builder<>();
    }
}


