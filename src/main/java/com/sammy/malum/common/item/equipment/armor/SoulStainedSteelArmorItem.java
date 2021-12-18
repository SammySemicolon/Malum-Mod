package com.sammy.malum.common.item.equipment.armor;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

import static com.sammy.malum.core.registry.item.ArmorTiers.ArmorTierEnum.SOUL_STAINED_STEEL;

public class SoulStainedSteelArmorItem extends MalumArmorItem {
    public SoulStainedSteelArmorItem(EquipmentSlot slot, Properties builder) {
        super(SOUL_STAINED_STEEL, slot, builder, createExtraAttributes(slot));
    }

    public static ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
        builder.put(AttributeRegistry.MAGIC_RESISTANCE, new AttributeModifier(uuid, "Magic Resistance", 0.5f, AttributeModifier.Operation.ADDITION));
        return builder;
    }

    @Override
    public void pickupSpirit(LivingEntity attacker, ItemStack stack) {
        ItemHelper.giveStackingEffect(MobEffects.DAMAGE_RESISTANCE, attacker, 25, 0);
    }

    public String getTexture() {
        return "soul_stained_steel_armor";
    }
}