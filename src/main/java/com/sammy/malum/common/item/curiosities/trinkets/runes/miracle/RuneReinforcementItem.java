package com.sammy.malum.common.item.curiosities.trinkets.runes.miracle;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.trinkets.runes.AbstractRuneTrinketsItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class RuneReinforcementItem extends AbstractRuneTrinketsItem {

    public RuneReinforcementItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack, LivingEntity entity) {
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAP.get(), uuid -> new AttributeModifier(uuid,
                "Curio Soul Ward Capacity", 6f, AttributeModifier.Operation.ADDITION));
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_STRENGTH.get(), uuid -> new AttributeModifier(uuid,
                "Curio Soul Ward Strength", 1f, AttributeModifier.Operation.ADDITION));
    }
}