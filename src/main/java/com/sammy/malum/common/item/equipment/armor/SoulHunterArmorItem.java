package com.sammy.malum.common.item.equipment.armor;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

import static com.sammy.malum.core.registry.item.ArmorTiers.ArmorTierEnum.SPIRIT_HUNTER;

public class SoulHunterArmorItem extends MalumArmorItem {
    public SoulHunterArmorItem(EquipmentSlot slot, Properties builder) {
        super(SPIRIT_HUNTER, slot, builder, createExtraAttributes(slot));
    }
    public static ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
        builder.put(AttributeRegistry.MAGIC_PROFICIENCY, new AttributeModifier(uuid, "Magic Proficiency", 1f, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.SCYTHE_PROFICIENCY, new AttributeModifier(uuid, "Scythe Proficiency", 1f, AttributeModifier.Operation.ADDITION));
        return builder;
    }
    public String getTexture() {
        return "spirit_hunter_armor";
    }
}