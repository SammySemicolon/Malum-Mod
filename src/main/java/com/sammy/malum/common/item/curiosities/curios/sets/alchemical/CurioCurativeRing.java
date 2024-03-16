package com.sammy.malum.common.item.curiosities.curios.sets.alchemical;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.*;

public class CurioCurativeRing extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioCurativeRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.curio.effect.ring_of_curative_talent"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, ItemStack stack, double arcaneResonance) {
        collector.heal(collector.getMaxHealth() * 0.1f + (float) (arcaneResonance * 0.05f));
    }
}