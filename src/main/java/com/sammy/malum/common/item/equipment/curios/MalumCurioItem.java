package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.setup.SoundRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundSource;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class MalumCurioItem extends Item implements ICurioItem {
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
            livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundRegistry.HOLY_EQUIP, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        if (isOrnate()) {
            livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundRegistry.SINISTER_EQUIP, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean canRightClickEquip(ItemStack stack) {
        return true;
    }
}