package com.sammy.malum.common.spiritrite;

import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.core.systems.rites.PotionRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.AERIAL_SPIRIT;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class AerialRiteType extends MalumRiteType {
    public AerialRiteType() {
        super("aerial_rite", ARCANE_SPIRIT.get(), AERIAL_SPIRIT.get(), AERIAL_SPIRIT.get());
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.ZEPHYRS_COURAGE);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.AETHERS_CHARM);
    }

    @Override
    public MalumRiteType setRegistryName(ResourceLocation name) {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return null;
    }

    @Override
    public Class<MalumRiteType> getRegistryType() {
        return null;
    }
}