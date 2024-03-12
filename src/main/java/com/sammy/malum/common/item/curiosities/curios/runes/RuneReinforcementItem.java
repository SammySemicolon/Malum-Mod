package com.sammy.malum.common.item.curiosities.curios.runes;

import com.google.common.collect.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class RuneReinforcementItem extends MalumRuneCurioItem {

    public RuneReinforcementItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAP.get(), uuid -> new AttributeModifier(uuid,
                "Curio Soul Ward Capacity", 6f, AttributeModifier.Operation.ADDITION));
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_STRENGTH.get(), uuid -> new AttributeModifier(uuid,
                "Curio Soul Ward Strength", 1f, AttributeModifier.Operation.ADDITION));
    }
}