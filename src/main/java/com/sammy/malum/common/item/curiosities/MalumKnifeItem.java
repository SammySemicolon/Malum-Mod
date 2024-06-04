package com.sammy.malum.common.item.curiosities;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Tier;

public class MalumKnifeItem  {

    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        return new ImmutableMultimap.Builder<>();
    }
}