package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

import net.minecraft.item.Item.Properties;

public class CurioNarrowNecklace extends MalumCurioItem
{
    private static final UUID ATTRIBUTE_UUID = UUID.fromString("2b21fa84-f9f2-4fa0-a225-0997206ee54b");
    public CurioNarrowNecklace(Properties builder)
    {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(AttributeRegistry.SCYTHE_PROFICIENCY, new AttributeModifier(ATTRIBUTE_UUID, "Curio scythe proficiency", 4f, AttributeModifier.Operation.ADDITION));
        return map;
    }
    @Override
    public boolean isOrnate()
    {
        return true;
    }

}