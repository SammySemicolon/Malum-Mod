package com.sammy.malum.common.item.curiosities.curios.brooches;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;
import java.util.function.*;

public class CurioRavenousBrooch extends MalumCurioItem {

    public static final UUID RAVENOUS_BROOCH_BELT = UUID.fromString("f8ec834e-18ba-4deb-9156-ff70d52821e4");

    public CurioRavenousBrooch(Properties builder) {
        super(builder, MalumTrinketType.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(negativeEffect("malum.gui.curio.effect.ravenous_brooch"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        CuriosApi.addSlotModifier(map, "belt", RAVENOUS_BROOCH_BELT, 1, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            player.causeFoodExhaustion(0.005f);
        }
        super.curioTick(slotContext, stack);
    }
}