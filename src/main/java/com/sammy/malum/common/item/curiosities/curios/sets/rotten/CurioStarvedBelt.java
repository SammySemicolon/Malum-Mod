package com.sammy.malum.common.item.curiosities.curios.sets.rotten;

import com.sammy.malum.common.effect.gluttony.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.MalumParticleEffectTypes;
import com.sammy.malum.registry.common.sound.*;
import com.sammy.malum.visual_effects.networked.gluttony.AbsorbGluttonyParticleEffect;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;

import java.util.function.*;

public class CurioStarvedBelt extends MalumCurioItem implements IMalumEventResponderBody {

    public CurioStarvedBelt(Properties builder) {
        super(builder, MalumTrinketFamily.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("spirits_gluttony"));
    }

    @Override
    public void spiritCollectionEvent(CollectSpiritEvent event, LivingEntity collector, double arcaneResonance) {
        if (collector.level() instanceof ServerLevel serverLevel) {
            int limit = Mth.floor(arcaneResonance * 5);
            GluttonyEffect.applyGluttony(collector, b -> b
                    .setDuration(600)
                    .setAmplifierGain(1)
                    .setAmplifierLimit(limit));

            var random = serverLevel.random;


            SoundPlayer.create(MalumGearSoundEvents.HUNGRY_BELT_FEEDS).volume(0.7f).pitch(1.5f, 2f).play(collector);
            SoundPlayer.create(SoundEvents.GENERIC_EAT).volume(0.7f).pitch(0.8f, 1.2f).play(collector);
            createGluttonyVFX(serverLevel, collector, 0.5f);
        }
    }

    public static void createGluttonyVFX(ServerLevel serverLevel, LivingEntity target, float potency) {
        var position = target.position().add(0, target.getBbHeight() / 2f, 0);
        MalumParticleEffectTypes.GLUTTONY_ABSORB.createEffect(position)
                .customData(new AbsorbGluttonyParticleEffect.AbsorbGluttonyEffectData(potency))
                .spawn(serverLevel);
    }

}