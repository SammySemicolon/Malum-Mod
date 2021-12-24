package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.registry.misc.SoundRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

import static top.theillusivec4.curios.api.type.capability.ICurio.DropRule.ALWAYS_KEEP;

public class CurioTokenOfGratitude extends MalumCurioItem {
    public static final String SAMMY = "0ca54301-6170-4c44-b3e0-b8afa6b81ed2";

    public CurioTokenOfGratitude(Properties builder) {
        super(builder);
    }

    @Override
    public void playRightClickEquipSound(LivingEntity livingEntity, ItemStack stack) {
        livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundRegistry.SINISTER_EQUIP, SoundSource.NEUTRAL, 1.0f, 1.0f);
        livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundRegistry.HOLY_EQUIP, SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    @Nonnull
    @Override
    public ICurio.DropRule getDropRule(LivingEntity livingEntity, ItemStack stack) {
        return ALWAYS_KEEP;
    }
}