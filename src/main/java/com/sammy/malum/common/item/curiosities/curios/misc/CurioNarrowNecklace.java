package com.sammy.malum.common.item.curiosities.curios.misc;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.*;

import java.util.UUID;

public class CurioNarrowNecklace extends MalumCurioItem
{
    public CurioNarrowNecklace(Properties builder)
    {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SCYTHE_PROFICIENCY.get(), uuid -> new AttributeModifier(uuid,
                "Curio Scythe Proficiency", 4f, AttributeModifier.Operation.ADDITION));
    }
}