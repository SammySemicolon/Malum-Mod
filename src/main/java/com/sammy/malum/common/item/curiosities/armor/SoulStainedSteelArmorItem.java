package com.sammy.malum.common.item.curiosities.armor;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;

import java.util.UUID;

import static com.sammy.malum.registry.common.item.ArmorTiers.ArmorTierEnum.SOUL_STAINED_STEEL;

public class SoulStainedSteelArmorItem extends MalumArmorItem {
    public SoulStainedSteelArmorItem(ArmorItem.Type slot, Properties builder) {
        super(SOUL_STAINED_STEEL, slot, builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createExtraAttributes(Type type) {
        Multimap<Attribute, AttributeModifier> attributes = ArrayListMultimap.create();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
        attributes.put(AttributeRegistry.SOUL_WARD_CAP.get(), new AttributeModifier(uuid, "Soul Ward Cap", 3f, AttributeModifier.Operation.ADDITION));
        attributes.put(AttributeRegistry.SOUL_WARD_RECOVERY_RATE.get(), new AttributeModifier(uuid, "Soul Ward Recovery Rate", 0.15f, AttributeModifier.Operation.MULTIPLY_BASE));
        return attributes;
    }

    public String getTexture() {
        return "soul_stained_steel_reforged";
    }
}