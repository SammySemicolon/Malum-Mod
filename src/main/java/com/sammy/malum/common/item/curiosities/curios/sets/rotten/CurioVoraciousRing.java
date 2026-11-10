package com.sammy.malum.common.item.curiosities.curios.sets.rotten;

import com.sammy.malum.common.effect.gluttony.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.core.easing.Easing;

import java.util.function.*;

import static com.sammy.malum.registry.common.MalumTags.Items.*;

public class CurioVoraciousRing extends MalumCurioItem {

    public CurioVoraciousRing(Properties builder) {
        super(builder, MalumTrinketFamily.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("eat_rotten"));
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("rotten_gluttony"));
    }

    public static void modifyEating(LivingEntityUseItemEvent.Start event) {
        if (!event.getItem().is(GROSS_FOODS)) {
            return;
        }
        if (!CurioHelper.hasCurioEquipped(event.getEntity(), MalumContent.Gear.RING_OF_DESPERATE_VORACITY.get())) {
            return;
        }
        event.setDuration((int) (event.getDuration() * 0.5f));
    }

    public static void onEat(Level level, LivingEntity livingEntity, ItemStack food) {
        if (level.isClientSide) {
            return;
        }
        if (!food.is(GROSS_FOODS)) {
            return;
        }
        if (!CurioHelper.hasCurioEquipped(livingEntity, MalumContent.Gear.RING_OF_DESPERATE_VORACITY.get())) {
            return;
        }
        if (livingEntity instanceof Player player) {
            player.getFoodData().eat(1, 1f);
        }
        GluttonyEffect.applyGluttony(livingEntity, b -> b
                .setDuration(600)
                .setAmplifierGain(1)
                .setAmplifierLimit(5));
        float pitch = Easing.SINE_IN_OUT.asWeighedRandom(level.random, 1.2f, 1.6f);
        livingEntity.playSound(MalumGearSoundEvents.VORACIOUS_RING_FEEDS.get(), 0.5f, pitch);
    }
}