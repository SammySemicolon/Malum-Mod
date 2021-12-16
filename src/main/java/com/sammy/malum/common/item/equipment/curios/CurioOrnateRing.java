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

public class CurioOrnateRing extends MalumCurioItem
{
    private static final UUID ARMOR = UUID.fromString("335abd8a-0e66-4b11-aa33-4aee88f0f614");
    public CurioOrnateRing(Properties builder)
    {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ARMOR, "Curio armor toughness", 2f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isOrnate()
    {
        return true;
    }
}