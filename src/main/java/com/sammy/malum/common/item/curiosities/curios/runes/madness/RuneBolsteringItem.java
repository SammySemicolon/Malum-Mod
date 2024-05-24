package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.*;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;


public class RuneBolsteringItem extends AbstractRuneCurioItem {

    public RuneBolsteringItem(Properties builder) {
        super(builder, SpiritTypeRegistry.SACRED_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.MAX_HEALTH, uuid -> new AttributeModifier(uuid,
                "Curio Max Health", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}