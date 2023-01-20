package com.sammy.malum.common.effect;

import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.item.tools.MalumScytheItem;
import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.DamageSourceRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.potion.MalumMobEffectRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.helpers.ColorHelper;

public class RejectedEffect extends MobEffect {
    public RejectedEffect() {
        super(MobEffectCategory.NEUTRAL, ColorHelper.getColor(20, 14, 22));
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "248da214-2292-4637-a040-5597fb65db58", -0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        TouchOfDarknessHandler handler = MalumLivingEntityDataCapability.getCapability(pLivingEntity).touchOfDarknessHandler;
        handler.afflict(20);
        if (pLivingEntity.level.getGameTime() % 60L == 0) {
            pLivingEntity.hurt(new DamageSource(DamageSourceRegistry.GUARANTEED_SOUL_SHATTER), 1);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}