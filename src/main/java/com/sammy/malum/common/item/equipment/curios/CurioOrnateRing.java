package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

import net.minecraft.world.item.Item.Properties;

public class CurioOrnateRing extends MalumCurioItem
{
    public CurioOrnateRing(Properties builder)
    {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Curio armor toughness", 1f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isOrnate()
    {
        return true;
    }
}