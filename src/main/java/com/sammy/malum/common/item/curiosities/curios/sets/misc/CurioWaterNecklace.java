package com.sammy.malum.common.item.curiosities.curios.sets.misc;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.mixin.AccessorPostDamageEvent;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class CurioWaterNecklace extends MalumCurioItem implements IMalumEventResponderItem {

    public static final AttributeModifier SWIM_SPEED = new AttributeModifier(MalumMod.malumPath("curio_swim_speed"), 0.15f, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier SWIM_SPEED_CONDUIT = new AttributeModifier(MalumMod.malumPath("curio_swim_speed"), 0.45f, AttributeModifier.Operation.ADD_VALUE);

    public CurioWaterNecklace(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        super.addAttributeModifiers(map, slotContext, stack);
        addAttributeModifier(map, NeoForgeMod.SWIM_SPEED, SWIM_SPEED);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("better_conduit_power"));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.level().getGameTime() % 40L == 0 && livingEntity.isSwimming()) {
            AttributeInstance attribute = livingEntity.getAttribute(NeoForgeMod.SWIM_SPEED);
            if (attribute != null) {
                attribute.setDirty();
                attribute.addOrUpdateTransientModifier(
                        livingEntity.hasEffect(MobEffects.CONDUIT_POWER) ? SWIM_SPEED_CONDUIT : SWIM_SPEED
                );
            }
        }

        if (livingEntity.level().getGameTime() % 20L == 0) {
            if (livingEntity.hasEffect(MobEffects.CONDUIT_POWER)) {
                livingEntity.heal(2);
            }
        }
    }

    @Override
    public void takeDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.hasEffect(MobEffects.CONDUIT_POWER)) {
            ((AccessorPostDamageEvent)event).malum$setNewDamage(event.getNewDamage() * 0.5f);
        }
    }
}
