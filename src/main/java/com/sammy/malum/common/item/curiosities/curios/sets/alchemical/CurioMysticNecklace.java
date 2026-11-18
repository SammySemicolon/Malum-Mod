package com.sammy.malum.common.item.curiosities.curios.sets.alchemical;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class CurioMysticNecklace extends MalumCurioItem implements IMalumEventResponderBody {
    public CurioMysticNecklace(Properties builder) {
        super(builder, MalumTrinketFamily.GILDED);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, MalumAttributes.ARCANE_RESONANCE,
                new AttributeModifier(MalumMod.malumPath("mirror_necklace"), 1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
}