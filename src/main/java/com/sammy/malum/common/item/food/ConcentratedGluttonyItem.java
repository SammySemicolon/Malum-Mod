package com.sammy.malum.common.item.food;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.*;

import java.util.*;
import java.util.function.*;

public class ConcentratedGluttonyItem extends BottledDrinkItem {
    public static final Collection<Supplier<Item>> ROTTEN_TRINKETS = new ArrayList<>(List.of(ItemRegistry.RING_OF_DESPERATE_VORACITY, ItemRegistry.GLUTTONOUS_BROOCH, ItemRegistry.BELT_OF_THE_STARVED));

    public ConcentratedGluttonyItem(Properties builder) {
        super(builder);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        pEntityLiving.addEffect(createGluttonyEffect(pEntityLiving));
        pEntityLiving.playSound(SoundRegistry.CONCENTRATED_GLUTTONY_DRINK.get(), 1f, RandomHelper.randomBetween(pLevel.random, 1.5f, 2f));
        if (pLevel instanceof ServerLevel serverLevel) {
            var position = pEntityLiving.position().add(0, pEntityLiving.getBbHeight()/2f, 0);
            ParticleEffectTypeRegistry.THROWN_GLUTTONY_IMPACT.createPositionedEffect(serverLevel, new PositionEffectData(position));

        }
        return super.finishUsingItem(pStack, pLevel, pEntityLiving);
    }

    public static MobEffectInstance createGluttonyEffect(@Nullable Entity source) {
        return createGluttonyEffect(source, 1);
    }

    public static MobEffectInstance createGluttonyEffect(@Nullable Entity source, float durationScalar) {
        int amplifier = 3;
        int duration = 20;

        if (source instanceof LivingEntity livingEntity) {
            if (TrinketsHelper.hasTrinketEquipped(livingEntity, ItemRegistry.RING_OF_GRUESOME_CONCENTRATION.get())) {
                amplifier++;
                duration += 40;
            }
            for (Supplier<Item> rottenTrinket : ROTTEN_TRINKETS) {
                if (TrinketsHelper.hasTrinketEquipped(livingEntity, rottenTrinket.get())) {
                    amplifier++;
                    duration += 10;
                }
            }
        }
        return new MobEffectInstance(MobEffectRegistry.GLUTTONY.get(), (int) (duration * 20 * durationScalar), amplifier);
    }
}