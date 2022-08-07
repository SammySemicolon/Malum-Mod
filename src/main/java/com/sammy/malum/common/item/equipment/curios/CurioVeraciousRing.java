package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import team.lodestar.lodestone.helpers.CurioHelper;
import team.lodestar.lodestone.helpers.EntityHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.UUID;

import static com.sammy.malum.core.setup.content.item.ItemTagRegistry.GROSS_FOODS;

public class CurioVeraciousRing extends MalumCurioItem {

    public CurioVeraciousRing(Properties builder) {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR, new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Curio armor boost", 1, AttributeModifier.Operation.ADDITION));
        return map;
    }

    public static void accelerateEating(LivingEntityUseItemEvent.Start event) {
        if (CurioHelper.hasCurioEquipped(event.getEntityLiving(), ItemRegistry.RING_OF_DESPERATE_VORACITY)) {
            if (event.getItem().is(GROSS_FOODS)) {
                event.setDuration((int) (event.getDuration() * 0.6f));
            }
        }
    }

    public static void finishEating(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntityLiving() instanceof Player player) {
            ItemStack stack = event.getResultStack();
            if (CurioHelper.hasCurioEquipped(player, ItemRegistry.RING_OF_DESPERATE_VORACITY)) {
                if (stack.is(GROSS_FOODS)) {
                    player.getFoodData().eat(1, 0.5f);
                    MobEffectInstance effect = player.getEffect(MobEffects.HUNGER);
                    if (effect != null) {
                        EntityHelper.shortenEffect(effect, player, 100);
                    }
                }
            }
        }
    }

    @Override
    public boolean isOrnate() {
        return true;
    }
}