package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class CurioEndlessRing extends MalumCurioItem implements IVoidItem {
    public CurioEndlessRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.RESERVE_STAFF_CHARGES.get(), uuid -> new AttributeModifier(uuid,
                "Curio Reserve Staff Charges", 3f, AttributeModifier.Operation.ADDITION));
    }
}