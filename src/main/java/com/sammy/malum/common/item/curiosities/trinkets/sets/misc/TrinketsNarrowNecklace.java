package com.sammy.malum.common.item.curiosities.trinkets.sets.misc;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class TrinketsNarrowNecklace extends MalumTinketsItem {
    public TrinketsNarrowNecklace(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(negativeEffect("no_sweep"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotReference, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SCYTHE_PROFICIENCY.get(), uuid -> new AttributeModifier(uuid,
                "Curio Scythe Proficiency", 4f, AttributeModifier.Operation.ADDITION));
    }
}
