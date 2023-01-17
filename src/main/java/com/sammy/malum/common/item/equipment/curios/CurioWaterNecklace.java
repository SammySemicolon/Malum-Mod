package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class CurioWaterNecklace extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioWaterNecklace(Properties builder) {
        super(builder);
    }

    @Override
    public boolean isGilded() {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();

        map.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Swim Speed", 0.15f, AttributeModifier.Operation.ADDITION) {
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
        return map;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity.level.getGameTime() % 40L == 0 && livingEntity.isSwimming()) {
            AttributeInstance attribute = livingEntity.getAttribute(ForgeMod.SWIM_SPEED.get());
            if (attribute != null) {
                attribute.setDirty();
            }
        }
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.hasEffect(MobEffects.CONDUIT_POWER)) {
            event.setAmount(event.getAmount() * 0.5f);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity.level.getGameTime() % 20L == 0) {
            if (entity.hasEffect(MobEffects.CONDUIT_POWER)) {
                entity.heal(2);
            }
        }
    }
}