package com.sammy.malum.common.effect.gluttony;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;

public class DesperateNeedEffect extends MobEffect {

    public DesperateNeedEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(88, 86, 60));
        final ResourceLocation id = MalumMod.malumPath("desperate_need");
        addAttributeModifier(MalumAttributes.SCYTHE_PROFICIENCY, id, 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof Player player) {
            player.causeFoodExhaustion(0.01f * (amplifier + 1));
        }
        return true;
    }

    public static void spawnLocusts(LivingDamageEvent.Post event) {
        var source = event.getSource();
        if (!(source.getEntity() instanceof LivingEntity attacker)) {
            return;
        }
        if (!source.is(MalumTags.DamageTypes.IS_SCYTHE)) {
            return;
        }
        if (source.is(MalumDamageTypes.SCYTHE_MAELSTROM)) {
            if (attacker.level().getRandom().nextFloat() >= 0.2f) {
                return;
            }
        }
        var instance = attacker.getEffect(MalumMobEffects.DESPERATE_NEED);
        if (instance == null) {
            return;
        }
        var target = event.getEntity();

        if (GluttonyEffect.spawnLocusts(attacker, target, 1)) {
            instance.amplifier--;
            if (instance.amplifier < 0) {
                attacker.removeEffect(MalumMobEffects.DESPERATE_NEED);
            } else {
                EntityHelper.syncEffect(instance, attacker);
            }
        }
    }
}