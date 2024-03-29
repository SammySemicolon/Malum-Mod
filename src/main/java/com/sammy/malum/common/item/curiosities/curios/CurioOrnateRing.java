package com.sammy.malum.common.item.curiosities.curios;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class CurioOrnateRing extends MalumCurioItem {
    public CurioOrnateRing(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.ARMOR_TOUGHNESS, uuid -> new AttributeModifier(uuid,
                "Curio Armor Toughness", 1f, AttributeModifier.Operation.ADDITION));
    }
}