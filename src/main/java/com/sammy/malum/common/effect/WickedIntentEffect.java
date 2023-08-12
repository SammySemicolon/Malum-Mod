package com.sammy.malum.common.effect;

import com.sammy.malum.common.item.curiosities.weapons.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.level.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;

public class WickedIntentEffect extends MobEffect {
    public WickedIntentEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(88, 86, 60));
        addAttributeModifier(AttributeRegistry.SCYTHE_PROFICIENCY.get(), "0cd21cec-758c-456b-9955-06713e732303", 4f, AttributeModifier.Operation.ADDITION);
    }

    public static void removeWickedIntent(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (source.isMagic() || (source instanceof EntityDamageSource entityDamageSource && entityDamageSource.isThorns())) {
            return;
        }
        if (source.getEntity() instanceof LivingEntity livingEntity) {
            if (MalumScytheItem.getScytheItemStack(source, livingEntity).isEmpty()) {
                return;
            }
            MobEffectInstance effect = livingEntity.getEffect(MobEffectRegistry.WICKED_INTENT.get());
            if (effect != null) {
                Level level = livingEntity.level;
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