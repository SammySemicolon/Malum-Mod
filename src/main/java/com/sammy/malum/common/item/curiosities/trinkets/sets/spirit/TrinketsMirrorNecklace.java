package com.sammy.malum.common.item.curiosities.trinkets.sets.spirit;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;

public class TrinketsMirrorNecklace extends MalumTinketsItem implements IMalumEventResponderItem {
    public TrinketsMirrorNecklace(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack, LivingEntity entity) {
        addAttributeModifier(map, AttributeRegistry.ARCANE_RESONANCE.get(), uuid -> new AttributeModifier(uuid,
                "Curio Arcane Resonance", 1f, AttributeModifier.Operation.ADDITION));
    }
}