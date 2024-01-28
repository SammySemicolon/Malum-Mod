package com.sammy.malum.core.systems.item;

import com.google.common.collect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import vectorwing.farmersdelight.common.item.*;

public class MalumKnifeItem extends KnifeItem {
    private Multimap<Attribute, AttributeModifier> attributes;

    public MalumKnifeItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn - 2f, properties);
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