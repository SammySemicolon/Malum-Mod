package com.sammy.malum.common.item.curiosities.curios.spirit;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.setup.LodestoneAttributeRegistry;
import top.theillusivec4.curios.api.*;

import java.util.UUID;

public class CurioMirrorNecklace extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioMirrorNecklace(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.ARCANE_RESONANCE.get(), uuid -> new AttributeModifier(uuid,
                "Curio Arcane Resonance", 1f, AttributeModifier.Operation.ADDITION));
        addAttributeModifier(map, LodestoneAttributeRegistry.MAGIC_RESISTANCE.get(), uuid -> new AttributeModifier(uuid,
                "Curio Magic Resistance", 1f, AttributeModifier.Operation.ADDITION));
    }
}