package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CurioArcaneSpoilRing extends MalumCurioItem {
    private static final UUID ATTRIBUTE_UUID = UUID.fromString("d214b8f2-d908-4435-9845-61656adff7d1");
    public CurioArcaneSpoilRing(Properties builder) {
        super(builder);
    }

    @Override
    public boolean isOrnate() {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(AttributeRegistry.SPIRIT_SPOILS, new AttributeModifier(ATTRIBUTE_UUID, "Curio spirit spoils", 1f, AttributeModifier.Operation.ADDITION));
        return map;
    }
}