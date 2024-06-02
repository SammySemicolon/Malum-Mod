package com.sammy.malum.common.item.curiosities.armor;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

import static com.sammy.malum.registry.common.item.ArmorTiers.ArmorTierEnum.MALIGNANT_ALLOY;

public class MalignantStrongholdArmorItem extends MalumArmorItem {

    public MalignantStrongholdArmorItem(Type slot, Properties builder) {
        super(MALIGNANT_ALLOY, slot, builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createExtraAttributes(Type type) {
        Multimap<Attribute, AttributeModifier> attributes = ArrayListMultimap.create();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
        attributes.put(AttributeRegistry.MALIGNANT_CONVERSION.get(), new AttributeModifier(uuid, "Malignant Conversion", 0.25f, AttributeModifier.Operation.ADDITION));
        return attributes;
    }

    public String getTexture() {
        return "malignant_stronghold";
    }
}