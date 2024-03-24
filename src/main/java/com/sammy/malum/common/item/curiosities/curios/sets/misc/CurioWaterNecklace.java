package com.sammy.malum.common.item.curiosities.curios.sets.misc;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class CurioWaterNecklace extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioWaterNecklace(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("better_conduit_power"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, ForgeMod.SWIM_SPEED.get(), uuid -> new AttributeModifier(uuid,
                "Curio Swim Speed", 0.15f, AttributeModifier.Operation.ADDITION) {
            @Override
            public double getAmount() {
                double amount = super.getAmount();
                if (slotContext.entity() != null) {
                    if (slotContext.entity().hasEffect(MobEffects.CONDUIT_POWER)) {
                        return amount * 3f;
                    }
                }
                return amount;
            }
        });
        super.addAttributeModifiers(map, slotContext, stack);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.level().getGameTime() % 40L == 0 && livingEntity.isSwimming()) {
            AttributeInstance attribute = livingEntity.getAttribute(ForgeMod.SWIM_SPEED.get());
            if (attribute != null) {
                attribute.setDirty();
            }
        }

        if (livingEntity.level().getGameTime() % 20L == 0) {
            if (livingEntity.hasEffect(MobEffects.CONDUIT_POWER)) {
                livingEntity.heal(2);
            }
        }
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.hasEffect(MobEffects.CONDUIT_POWER)) {
            event.setAmount(event.getAmount() * 0.5f);
        }
    }
}
