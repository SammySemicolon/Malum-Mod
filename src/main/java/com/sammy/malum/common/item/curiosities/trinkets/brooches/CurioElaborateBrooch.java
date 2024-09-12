package com.sammy.malum.common.item.curiosities.trinkets.brooches;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CurioElaborateBrooch extends MalumTinketsItem {

    public static final UUID ELABORATE_BROOCH_NECKLACE = UUID.fromString("73313da6-1b56-44cb-ac80-1f043a713bb4");
    public static final UUID ELABORATE_BROOCH_BELT = UUID.fromString("7b76b980-f2b7-42e1-a528-b2610048db53");

    public CurioElaborateBrooch(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack, LivingEntity entity) {
        SlotAttributes.addSlotModifier(map, "legs/belt", ELABORATE_BROOCH_BELT, 1, AttributeModifier.Operation.ADDITION);
        SlotAttributes.addSlotModifier(map, "chest/necklace", ELABORATE_BROOCH_NECKLACE, -1, AttributeModifier.Operation.ADDITION);
    }
}
