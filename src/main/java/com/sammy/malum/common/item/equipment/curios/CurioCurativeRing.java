package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CurioCurativeRing extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioCurativeRing(Properties builder) {
        super(builder);
    }

    @Override
    public boolean isGilded() {
        return true;
    }

    @Override
    public void pickupSpirit(LivingEntity collector, ItemStack stack, double arcaneResonance) {
        collector.heal(collector.getMaxHealth() * 0.1f+(float)(arcaneResonance*0.05f));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.MAX_HEALTH, new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Curio health boost", 4f, AttributeModifier.Operation.ADDITION));
        return map;
    }
}