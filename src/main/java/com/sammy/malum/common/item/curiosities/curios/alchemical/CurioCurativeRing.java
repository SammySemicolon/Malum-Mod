package com.sammy.malum.common.item.curiosities.curios.alchemical;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class CurioCurativeRing extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioCurativeRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void pickupSpirit(LivingEntity collector, ItemStack stack, double arcaneResonance) {
        collector.heal(collector.getMaxHealth() * 0.1f + (float) (arcaneResonance * 0.05f));
    }
}