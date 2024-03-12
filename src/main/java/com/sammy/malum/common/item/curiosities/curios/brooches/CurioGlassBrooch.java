package com.sammy.malum.common.item.curiosities.curios.brooches;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioGlassBrooch extends MalumCurioItem {

    public static final UUID GLASS_BROOCH_RUNE = UUID.fromString("975487ea-d2ad-43ce-b022-620de8185ff7");

    public CurioGlassBrooch(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        CuriosApi.addSlotModifier(map, "rune", GLASS_BROOCH_RUNE, 2, AttributeModifier.Operation.ADDITION);
        addAttributeModifier(map, Attributes.MAX_HEALTH, uuid -> new AttributeModifier(uuid,
                "Curio Max Health", -0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}