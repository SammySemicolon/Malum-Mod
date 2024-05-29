package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.function.Consumer;

import static com.sammy.malum.registry.common.item.ItemTagRegistry.GROSS_FOODS;

public class CurioGruesomeConcentrationRing extends MalumCurioItem implements IVoidItem {
    public CurioGruesomeConcentrationRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("rotten_gluttony"));
    }

    public static ItemStack finishEating(LivingEntity livingEntity, ItemStack itemStack, int i, ItemStack stack) {
        if (livingEntity instanceof Player player) {
            if (TrinketsHelper.hasTrinketEquipped(player, ItemRegistry.RING_OF_GRUESOME_CONCENTRATION.get())) {
                if (stack.is(GROSS_FOODS)) {
                    double arcaneResonance = player.getAttribute(AttributeRegistry.ARCANE_RESONANCE.get()).getValue();
                    MobEffect gluttony = MobEffectRegistry.GLUTTONY.get();
                    MobEffectInstance effect = player.getEffect(MobEffectRegistry.GLUTTONY.get());
                    if (effect != null) {
                        EntityHelper.amplifyEffect(effect, player, 2, 9);
                    }
                    else {
                        player.addEffect(new MobEffectInstance(gluttony, 600 + (int) (arcaneResonance * 600), 1, true, true, true));
                    }
                }
            }
        }
        return itemStack;
    }
}
