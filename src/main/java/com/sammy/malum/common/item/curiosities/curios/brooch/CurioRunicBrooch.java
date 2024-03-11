package com.sammy.malum.common.item.curiosities.curios.brooch;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioRunicBrooch extends MalumCurioItem {

    public static final UUID RUNIC_BROOCH_RING = UUID.fromString("2a088a9a-dab8-42e4-9d6c-a04b34be6abf");
    public static final UUID RUNIC_BROOCH_RUNE = UUID.fromString("17d663c4-a8e0-472a-b444-f2907e1e3e92");

    public CurioRunicBrooch(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        CuriosApi.addSlotModifier(map, "ring", RUNIC_BROOCH_RING, -1, AttributeModifier.Operation.ADDITION);
        CuriosApi.addSlotModifier(map, "rune", RUNIC_BROOCH_RUNE, 2, AttributeModifier.Operation.ADDITION);
    }
}