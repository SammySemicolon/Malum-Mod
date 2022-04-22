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

public class CurioGildedBelt extends MalumCurioItem
{

    public CurioGildedBelt(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR, new AttributeModifier(UUID.randomUUID(), "Curio armor boost", 4, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isGilded()
    {
        return true;
    }
}