package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class RuneToughnessItem extends AbstractRuneCurioItem {

    public RuneToughnessItem(Properties builder) {
        super(builder, SpiritTypeRegistry.EARTHEN_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.ARMOR_TOUGHNESS, uuid -> new AttributeModifier(uuid,
                "Curio Armor Toughness", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}