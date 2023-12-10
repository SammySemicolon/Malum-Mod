package com.sammy.malum.common.item.curiosities.curios.rotten;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import team.lodestar.lodestone.helpers.*;
import top.theillusivec4.curios.api.SlotContext;

import static com.sammy.malum.registry.common.item.ItemTagRegistry.GROSS_FOODS;

public class CurioVoraciousRing extends MalumCurioItem {

    public CurioVoraciousRing(Properties builder) {
        super(builder, MalumTrinketType.ROTTEN);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.ARMOR_TOUGHNESS, uuid -> new AttributeModifier(uuid,
                "Curio Armor Toughness", 1f, AttributeModifier.Operation.ADDITION));
        addAttributeModifier(map, Attributes.ARMOR, uuid -> new AttributeModifier(uuid,
                "Curio Armor", 1f, AttributeModifier.Operation.ADDITION));
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
                    double arcaneResonance = player.getAttribute(AttributeRegistry.ARCANE_RESONANCE.get()).getValue();
                    Level level = player.level();
                    MobEffectInstance gluttony = player.getEffect(MobEffectRegistry.GLUTTONY.get());
                    MobEffectInstance hunger = player.getEffect(MobEffects.HUNGER);
                    if (gluttony != null) {
                        EntityHelper.extendEffect(gluttony, player, 300, 600 + (int) (arcaneResonance * 600));
                    }
                    if (hunger != null) {
                        EntityHelper.shortenEffect(hunger, player, 150);
                    }
                    player.getFoodData().eat(1, 1f);
                    level.playSound(null, player.blockPosition(), SoundRegistry.HUNGRY_BELT_FEEDS.get(), SoundSource.PLAYERS, 1.7f, RandomHelper.randomBetween(level.random, 0.8f, 1.2f));
                }
            }
        }
    }
}