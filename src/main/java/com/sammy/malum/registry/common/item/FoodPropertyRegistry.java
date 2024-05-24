package com.sammy.malum.registry.common.item;

import net.minecraft.world.effect.*;
import net.minecraft.world.food.*;

public class FoodPropertyRegistry {

    public static final FoodProperties RUNIC_SAP = new FoodProperties.Builder().nutrition(6).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.HEAL, 1, 0), 1).effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1).build();
    public static final FoodProperties CURSED_SAP = new FoodProperties.Builder().nutrition(6).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 0), 1).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 0), 1).build();
}
