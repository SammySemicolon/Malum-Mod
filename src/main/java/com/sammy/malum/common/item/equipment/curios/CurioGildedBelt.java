package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class CurioGildedBelt extends MalumCurioItem
{
    private static final UUID ATTRIBUTE_UUID = UUID.fromString("0baf5041-f36d-4b33-b45f-b7d340a8fa6c");

    public CurioGildedBelt(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR, new AttributeModifier(ATTRIBUTE_UUID, "Curio armor boost", 4, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isGilded()
    {
        return true;
    }
}