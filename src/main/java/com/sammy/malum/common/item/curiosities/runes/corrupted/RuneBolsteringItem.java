package com.sammy.malum.common.item.curiosities.runes.corrupted;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.runes.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class RuneBolsteringItem extends MalumRuneCurioItem {

    public RuneBolsteringItem(Properties builder) {
        super(builder, SpiritTypeRegistry.SACRED_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.MAX_HEALTH, uuid -> new AttributeModifier(uuid,
                "Curio Max Health", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}