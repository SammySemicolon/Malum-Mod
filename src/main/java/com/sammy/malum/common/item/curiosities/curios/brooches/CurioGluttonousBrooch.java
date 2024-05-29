package com.sammy.malum.common.item.curiosities.curios.brooches;

import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public class CurioGluttonousBrooch extends MalumCurioItem {
    public static final UUID GLUTTONOUS_BROOCH_BELT = UUID.fromString("f8ec834e-18ba-4deb-9156-ff70d52821e4");

    public CurioGluttonousBrooch(Properties builder) {
        super(builder, MalumTrinketType.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(negativeEffect("hunger_drain"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slotContext, ItemStack stack) {
        //TODO CuriosApi.addSlotModifier(map, "belt", GLUTTONOUS_BROOCH_BELT, 1, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (slot.inventory().getComponent().getEntity() instanceof Player player) {
            player.causeFoodExhaustion(0.005f);
        }
        super.tick(stack, slot, entity);
    }
}
