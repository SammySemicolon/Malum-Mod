package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.setup.LodestoneAttributeRegistry;

import java.util.UUID;

public class CurioMirrorNecklace extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioMirrorNecklace(Properties builder) {
        super(builder);
    }

    @Override
    public boolean isGilded() {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(AttributeRegistry.ARCANE_RESONANCE.get(), new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Arcane Resonance", 1f, AttributeModifier.Operation.ADDITION));
        map.put(LodestoneAttributeRegistry.MAGIC_RESISTANCE.get(), new AttributeModifier(uuids.computeIfAbsent(1, (i) -> UUID.randomUUID()), "Magic Resistance", 1f, AttributeModifier.Operation.ADDITION));
        return map;
    }
}