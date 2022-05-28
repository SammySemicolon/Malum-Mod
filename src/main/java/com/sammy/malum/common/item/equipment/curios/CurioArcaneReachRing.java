package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CurioArcaneReachRing extends MalumCurioItem {
    public CurioArcaneReachRing(Properties builder) {
        super(builder);
    }

    @Override
    public boolean isGilded() {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(AttributeRegistry.SPIRIT_REACH.get(), new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Curio spirit reach", 8f, AttributeModifier.Operation.ADDITION));
        return map;
    }
}