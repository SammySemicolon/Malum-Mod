package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.ortus.setup.OrtusAttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CurioWardedBelt extends MalumCurioItem {

    public CurioWardedBelt(Properties builder) {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR, new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Curio armor boost", 2, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuids.computeIfAbsent(1, (i) -> UUID.randomUUID()), "Curio armor toughness boost", 1, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuids.computeIfAbsent(2, (i) -> UUID.randomUUID()), "Curio knockback resistance", 0.2f, AttributeModifier.Operation.ADDITION));
        map.put(OrtusAttributeRegistry.MAGIC_RESISTANCE.get(), new AttributeModifier(uuids.computeIfAbsent(3, (i) -> UUID.randomUUID()), "Curio magic resistance", 1f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isGilded() {
        return true;
    }
}