package com.sammy.malum.common.item.curiosities.curios.spirit;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class CurioArcaneSpoilRing extends MalumCurioItem {

    public CurioArcaneSpoilRing(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SPIRIT_SPOILS.get(), uuid -> new AttributeModifier(uuid,
                "Curio Spirit Spoils", 1f, AttributeModifier.Operation.ADDITION));
    }
}