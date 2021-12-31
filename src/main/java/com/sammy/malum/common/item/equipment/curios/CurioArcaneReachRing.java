package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CurioArcaneReachRing extends MalumCurioItem
{
    private static final UUID ATTRIBUTE_UUID = UUID.fromString("f792e379-4dce-4387-bd3a-099cd49b15f4");
    public CurioArcaneReachRing(Properties builder)
    {
        super(builder);
    }

    @Override
    public boolean isGilded()
    {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(AttributeRegistry.SPIRIT_REACH, new AttributeModifier(ATTRIBUTE_UUID, "Curio spirit reach", 8f, AttributeModifier.Operation.ADDITION));
        return map;
    }
}