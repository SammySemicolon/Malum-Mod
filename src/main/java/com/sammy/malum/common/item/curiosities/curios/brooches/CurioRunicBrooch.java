package com.sammy.malum.common.item.curiosities.curios.brooches;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CurioRunicBrooch extends MalumCurioItem {

    public static final UUID RUNIC_BROOCH_RING = UUID.fromString("2a088a9a-dab8-42e4-9d6c-a04b34be6abf");
    public static final UUID RUNIC_BROOCH_RUNE = UUID.fromString("17d663c4-a8e0-472a-b444-f2907e1e3e92");

    public CurioRunicBrooch(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack) {
        //TODO CuriosApi.addSlotModifier(map, "rune", RUNIC_BROOCH_RUNE, 2, AttributeModifier.Operation.ADDITION);
        //TODO CuriosApi.addSlotModifier(map, "ring", RUNIC_BROOCH_RING, -1, AttributeModifier.Operation.ADDITION);
    }
}