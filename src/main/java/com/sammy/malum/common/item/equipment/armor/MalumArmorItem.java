package com.sammy.malum.common.item.equipment.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class MalumArmorItem extends ArmorItem implements IEventResponderItem {
    protected LazyLoadedValue<Object> model;
    private Multimap<Attribute, AttributeModifier> attributes;

    public MalumArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder, ImmutableMultimap.Builder<Attribute, AttributeModifier> extraAttributes) {
        super(materialIn, slot, builder);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = new ImmutableMultimap.Builder<>();
        attributeBuilder.putAll(extraAttributes.build());
        attributes = attributeBuilder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == this.slot ? this.attributes : ImmutableMultimap.of();
    }

    public String getTexture() {
        return null;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "malum:textures/armor/" + getTexture() + ".png";
    }
}