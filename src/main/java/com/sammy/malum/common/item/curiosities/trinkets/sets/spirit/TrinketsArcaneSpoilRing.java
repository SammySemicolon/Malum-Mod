package com.sammy.malum.common.item.curiosities.trinkets.sets.spirit;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class TrinketsArcaneSpoilRing extends MalumTinketsItem {

    public TrinketsArcaneSpoilRing(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SPIRIT_SPOILS.get(), uuid -> new AttributeModifier(uuid,
                "Curio Spirit Spoils", 1f, AttributeModifier.Operation.ADDITION));
    }
}