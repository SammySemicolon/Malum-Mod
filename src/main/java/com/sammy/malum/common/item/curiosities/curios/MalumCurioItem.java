package com.sammy.malum.common.item.curiosities.curios;

import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MalumCurioItem extends Item implements ICurioItem {

    public Map<Integer, UUID> uuids = new HashMap<>();

    public MalumCurioItem(Properties properties) {
        super(properties);
    }

    public boolean isGilded() {
        return false;
    }

    public boolean isOrnate() {
        return false;
    }

    @Override
    public void playRightClickEquipSound(LivingEntity livingEntity, ItemStack stack) {
        if (isGilded()) {
            livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundRegistry.HOLY_EQUIP.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        if (isOrnate()) {
            livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundRegistry.SINISTER_EQUIP.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        Map<Enchantment, Integer> list = EnchantmentHelper.getEnchantments(book);

        if (list.size() == 1 && list.containsKey(Enchantments.BINDING_CURSE))
            return true;
        else
            return super.isBookEnchantable(stack, book);
    }

    @Override
    public boolean canRightClickEquip(ItemStack stack) {
        return true;
    }
}