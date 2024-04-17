package com.sammy.malum.common.item.curiosities.curios.sets.rotten;

import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.item.ItemTagRegistry.*;

public class CurioVoraciousRing extends MalumCurioItem {

    public CurioVoraciousRing(Properties builder) {
        super(builder, MalumTrinketType.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("eat_rotten"));
        consumer.accept(positiveEffect("growing_gluttony"));
    }

    public static void accelerateEating(LivingEntityUseItemEvent.Start event) {
        if (CurioHelper.hasCurioEquipped(event.getEntity(), ItemRegistry.RING_OF_DESPERATE_VORACITY.get())) {
            if (event.getItem().is(GROSS_FOODS)) {
                event.setDuration((int) (event.getDuration() * 0.5f));
            }
        }
    }
    public static void finishEating(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack stack = event.getResultStack();
            if (CurioHelper.hasCurioEquipped(player, ItemRegistry.RING_OF_DESPERATE_VORACITY.get())) {
                if (stack.is(GROSS_FOODS)) {
                    Level level = player.level();
                    MobEffectInstance gluttony = player.getEffect(MobEffectRegistry.GLUTTONY.get());
                    MobEffectInstance hunger = player.getEffect(MobEffects.HUNGER);
                    if (gluttony != null) {
                        EntityHelper.extendEffect(gluttony, player, 300, 600);
                        level.playSound(null, player.blockPosition(), SoundRegistry.HUNGRY_BELT_FEEDS.get(), SoundSource.PLAYERS, 0.75f, RandomHelper.randomBetween(level.random, 0.8f, 1.2f));
                    }
                    if (hunger != null) {
                        EntityHelper.shortenEffect(hunger, player, 150);
                    }
                    player.getFoodData().eat(1, 1f);
                }
            }
        }
    }
}
