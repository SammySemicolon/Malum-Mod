package com.sammy.malum.common.item.curiosities.curios;

import com.google.common.collect.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import java.util.*;
import java.util.function.*;

public abstract class AbstractMalumCurioItem extends Item implements ICurioItem {

    public enum MalumTrinketType {
        CLOTH(SoundRegistry.CLOTH_TRINKET_EQUIP),
        ORNATE(SoundRegistry.ORNATE_TRINKET_EQUIP),
        GILDED(SoundRegistry.GILDED_TRINKET_EQUIP),
        ALCHEMICAL(SoundRegistry.ALCHEMICAL_TRINKET_EQUIP),
        ROTTEN(SoundRegistry.ROTTEN_TRINKET_EQUIP),
        METALLIC(SoundRegistry.METALLIC_TRINKET_EQUIP),
        RUNE(SoundRegistry.ORNATE_TRINKET_EQUIP),
        VOID(SoundRegistry.VOID_TRINKET_EQUIP);
        final Supplier<SoundEvent> sound;

        MalumTrinketType(Supplier<SoundEvent> sound) {
            this.sound = sound;
        }
    }

    private final Function<Attribute, UUID> uuids = Util.memoize(a -> UUID.randomUUID());
    public final MalumTrinketType type;

    public AbstractMalumCurioItem(Properties properties, MalumTrinketType type) {
        super(properties);
        this.type = type;
    }

    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
    }

    @Override
    public final Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = LinkedHashMultimap.create();
        addAttributeModifiers(map, slotContext, stack);
        return map;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.entity().level().playSound(null, slotContext.entity().blockPosition(), type.sound.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
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
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public void addAttributeModifier(Multimap<Attribute, AttributeModifier> map, Attribute attribute, Function<UUID, AttributeModifier> attributeModifier) {
        map.put(attribute, attributeModifier.apply(uuids.apply(attribute)));
    }
}