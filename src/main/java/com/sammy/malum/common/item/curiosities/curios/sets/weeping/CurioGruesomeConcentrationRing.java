package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.item.ItemTagRegistry.*;

public class CurioGruesomeConcentrationRing extends MalumCurioItem implements IVoidItem {
    public CurioGruesomeConcentrationRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("rotten_gluttony"));
    }

    public static void finishEating(LivingEntityUseItemEvent.Finish event) {
        ItemStack stack = event.getResultStack();
        if (stack.is(GROSS_FOODS)) {
            var livingEntity = event.getEntity();
            if (CurioHelper.hasCurioEquipped(livingEntity, ItemRegistry.RING_OF_GRUESOME_CONCENTRATION.get())) {
                var gluttony = MobEffectRegistry.GLUTTONY.get();
                var effect = livingEntity.getEffect(gluttony);
                var level = livingEntity.level();

                if (effect != null) {
                    EntityHelper.amplifyEffect(effect, livingEntity, 2, 9);
                } else {
                    livingEntity.addEffect(new MobEffectInstance(gluttony, 600, 1, true, true, true));
                }
                livingEntity.playSound(SoundRegistry.GRUESOME_RING_FEEDS.get(), 0.5f, RandomHelper.randomBetween(level.random, 0.8f, 1.2f));
            }
        }
    }
}