package com.sammy.malum.common.item.curiosities.curios.brooches;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioElaborateBrooch extends MalumCurioItem {

    public static final UUID ELABORATE_BROOCH_NECKLACE = UUID.fromString("73313da6-1b56-44cb-ac80-1f043a713bb4");
    public static final UUID ELABORATE_BROOCH_BELT = UUID.fromString("7b76b980-f2b7-42e1-a528-b2610048db53");

    public CurioElaborateBrooch(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        CuriosApi.addSlotModifier(map, "belt", ELABORATE_BROOCH_BELT, 1, AttributeModifier.Operation.ADDITION);
        CuriosApi.addSlotModifier(map, "necklace", ELABORATE_BROOCH_NECKLACE, -1, AttributeModifier.Operation.ADDITION);
    }
}
