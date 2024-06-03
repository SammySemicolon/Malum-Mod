package com.sammy.malum.common.item.curiosities.trinkets.brooches;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CurioGlassBrooch extends MalumTinketsItem {

    public static final UUID GLASS_BROOCH_RUNE = UUID.fromString("975487ea-d2ad-43ce-b022-620de8185ff7");

    public CurioGlassBrooch(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack, LivingEntity entity) {
        SlotAttributes.addSlotModifier(map, "rune", GLASS_BROOCH_RUNE, 2, AttributeModifier.Operation.ADDITION);
        addAttributeModifier(map, Attributes.MAX_HEALTH, uuid -> new AttributeModifier(uuid,
                "Curio Max Health", -0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}