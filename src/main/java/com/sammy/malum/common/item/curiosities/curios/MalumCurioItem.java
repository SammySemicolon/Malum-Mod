package com.sammy.malum.common.item.curiosities.curios;

import com.google.common.collect.*;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Map;
import java.util.UUID;
import java.util.function.*;

public class MalumCurioItem extends Item implements ICurioItem {

    public enum MalumTrinketType {
        CLOTH(SoundRegistry.CLOTH_TRINKET_EQUIP),
        ORNATE(SoundRegistry.ORNATE_TRINKET_EQUIP),
        GILDED(SoundRegistry.GILDED_TRINKET_EQUIP),
        ALCHEMICAL(SoundRegistry.ALCHEMICAL_TRINKET_EQUIP),
        ROTTEN(SoundRegistry.ROTTEN_TRINKET_EQUIP),
        METALLIC(SoundRegistry.METALLIC_TRINKET_EQUIP),
        VOID(SoundRegistry.VOID_TRINKET_EQUIP);
        final Supplier<SoundEvent> sound;

        MalumTrinketType(Supplier<SoundEvent> sound) {
            this.sound = sound;
        }
    }

    private final Function<Attribute, UUID> uuids = Util.memoize(a -> UUID.randomUUID());
    public final MalumTrinketType type;

    public MalumCurioItem(Properties properties, MalumTrinketType type) {
        super(properties);
        this.type = type;
    }

    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
    }

    @Override
    public final Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        addAttributeModifiers(map, slotContext, stack);
        return map;
    }

    @Override
    public void playRightClickEquipSound(LivingEntity livingEntity, ItemStack stack) {
        livingEntity.level.playSound(null, livingEntity.blockPosition(), type.sound.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        Map<Enchantment, Integer> list = EnchantmentHelper.getEnchantments(book);
        if (list.size() == 1 && list.containsKey(Enchantments.BINDING_CURSE)) {
            return true;
        }
        return super.isBookEnchantable(stack, book);
    }

    @Override
    public boolean canRightClickEquip(ItemStack stack) {
        return true;
    }

    public void addAttributeModifier(Multimap<Attribute, AttributeModifier> map, Attribute attribute, Function<UUID, AttributeModifier> attributeModifier) {
        map.put(attribute, attributeModifier.apply(uuids.apply(attribute)));
    }
}