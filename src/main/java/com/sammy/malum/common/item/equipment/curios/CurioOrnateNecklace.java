package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;

import java.util.UUID;

import net.minecraft.item.Item.Properties;

public class CurioOrnateNecklace extends MalumCurioItem
{
    private static final UUID ATTRIBUTE_UUID = UUID.fromString("bf622288-b96f-4219-8623-9a6f3f60850d");
    public CurioOrnateNecklace(Properties builder)
    {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ATTRIBUTE_UUID, "Curio armor toughness", 4f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isOrnate()
    {
        return true;
    }

}