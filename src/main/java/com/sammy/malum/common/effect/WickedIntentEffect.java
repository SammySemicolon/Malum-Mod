package com.sammy.malum.common.effect;

import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.common.tag.*;

public class WickedIntentEffect extends MobEffect {
    public WickedIntentEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(88, 86, 60));
        addAttributeModifier(AttributeRegistry.SCYTHE_PROFICIENCY.get(), "0cd21cec-758c-456b-9955-06713e732303", 4f, AttributeModifier.Operation.ADDITION);
    }

    public static void removeWickedIntent(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (source.is(LodestoneDamageTypeTags.IS_MAGIC) || (source.is(DamageTypes.THORNS))) {
            return;
        }
        if (source.getEntity() instanceof LivingEntity livingEntity) {
            if (MalumScytheItem.getScytheItemStack(source, livingEntity).isEmpty()) {
                return;
            }
            MobEffectInstance effect = livingEntity.getEffect(MobEffectRegistry.WICKED_INTENT.get());
            if (effect != null) {
                Level level = livingEntity.level();
                livingEntity.removeEffect(effect.getEffect());
                level.playSound(null, livingEntity.blockPosition(), SoundRegistry.HIDDEN_BLADE_STRIKES.get(), SoundSource.PLAYERS, 2.5f, 1 + level.random.nextFloat() * 0.15f);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return false;
    }
}