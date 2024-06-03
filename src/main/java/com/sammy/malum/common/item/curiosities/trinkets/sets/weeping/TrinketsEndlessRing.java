package com.sammy.malum.common.item.curiosities.trinkets.sets.weeping;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class TrinketsEndlessRing extends MalumTinketsItem implements IVoidItem {
    public TrinketsEndlessRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack, LivingEntity entity) {
        addAttributeModifier(map, AttributeRegistry.RESERVE_STAFF_CHARGES.get(), uuid -> new AttributeModifier(uuid,
                "Curio Reserve Staff Charges", 3f, AttributeModifier.Operation.ADDITION));
    }
}